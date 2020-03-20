package top.guyi.iot.ipojo.application.osgi.configuration.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author guyi
 * 配置项
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.CLASS)
public @interface ConfigurationKey {

    /**
     * 配置项的名称
     * @return 配置项的名称
     */
    String key() default "";

    /**
     * 是否在运行时从文件中读取
     * @return 是否在运行时从文件中读取
     */
    boolean file() default false;

}
