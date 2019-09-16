package top.guyi.iot.ipojo.application.osgi.configuration;

import org.osgi.framework.BundleContext;
import top.guyi.iot.ipojo.application.ApplicationContext;
import top.guyi.iot.ipojo.application.bean.interfaces.ApplicationStartEvent;
import top.guyi.iot.ipojo.application.osgi.configuration.format.ConfigurationTypeFormat;
import top.guyi.iot.ipojo.application.osgi.log.StaticLogger;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class ConfigurationRefresher implements ApplicationStartEvent {

    private ApplicationContext applicationContext;
    private ConfigurationTypeFormat format = new ConfigurationTypeFormat();
    private Map<String, List<ConfigurationRefreshInvoker>> invokers = new HashMap<>();

    protected List<ConfigurationRefreshInvoker> getInvokers(String key){
        List<ConfigurationRefreshInvoker> invokers = this.invokers.get(key);
        if (invokers == null){
            invokers = new LinkedList<>();
        }
        return invokers;
    }

    protected abstract void registerInvokerAll();

    protected void registerInvoker(ConfigurationRefreshInvoker invoker){
        List<ConfigurationRefreshInvoker> invokers = this.getInvokers(invoker.getKey());
        invokers.add(invoker);
        this.invokers.put(invoker.getKey(),invokers);
    }

    @Override
    public void onStart(ApplicationContext applicationContext, BundleContext bundleContext) throws Exception {
        this.applicationContext = applicationContext;
        this.registerInvokerAll();
    }

    public void publish(String key,Object configurationValue){
        try{
            if (configurationValue == null){
                return;
            }
            for (ConfigurationRefreshInvoker invoker : this.getInvokers(key)) {
                Object value = this.format.format(invoker.getType(),configurationValue);
                invoker.refresh(this.applicationContext,Object.class.cast(value));
            }
        }catch (Exception e){
            StaticLogger.error("配置刷新失败 {}",key,e);
        }
    }

    public void publish(Map<String,Object> configurations){
        for (Map.Entry<String, Object> entry : configurations.entrySet()) {
            this.publish(entry.getKey(),entry.getValue());
        }
    }
    
}
