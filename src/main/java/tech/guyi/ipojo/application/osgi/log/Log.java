package tech.guyi.ipojo.application.osgi.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author guyi
 * 注入OSGI-logger，在类型为org.osgi.service.log.Logger的字段上添加此注解，在组件注入时会自动注入logger
 */
@Target({ElementType.ANNOTATION_TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {

    String value() default AbstractLoggerRepository.DEFAULT_LOGGER_KEY;

}
