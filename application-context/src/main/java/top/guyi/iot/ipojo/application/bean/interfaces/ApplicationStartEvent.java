package top.guyi.iot.ipojo.application.bean.interfaces;

import top.guyi.iot.ipojo.application.ApplicationContext;
import org.osgi.framework.BundleContext;

public interface ApplicationStartEvent {

    void onStart(ApplicationContext applicationContext, BundleContext bundleContext) throws Exception;

}
