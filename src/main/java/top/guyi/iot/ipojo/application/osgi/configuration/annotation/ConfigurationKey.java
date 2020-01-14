package top.guyi.iot.ipojo.application.osgi.configuration.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.CLASS)
public @interface ConfigurationKey {

    String key() default "";

    boolean file() default false;

}
