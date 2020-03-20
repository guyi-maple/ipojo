package top.guyi.iot.ipojo.application.component;

/**
 * @author guyi
 * 组件工厂
 */
public interface FactoryBean {

    /**
     * 获取组件对象
     * @param classes 组件类型
     * @param <T>
     * @return 组件对象
     */
    <T> T get(Class<T> classes);

}
