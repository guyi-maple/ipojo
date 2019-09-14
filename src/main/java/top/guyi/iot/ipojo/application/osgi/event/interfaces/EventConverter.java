package top.guyi.iot.ipojo.application.osgi.event.interfaces;

import java.util.List;

public interface EventConverter {

    int order();

    boolean check(Class<?> targetClass);

    Event convert(Class<?> eventClass, org.osgi.service.event.Event sourceEvent);

    List<org.osgi.service.event.Event> convert(Event event);
}
