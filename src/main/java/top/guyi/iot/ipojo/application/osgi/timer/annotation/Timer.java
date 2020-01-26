package top.guyi.iot.ipojo.application.osgi.timer.annotation;

import top.guyi.iot.ipojo.application.osgi.timer.enums.TimeType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface Timer {

    String name() default "";

    int delay();

    int initDelay() default -1;

    TimeType type() default TimeType.CYCLE;

}
