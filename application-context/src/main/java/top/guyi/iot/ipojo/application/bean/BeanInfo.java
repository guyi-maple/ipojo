package top.guyi.iot.ipojo.application.bean;

import top.guyi.iot.ipojo.application.ApplicationContext;
import top.guyi.iot.ipojo.application.annotation.Primary;
import top.guyi.iot.ipojo.application.bean.interfaces.ApplicationStartEvent;
import top.guyi.iot.ipojo.application.bean.interfaces.InitializingBean;
import top.guyi.iot.ipojo.application.component.ComponentInterface;
import top.guyi.iot.ipojo.application.utils.AnnotationUtils;
import lombok.Data;
import org.osgi.framework.BundleContext;

@Data
public class BeanInfo {

    private String name;
    private Class<?> classes;

    private boolean initializing;
    private boolean primary;
    private boolean inject;

    private Object bean;
    private Object target;

    public BeanInfo(Class<?> classes) {
        this.classes = classes;
        this.primary = AnnotationUtils.hasAnnotation(Primary.class,classes);
    }

    public void initializingBean(){
        if (bean != null){
            if (!initializing && bean instanceof InitializingBean){
                ((InitializingBean) bean).afterPropertiesSet();
            }
            initializing = true;
        }
    }

    public boolean isCreate(){
        return this.target != null;
    }

    public void inject(ApplicationContext context){
        if (bean != null){
            if (!inject && bean instanceof ComponentInterface){
                ((ComponentInterface) bean).inject(context);
            }
            inject = true;
        }
    }

    public void onStart(ApplicationContext applicationContext, BundleContext bundleContext) throws Exception {
        if (this.bean instanceof ApplicationStartEvent){
            ((ApplicationStartEvent) this.bean).onStart(applicationContext,bundleContext);
        }
    }
}
