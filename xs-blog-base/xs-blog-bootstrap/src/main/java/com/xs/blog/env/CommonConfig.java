package com.xs.blog.env;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xs.blog.utils.YamlUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xs on 2018/7/6
 */
public class CommonConfig {
    private static final Logger LOG = LoggerFactory.getLogger(CommonConfig.class);

    private static String defaultConfigFilePath = "classpath:config-test/config.test.yml";

    private static final String sysProp_filePath = "blog.service.config.path";
    private static final String sysProp_serverId = "blog.service.id";
    private static final String sysProp_serverPort = "blog.service.port";
    private static final String sysProp_configName = "blog.service.config.name";
    private static final String springConfigServerName = "spring.application.name";
    private static final String springConfigServerPort = "server.port";

    private static Properties prop = new Properties();

    /**
     * 环境初始化
     * 加载配置文件，在jar包当前目录下的config文件夹内生成bootstrap.properties
     * @param args
     */
    public static void init(String[] args) {
        Properties sysProp = System.getProperties();
        String filePathStr = sysProp.getProperty(sysProp_filePath, defaultConfigFilePath);
        String configName = sysProp.getProperty(sysProp_configName);
        if (!StringUtils.isEmpty(filePathStr)) {
            // 加载配置文件
            String[] filePaths = filePathStr.split(";");
            for (String filePath : filePaths) {
                Properties properties = resolveFileAddProp(filePath, prop);
                prop.putAll(properties);
            }
        }
        String serverId = sysProp.getProperty(sysProp_serverId);
        if (!StringUtils.isEmpty(serverId)) {
            sysProp.put(springConfigServerName, serverId);
            prop.put(springConfigServerName, serverId);
            prop.put(sysProp_serverId, serverId);
        }
        String serverPort = sysProp.getProperty(sysProp_serverPort);
        if (!StringUtils.isEmpty(serverPort)) {
            sysProp.put(springConfigServerPort, serverPort);
            prop.put(sysProp_serverPort, serverPort);
            prop.put(springConfigServerPort, serverPort);
        }
        assertLostParams();
        createBootStrapProp();
//        replaceEl();
    }

    private static void createBootStrapProp() {
        String path = getJarPath() + "config/bootstrap.properties";
        System.out.println(path);
    }

    private static String getJarPath() {
        java.net.URL url = CommonConfig.class.getProtectionDomain().getCodeSource()
                .getLocation();
        String filePath = null;
        try {
            filePath = java.net.URLDecoder.decode(url.getPath(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (filePath.endsWith(".jar"))
            filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1);
        java.io.File file = new java.io.File(filePath);
        filePath = file.getAbsolutePath();
        return filePath;
    }
    private static void assertLostParams() {
        Properties sysProp = System.getProperties();
        for (String key : sysProp.stringPropertyNames()) {
            System.out.println("systemProp:" + key + "=" + sysProp.getProperty(key));
        }
        String serverIdProp = sysProp.getProperty(springConfigServerName);
        String serverPortProp = sysProp.getProperty(springConfigServerPort);
        if (StringUtils.isEmpty(serverIdProp) || StringUtils.isEmpty(serverPortProp)) {
            serverIdProp = prop.getProperty(springConfigServerName);
            serverPortProp = prop.getProperty(springConfigServerPort);
            if (StringUtils.isEmpty(serverIdProp) || StringUtils.isEmpty(serverPortProp)) {
                throw new RuntimeException("prop lost params:serverId=" + serverIdProp + ";serverPort=" + serverPortProp);
            } else {
                sysProp.put(springConfigServerName, serverIdProp);
                sysProp.put(springConfigServerPort, serverPortProp);
            }
        }
        sysProp.put(sysProp_serverId, serverIdProp);
        sysProp.put(sysProp_serverPort, serverPortProp);
        prop.put(sysProp_serverId, serverIdProp);
        prop.put(sysProp_serverPort, serverPortProp);
    }

    private static void replaceEl() {
        String regex = "\\$\\{[^}]+}";
        Pattern pattern = Pattern.compile(regex);
        Properties prop = System.getProperties();

        for (String key : prop.stringPropertyNames()) {
            String value = prop.getProperty(key);
            Matcher matcher = pattern.matcher(value);
            String newValue = value;
            while (matcher.find()) {
                String match = matcher.group();
                String propKey = match.substring(2, match.length()-1);
                String replaceValue = prop.getProperty(propKey);
                if (replaceValue == null) {
                    replaceValue = "";
                } else {
                    replaceValue = replaceValue.trim();
                }
                newValue = newValue.replace(match, replaceValue);
            }
            if (!value.equals(newValue)) System.out.println(value + "--->" + newValue);
            prop.put(key, newValue);
        }
    }

    public static void main(String[] args) {
        init(args);
    }
    private static Properties resolveFileAddProp(String filePath, Properties prop) {
        Properties singleProp = new Properties();
        if (filePath.startsWith("classpath:")) {
            filePath = CommonConfig.class.getResource("/").getPath() + filePath.replace("classpath:", "");
        }
        LOG.info("filePath=" + filePath);
        singleProp.putAll(laodDir(new File(filePath)));
//        System.getProperties().putAll(singleProp);
        return singleProp;
    }

    private static Properties laodDir(File file) {
        Properties prop = new Properties();
        if(file==null) return prop;
        if (file.isDirectory()) {
            for(File f : file.listFiles()){
                prop.putAll(laodDir(f));
            }
        } else if (file.isFile()) {
            if (file.getName().endsWith(".properties")) {
                try(InputStream in = new FileInputStream(file)) {
                    prop.load(in);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (file.getName().endsWith(".yml")) {
                prop.putAll(resolveYmlFile(file));
            }
        }
        return prop;
    }

    private static Properties resolveYmlFile(File file) {
        Properties properties = YamlUtil.transToProp(file);
        if (properties == null) {
            return new Properties();
        }
        return properties;
    }
}
