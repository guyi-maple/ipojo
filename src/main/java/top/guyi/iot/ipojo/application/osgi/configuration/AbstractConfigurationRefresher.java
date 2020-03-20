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

/**
 * @author guyi
 * 配置刷新器
 */
public abstract class AbstractConfigurationRefresher implements ApplicationStartEvent {

    private ApplicationContext applicationContext;
    private ConfigurationTypeFormat format = new ConfigurationTypeFormat();
    private Map<String, List<AbstractConfigurationRefreshInvoker>> invokers = new HashMap<>();

    protected List<AbstractConfigurationRefreshInvoker> getInvokers(String key){
        List<AbstractConfigurationRefreshInvoker> invokers = this.invokers.get(key);
        if (invokers == null){
            invokers = new LinkedList<>();
        }
        return invokers;
    }

    /**
     * 注册所有配置刷新器
     */
    protected abstract void registerInvokerAll();

    protected void registerInvoker(AbstractConfigurationRefreshInvoker invoker){
        List<AbstractConfigurationRefreshInvoker> invokers = this.getInvokers(invoker.getKey());
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
            for (AbstractConfigurationRefreshInvoker invoker : this.getInvokers(key)) {
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
