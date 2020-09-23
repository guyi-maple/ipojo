package tech.guyi.ipojo.application.osgi.service.reference;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import tech.guyi.ipojo.application.ApplicationContext;

import java.util.List;

/**
 * @author guyi
 * 默认的OSGI服务追踪器
 */
public class DefaultServiceTracker extends ServiceTracker {

    private ApplicationContext applicationContext;
    private BundleContext bundleContext;
    private List<ServiceReferenceEntry> entries;

    public DefaultServiceTracker(ApplicationContext applicationContext,BundleContext bundleContext, Class<?> serviceClass, List<ServiceReferenceEntry> entries) {
        super(bundleContext,serviceClass.getName(),null);
        this.bundleContext = bundleContext;
        this.applicationContext = applicationContext;
        this.entries = entries;
    }

    @Override
    public Object addingService(ServiceReference reference) {
        for (ServiceReferenceEntry entry : entries) {
            if (entry.getInvoker().check(entry,applicationContext,bundleContext)){
                entry.getInvoker().invoke(entry,applicationContext,bundleContext);
            }
        }
        return super.addingService(reference);
    }
}
