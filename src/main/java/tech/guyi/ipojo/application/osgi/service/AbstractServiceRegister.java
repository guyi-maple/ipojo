package tech.guyi.ipojo.application.osgi.service;

import tech.guyi.ipojo.application.ApplicationContext;
import tech.guyi.ipojo.application.bean.interfaces.ApplicationStartEvent;
import org.osgi.framework.BundleContext;
import tech.guyi.ipojo.application.osgi.service.entry.ServiceEntry;

import java.util.LinkedList;
import java.util.List;

/**
 * @author guyi
 * OSGI服务注册者
 */
public abstract class AbstractServiceRegister implements ApplicationStartEvent {

    private List<ServiceEntry> entries = new LinkedList<>();

    /**
     * 注册所有服务
     */
    protected abstract void registerAll();

    protected void register(ServiceEntry entry){
        this.entries.add(entry);
    }

    @Override
    public void onStart(ApplicationContext applicationContext, BundleContext bundleContext) {
        this.registerAll();
        for (ServiceEntry entry : this.entries) {
            Object service = applicationContext.get(entry.getService());
            if (service != null){
                bundleContext.registerService(entry.getInterfaceClass().getName(),service,null);
            }
        }
    }

}
