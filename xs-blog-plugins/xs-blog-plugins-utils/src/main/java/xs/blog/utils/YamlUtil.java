package xs.blog.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by xs on 2018/7/7
 */
public class YamlUtil {
    public static Properties transToProp(File file) {
        try (InputStream in = new FileInputStream(file)) {
            Yaml yaml = new Yaml();
            Map<String, Object> result = yaml.load(in);
//            JSONObject result = JSON.parseObject(JSON.toJSONString( yaml.load(in)));
            Properties prop = new Properties();
            dfsResolve(result, null, prop);
            return prop;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void dfsResolve(Map<String, Object> tree, String keyPreffix, Properties prop) {
        for (Map.Entry<String, Object> entry : tree.entrySet()) {
            Object value = entry.getValue();
//            System.out.println(value.getClass());
            if (value == null || StringUtils.isEmpty(value.toString())) {
                continue;
            }
            if (value instanceof Map) {
                if (StringUtils.isEmpty(keyPreffix)) {
                    dfsResolve((Map<String, Object>) value, entry.getKey(), prop);
                } else {
                    dfsResolve((Map<String, Object>) value, keyPreffix + "." + entry.getKey(), prop);
                }
            } else {
                prop.put(keyPreffix + "." + entry.getKey(), value.toString().trim());
            }
        }
    }

    public static void main(String[] args) {
        File file = new File("/Users/xs/workspaces/IdeaProjects/xs-blog/xs-blog-base/xs-blog-bootstrap/src/main/reasources/config-test/config.test.yml");
        Properties prop = transToProp(file);
        System.out.println(JSON.toJSONString(prop, SerializerFeature.PrettyFormat));
    }
}
