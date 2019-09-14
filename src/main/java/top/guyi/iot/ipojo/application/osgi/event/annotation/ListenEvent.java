package top.guyi.iot.ipojo.application.osgi.event.annotation;

import top.guyi.iot.ipojo.application.osgi.event.interfaces.Event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ListenEvent {

    Class<? extends Event> value();

}
