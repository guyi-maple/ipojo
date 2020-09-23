package tech.guyi.ipojo.application.bean.interfaces;

import tech.guyi.ipojo.application.ApplicationContext;
import tech.guyi.ipojo.application.bean.BeanInfo;

/**
 * @author guyi
 * 组件创建器
 * @param <T>
 */
public interface BeanCreator<T> {

    /**
     * 排序
     * @return 排序数值
     */
    int order();

    /**
     * 是否支持为指定类型初始化组件对象
     * @param classes 指定类型
     * @return 是否支持
     */
    boolean forType(Class<T> classes);

    /**
     * 为指定类型初始化组件对象
     * @param context 容器上下文
     * @param info 组件信息
     * @param classes 指定类型
     * @return 组件对象
     * @throws Exception
     */
    T create(ApplicationContext context, BeanInfo info,Class<T> classes) throws Exception;

}
