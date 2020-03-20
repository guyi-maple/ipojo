package top.guyi.iot.ipojo.application.osgi.service.reference;

import top.guyi.iot.ipojo.application.ApplicationContext;
import org.osgi.framework.BundleContext;

/**
 * @author guyi
 * OSGI服务检查者
 */
public interface BundleServiceReferenceChecker {

    /**
     * 检查OSGI服务是否满足条件
     * @param entry OSGI服务信息
     * @param applicationContext 容器上下文
     * @param bundleContext OSGI上下文
     * @return 是否满足
     */
    boolean check(ServiceReferenceEntry entry, ApplicationContext applicationContext, BundleContext bundleContext);

}
