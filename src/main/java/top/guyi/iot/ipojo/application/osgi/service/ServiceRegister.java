package top.guyi.iot.ipojo.application.osgi.service;

import top.guyi.iot.ipojo.application.ApplicationContext;
import top.guyi.iot.ipojo.application.bean.interfaces.ApplicationStartEvent;
import org.osgi.framework.BundleContext;
import top.guyi.iot.ipojo.application.osgi.service.entry.ServiceEntry;

import java.util.LinkedList;
import java.util.List;

public class ServiceRegister implements ApplicationStartEvent {

    private List<ServiceEntry> entries = new LinkedList<>();

    protected void registerAll(){}

    protected void register(ServiceEntry entry){
        this.entries.add(entry);
    }

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
