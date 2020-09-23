package tech.guyi.ipojo.application.bean;

import tech.guyi.ipojo.application.ApplicationContext;
import tech.guyi.ipojo.application.bean.interfaces.InitializingBean;
import tech.guyi.ipojo.application.annotation.Primary;
import tech.guyi.ipojo.application.component.ComponentInterface;
import tech.guyi.ipojo.application.utils.AnnotationUtils;
import lombok.Data;

/**
 * @author guyi
 * 组件信息
 */
@Data
public class BeanInfo {

    private ComponentInfo component;

    private String name;
    private Class<?> classes;

    private boolean initializing;
    private boolean primary;
    private boolean inject;

    private Object bean;
    private Object target;

    public BeanInfo(Class<?> classes,ComponentInfo component) {
        this.component = component;
        this.name = this.component.getName();
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

}
