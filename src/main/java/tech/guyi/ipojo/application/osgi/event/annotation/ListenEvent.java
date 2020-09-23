package tech.guyi.ipojo.application.osgi.event.annotation;

import tech.guyi.ipojo.application.osgi.event.interfaces.Event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author guyi
 * 事件监听，在方法上添加此注解，当接收到指定事件时调用此方法
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ListenEvent {

    Class<? extends Event> value();

}
