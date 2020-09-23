package tech.guyi.ipojo.application.bean.interfaces;

import tech.guyi.ipojo.application.ApplicationContext;

/**
 * @author guyi
 * 组件工厂
 */
public interface  FactoryBean<T> {

    /**
     * 组件类型
     * @return 组件类型
     */
    Class<T> forType();

    /**
     * 初始化组件对象
     * @param context 容器上下文
     * @param classes 组件Class
     * @return 组件对象
     */
    T create(ApplicationContext context, Class<T> classes);

}
