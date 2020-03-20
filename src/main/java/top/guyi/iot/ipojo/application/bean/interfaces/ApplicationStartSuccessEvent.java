package top.guyi.iot.ipojo.application.bean.interfaces;

import org.osgi.framework.BundleContext;
import top.guyi.iot.ipojo.application.ApplicationContext;

/**
 * @author guyi
 * 容器启动成功事件
 */
public interface ApplicationStartSuccessEvent {

    /**
     * 当容器完成所有操作且启动完成后调用此方法
     * @param applicationContext 容器上下文
     * @param bundleContext OSGI上下文
     * @throws Exception
     */
    void onStartSuccess(ApplicationContext applicationContext, BundleContext bundleContext) throws Exception;

}
