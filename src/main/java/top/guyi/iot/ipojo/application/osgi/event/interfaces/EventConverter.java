package top.guyi.iot.ipojo.application.osgi.event.interfaces;

import java.util.List;

public interface EventConverter {

    int order();

    boolean check(Class<? extends Event> targetClass);

    Event convert(Class<? extends Event> eventClass, org.osgi.service.event.Event sourceEvent);

    List<org.osgi.service.event.Event> convert(Event event);
}
