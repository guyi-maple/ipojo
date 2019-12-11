package top.guyi.iot.ipojo.application.osgi.log;

import org.osgi.service.log.Logger;

public class StaticLogger {

    private static Logger logger;
    public static void setLogger(Logger logger){
        StaticLogger.logger = logger;
    }

    public static void info(String log,Object...args){
        if (logger != null){
            logger.info(log,args);
        }
    }

    public static void debug(String log,Object...args){
        if (logger != null){
            logger.debug(log,args);
        }
    }

    public static void error(String log,Object...args){
        if (logger != null){
            logger.error(log,args);
        }
    }

}
