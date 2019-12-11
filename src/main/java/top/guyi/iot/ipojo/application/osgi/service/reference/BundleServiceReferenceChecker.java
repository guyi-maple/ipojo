package top.guyi.iot.ipojo.application.osgi.service.reference;

import top.guyi.iot.ipojo.application.ApplicationContext;
import org.osgi.framework.BundleContext;

public interface BundleServiceReferenceChecker {

    boolean check(ServiceReferenceEntry entry, ApplicationContext applicationContext, BundleContext bundleContext);

}
