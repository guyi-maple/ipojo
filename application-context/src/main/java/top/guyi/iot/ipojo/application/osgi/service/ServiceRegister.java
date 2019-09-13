package top.guyi.iot.ipojo.application.osgi.service;

import top.guyi.iot.ipojo.application.ApplicationContext;
import top.guyi.iot.ipojo.application.bean.interfaces.ApplicationStartEvent;
import org.osgi.framework.BundleContext;

public class ServiceRegister implements ApplicationStartEvent {

    public void onStart(ApplicationContext applicationContext, BundleContext bundleContext) {
//        for (Object bean : applicationContext.getAll()) {
//            Service service = AnnotationUtils.getAnnotation(Service.class,bean.getClass());
//            if (service != null){
//                bundleContext.registerService(service.value().getName(),bean,null);
//            }
//
//            if (bean instanceof BundleListener){
//                System.out.println("服务监听器");
//            }
//        }
    }

}
