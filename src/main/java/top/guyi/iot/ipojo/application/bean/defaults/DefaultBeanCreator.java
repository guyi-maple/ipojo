package top.guyi.iot.ipojo.application.bean.defaults;

import top.guyi.iot.ipojo.application.ApplicationContext;
import top.guyi.iot.ipojo.application.bean.BeanInfo;
import top.guyi.iot.ipojo.application.bean.interfaces.BeanCreator;

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
        return info.getBean();
    }

}
