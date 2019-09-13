package top.guyi.iot.ipojo.application.osgi.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.ANNOTATION_TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {

    String value() default AbstractLoggerRepository.DEFAULT_LOGGER_KEY;

}
