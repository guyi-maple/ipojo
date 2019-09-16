package top.guyi.iot.ipojo.application;

import top.guyi.iot.ipojo.application.bean.BeanInfo;
import top.guyi.iot.ipojo.application.bean.ComponentInfo;
import top.guyi.iot.ipojo.application.utils.StringUtils;

public class ApplicationContextRegister {

    private ApplicationContext applicationContext;
    public ApplicationContextRegister(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ApplicationContextRegister put(Object bean){
        this.put(bean.getClass(),bean);
        return this;
    }
    public ApplicationContextRegister put(Class<?> classes){
        this.put(classes,null,null);
        return this;
    }
    public ApplicationContextRegister put(Class<?> classes,Object bean){
        if (classes == null){
            classes = bean.getClass();
        }
        this.put(classes,bean,new ComponentInfo(
                classes.getSimpleName(),false
        ));
        return this;
    }
    public ApplicationContextRegister put(Class<?> classes,ComponentInfo component){
        this.put(classes,null,component);
        return this;
    }

    public ApplicationContextRegister put(Class<?> classes,Object bean,ComponentInfo component){
        BeanInfo info;
        if (component == null){
            info = new BeanInfo(classes,new ComponentInfo(classes.getSimpleName()));
        }else{
            info = new BeanInfo(classes,component);
        }
        if (bean != null){
            info.setBean(bean);
        }
        this.put(info);
        return this;
    }

    public ApplicationContextRegister put(BeanInfo bean){
        this.applicationContext.beanInfoMap.put(bean.getClasses(),bean);
        try {
            this.applicationContext.isBeanCreator(bean);
            this.applicationContext.isFactoryBean(bean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public ApplicationContext end(){
        return this.applicationContext;
    }

}
