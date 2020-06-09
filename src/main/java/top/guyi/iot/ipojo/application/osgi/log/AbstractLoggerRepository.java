package top.guyi.iot.ipojo.application.osgi.log;

import top.guyi.iot.ipojo.application.ApplicationContext;
import top.guyi.iot.ipojo.application.osgi.service.reference.BundleServiceReference;
import org.osgi.service.log.Logger;
import org.osgi.service.log.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author guyi
 * logger仓库
 */
public abstract class AbstractLoggerRepository {

    final static String DEFAULT_LOGGER_KEY = "default";

    private final Map<String,Logger> loggerMap = new HashMap<>();
    private final Map<String,DefaultLogger> interceptorHashMap = new HashMap<>();

    @BundleServiceReference(LoggerFactory.class)
    public void awaitLoggerFactory(LoggerFactory factory,ApplicationContext context){
        StaticLogger.setLogger(factory.getLogger("IPOJO"));
        for (Map.Entry<String, DefaultLogger> entry : interceptorHashMap.entrySet()) {
            entry.getValue().setLogger(factory.getLogger(DEFAULT_LOGGER_KEY.equals(entry.getKey()) ? context.getName() : entry.getKey()));
        }
    }

    public Logger get(String name){
        Logger logger = loggerMap.get(name);
        if (logger == null){
            logger = interceptorHashMap.get(name);
            if (logger == null){
                logger = new DefaultLogger(new NoneLogger(name));
                interceptorHashMap.put(name,(DefaultLogger) logger);
            }
            loggerMap.put(name,logger);
        }
        return logger;
    }

}
