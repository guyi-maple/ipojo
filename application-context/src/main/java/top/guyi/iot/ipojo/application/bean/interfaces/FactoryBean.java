package top.guyi.iot.ipojo.application.bean.interfaces;

import top.guyi.iot.ipojo.application.ApplicationContext;

public interface  FactoryBean<T> {

    Class<T> forType();

    T create(ApplicationContext context, Class<T> classes);

}
