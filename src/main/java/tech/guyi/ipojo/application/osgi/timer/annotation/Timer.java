package tech.guyi.ipojo.application.osgi.timer.annotation;

import tech.guyi.ipojo.application.osgi.timer.enums.TimeType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @author guyi
 * 定时任务注解，在方法上添加此注解，此方法就会被作为定时任务调用
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface Timer {

    /**
     * 定时任务的名称
     * @return 名称
     */
    String name() default "";

    /**
     * 执行间隔时间
     * @return 间隔时间
     */
    int delay();

    /**
     * 任务开始延迟时间
     * @return 延迟时间
     */
    int initDelay() default 0;

    /**
     * 任务执行方式
     * @return 执行方式
     */
    TimeType type() default TimeType.CYCLE;

    /**
     * 时间单位
     * @return 时间单位
     */
    TimeUnit unit() default TimeUnit.MINUTES;

}
