package top.guyi.iot.ipojo.application.bean.interfaces;

import top.guyi.iot.ipojo.application.ApplicationContext;
import org.osgi.framework.BundleContext;

/**
 * @author guyi
 * 容器启动事件
 */
public interface ApplicationStartEvent {

    /**
     * 当容器启动并完成注入时调用此方法
     * @param applicationContext 容器上下文
     * @param bundleContext OSGI上下文
     * @throws Exception
     */
    void onStart(ApplicationContext applicationContext, BundleContext bundleContext) throws Exception;

}
