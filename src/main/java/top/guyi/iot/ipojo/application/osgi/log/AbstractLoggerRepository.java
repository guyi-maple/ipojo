package top.guyi.iot.ipojo.application.osgi.log;

import top.guyi.iot.ipojo.application.ApplicationContext;
import top.guyi.iot.ipojo.application.osgi.service.reference.BundleServiceReference;
import net.sf.cglib.proxy.Enhancer;
import org.osgi.service.log.Logger;
import org.osgi.service.log.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class AbstractLoggerRepository {

    final static String DEFAULT_LOGGER_KEY = "default";

    private static Map<String,Logger> loggerMap = new HashMap<>();
    private static Map<String,LoggerMethodInterceptor> interceptorHashMap = new HashMap<>();

    @BundleServiceReference(LoggerFactory.class)
    public void awaitLoggerFactory(LoggerFactory factory,ApplicationContext context){
        StaticLogger.setLogger(factory.getLogger("IPOJO"));
        for (Map.Entry<String, LoggerMethodInterceptor> entry : interceptorHashMap.entrySet()) {
            entry.getValue().setLogger(new DefaultLogger(factory.getLogger(DEFAULT_LOGGER_KEY.equals(entry.getKey()) ? context.getName() : entry.getKey())));
        }
    }

    public static Logger get(String name){
        Logger logger = loggerMap.get(name);
        if (logger == null){
            LoggerMethodInterceptor interceptor = interceptorHashMap.get(name);
            if (interceptor == null){
                interceptor = new LoggerMethodInterceptor(null);
                interceptorHashMap.put(name,interceptor);
            }

            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(DefaultLogger.class);
            enhancer.setCallback(interceptor);
            logger = (Logger) enhancer.create();
            loggerMap.put(name,logger);
        }
        return logger;
    }

}
