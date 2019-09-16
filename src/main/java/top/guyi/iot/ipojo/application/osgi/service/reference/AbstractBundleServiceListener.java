package top.guyi.iot.ipojo.application.osgi.service.reference;

import top.guyi.iot.ipojo.application.ApplicationContext;
import top.guyi.iot.ipojo.application.bean.interfaces.ApplicationStartEvent;
import org.osgi.framework.BundleContext;
import top.guyi.iot.ipojo.application.bean.interfaces.ApplicationStartSuccessEvent;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class AbstractBundleServiceListener implements ApplicationStartEvent, ApplicationStartSuccessEvent {

    private ApplicationContext applicationContext;
    private BundleContext bundleContext;
    private ScheduledExecutorService executorService;

    protected abstract void registerAll(ApplicationContext applicationContext);

    private Map<String,ServiceReferenceEntry> entries;

    protected void register(ServiceReferenceEntry entry){
        if (entry.getInvoker().check(entry,applicationContext,bundleContext)){
            entry.getInvoker().invoke(entry,applicationContext,bundleContext);
        }else{
            entries.put(entry.getId(),entry);
        }
    }

    public void onStart(final ApplicationContext applicationContext, final BundleContext bundleContext) throws Exception {
        this.applicationContext = applicationContext;
        this.bundleContext = bundleContext;
        this.entries = new HashMap<>();
        this.registerAll(applicationContext);
        this.executorService = applicationContext.get(ScheduledExecutorService.class,true);
    }

    @Override
    public void onEvent(final ApplicationContext applicationContext, final BundleContext bundleContext) throws Exception {
        this.executorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                List<String> removeId = new LinkedList<>();
                for (final ServiceReferenceEntry entry : entries.values()) {
                    if (entry.getInvoker().check(entry,applicationContext,bundleContext)){
                        executorService.execute(new Runnable() {
                            @Override
                            public void run() {
                                entry.getInvoker().invoke(entry,applicationContext,bundleContext);
                            }
                        });
                        removeId.add(entry.getId());
                    }
                }
                for (String id : removeId) {
                    entries.remove(id);
                }
            }
        },0,10, TimeUnit.SECONDS);
    }
}
