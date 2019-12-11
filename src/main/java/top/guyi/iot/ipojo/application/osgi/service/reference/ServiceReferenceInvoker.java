package top.guyi.iot.ipojo.application.osgi.service.reference;

import top.guyi.iot.ipojo.application.ApplicationContext;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public abstract class ServiceReferenceInvoker {

    public abstract void invoke(ServiceReferenceEntry entry, ApplicationContext applicationContext, BundleContext bundleContext);

    protected <T> T get(Class<T> classes,ApplicationContext applicationContext, BundleContext bundleContext){
        T value = applicationContext.getOrNull(classes);
        if (value == null){
            ServiceReference reference = bundleContext.getServiceReference(classes.getName());
            if (reference != null){
                value = classes.cast(bundleContext.getService(reference));
            }
        }
        return value;
    }

    public boolean check(ServiceReferenceEntry entry,ApplicationContext applicationContext, BundleContext bundleContext){
        if (entry.getChecker() != null){
            return entry.getChecker().check(entry,applicationContext,bundleContext);
        }

        for (Class<?> serviceClass : entry.getServiceClasses()) {
            if (this.get(serviceClass,applicationContext,bundleContext) == null){
                return false;
            }
        }
        return true;
    }



}
