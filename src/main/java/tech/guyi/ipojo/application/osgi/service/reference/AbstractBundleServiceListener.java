package tech.guyi.ipojo.application.osgi.service.reference;

import tech.guyi.ipojo.application.ApplicationContext;
import tech.guyi.ipojo.application.bean.interfaces.ApplicationStartEvent;
import tech.guyi.ipojo.application.bean.interfaces.ApplicationStartSuccessEvent;
import tech.guyi.ipojo.application.bean.interfaces.ApplicationStopEvent;
import org.osgi.framework.BundleContext;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author guyi
 * OSGI服务监听器
 */
public abstract class AbstractBundleServiceListener
        implements ApplicationStartEvent, ApplicationStartSuccessEvent, ApplicationStopEvent {

    private ApplicationContext applicationContext;
    private BundleContext bundleContext;

    /**
     * 注册所有OSGI服务监听器
     * @param applicationContext 容器上下文
     */
    protected abstract void registerAll(ApplicationContext applicationContext);

    private Map<String,ServiceReferenceEntry> entries;

    protected void register(ServiceReferenceEntry entry){
        if (entry.getInvoker().check(entry,applicationContext,bundleContext)){
            entry.getInvoker().invoke(entry,applicationContext,bundleContext);
        }else{
            entries.put(entry.getId(),entry);
        }
    }

    @Override
    public void onStart(final ApplicationContext applicationContext, final BundleContext bundleContext) throws Exception {
        this.applicationContext = applicationContext;
        this.bundleContext = bundleContext;
        this.entries = new HashMap<>(20);
        this.registerAll(applicationContext);
    }

    private List<DefaultServiceTracker> trackers = new LinkedList<>();

    @Override
    public void onStartSuccess(final ApplicationContext applicationContext, final BundleContext bundleContext) throws Exception {
        Map<Class<?>,List<ServiceReferenceEntry>> trackerTemp = new HashMap<>(10);

        for (ServiceReferenceEntry entry : entries.values()) {
            for (Class<?> serviceClass : entry.getServiceClasses()) {
                List<ServiceReferenceEntry> list = trackerTemp.get(serviceClass);
                if (list == null){
                    list = new LinkedList<>();
                }
                list.add(entry);
                trackerTemp.put(serviceClass,list);
            }
        }

        for (Map.Entry<Class<?>, List<ServiceReferenceEntry>> entry : trackerTemp.entrySet()) {
            DefaultServiceTracker tracker = new DefaultServiceTracker(applicationContext,bundleContext,entry.getKey(),entry.getValue());
            tracker.open();
            this.trackers.add(tracker);
        }

        this.entries = null;
    }

    @Override
    public void onStop(ApplicationContext applicationContext, BundleContext bundleContext) {
        for (DefaultServiceTracker tracker : this.trackers) {
            tracker.close();
        }
        this.trackers = null;
    }
}
