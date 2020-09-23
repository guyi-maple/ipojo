package tech.guyi.ipojo.application.bean.defaults;

import tech.guyi.ipojo.application.bean.interfaces.BeanCreator;
import tech.guyi.ipojo.application.ApplicationContext;
import tech.guyi.ipojo.application.bean.BeanInfo;

/**
 * @author guyi
 * 默认的组件创建器
 */
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
