package top.guyi.iot.ipojo.application.bean.interfaces;

import org.osgi.framework.BundleContext;
import top.guyi.iot.ipojo.application.ApplicationContext;

public interface ApplicationStartSuccessEvent {

    void onEvent(ApplicationContext applicationContext, BundleContext bundleContext) throws Exception;

}
