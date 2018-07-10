package com.xs.blog.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.spi.LocationAwareLogger;

/**
 * Created by xs on 2018/7/10
 */
public class LoggerWithMDC {
    //logger 代理类
    private static final String callerFQCN = LoggerWithMDC.class.getName();
    private static final Logger fqcnLogger = LoggerFactory.getLogger(LoggerWithMDC.class);

    private String module;

    public static String MODULE = "module";

    //使用此logger , 定位真实的日志调用者
    private LocationAwareLogger logger;

    private LoggerWithMDC(Logger logger, String module) {
        this.logger = (LocationAwareLogger) logger;
        this.module = module;
    }

    static public LoggerWithMDC getLogger(String className, String module) {
        return new LoggerWithMDC(LoggerFactory.getLogger(className), module);
    }

    static public LoggerWithMDC getLogger(Class<?> clazz, String module) {
        return new LoggerWithMDC(LoggerFactory.getLogger(clazz), module);
    }

    public void debug(String msg, Object... args) {
        MDC.put(MODULE, module);
        logger.log(null, callerFQCN, LocationAwareLogger.DEBUG_INT, msg, args, null);
    }

    public void debug(String msg) {
        MDC.put(MODULE, module);
        logger.log(null, callerFQCN, LocationAwareLogger.DEBUG_INT, msg, null, null);
    }

    public void info(String msg, Object... args) {
        MDC.put(MODULE, module);
        logger.log(null, callerFQCN, LocationAwareLogger.INFO_INT, msg, args, null);
    }

    public void info(String msg) {
        MDC.put(MODULE, module);
        logger.log(null, callerFQCN, LocationAwareLogger.INFO_INT, msg, null, null);
    }

    public void error(String msg, Object... args) {
        MDC.put(MODULE, module);
        logger.log(null, callerFQCN, LocationAwareLogger.ERROR_INT, msg, args, null);
    }

    public void error(String msg) {
        MDC.put(MODULE, module);
        logger.log(null, callerFQCN, LocationAwareLogger.ERROR_INT, msg, null, null);
    }

    public void error(Throwable e) {
        MDC.put(MODULE, module);
        logger.log(null, callerFQCN, LocationAwareLogger.ERROR_INT, e.getMessage(), null, e);
    }

    public void error(String msg, Throwable e) {
        MDC.put(MODULE, module);
        logger.log(null, callerFQCN, LocationAwareLogger.ERROR_INT, msg, null, e);
    }

    public void error(String msg, Throwable e, Object... args) {
        MDC.put(MODULE, module);
        logger.log(null, callerFQCN, LocationAwareLogger.ERROR_INT, msg, args, e);
    }

    public void put2MDC(String key, String value) {
        MDC.put(key, value);
    }

}
