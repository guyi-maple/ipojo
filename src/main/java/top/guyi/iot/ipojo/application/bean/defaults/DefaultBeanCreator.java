package top.guyi.iot.ipojo.application.bean.defaults;

import top.guyi.iot.ipojo.application.ApplicationContext;
import top.guyi.iot.ipojo.application.bean.BeanInfo;
import top.guyi.iot.ipojo.application.bean.interfaces.BeanCreator;
import net.sf.cglib.proxy.Enhancer;

public class DefaultBeanCreator implements BeanCreator {

    @Override
    public int order() {
        return 999;
    }

    @Override
    public boolean forType(Class classes) {
        return true;
    }

    @Override
    public Object create(ApplicationContext context, BeanInfo info, Class classes) throws Exception {
        if (info.getBean() == null){
            info.setBean(classes.newInstance());
        }

        if (info.getComponent().isProxy()){
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(classes);
            enhancer.setCallback(new DefaultMethodInterceptor(info,context));
            return enhancer.create();
        }else{
            return info.getBean();
        }
    }

}
