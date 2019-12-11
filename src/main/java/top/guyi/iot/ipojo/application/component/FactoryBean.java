package top.guyi.iot.ipojo.application.component;

public interface FactoryBean {

    <T> T get(Class<T> classes);

}
