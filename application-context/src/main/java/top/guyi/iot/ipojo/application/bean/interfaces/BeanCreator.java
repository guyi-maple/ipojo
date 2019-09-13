package top.guyi.iot.ipojo.application.bean.interfaces;

import top.guyi.iot.ipojo.application.ApplicationContext;
import top.guyi.iot.ipojo.application.bean.BeanInfo;

public interface BeanCreator<T> {

    int order();

    boolean forType(Class<T> classes);

    T create(ApplicationContext context, BeanInfo info,Class<T> classes) throws Exception;

}
