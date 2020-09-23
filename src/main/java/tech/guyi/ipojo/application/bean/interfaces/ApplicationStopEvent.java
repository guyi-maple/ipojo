package tech.guyi.ipojo.application.bean.interfaces;

import org.osgi.framework.BundleContext;
import tech.guyi.ipojo.application.ApplicationContext;

/**
 * @author guyi
 * 容器停止事件
 */
public interface ApplicationStopEvent {

    /**
     * 当容器停止，即Bundle被调用stop方法时调用此方法
     * @param applicationContext 容器上下文
     * @param bundleContext OSGI上下文
     */
    void onStop(ApplicationContext applicationContext, BundleContext bundleContext);

}
