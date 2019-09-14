package top.guyi.iot.ipojo.application.osgi.event;

import top.guyi.iot.ipojo.application.osgi.event.interfaces.Event;
import top.guyi.iot.ipojo.application.osgi.event.interfaces.EventConverter;

import java.util.Collections;
import java.util.List;

public class DefaultEventConverter implements EventConverter {

    @Override
    public int order() {
        return 999;
    }

    @Override
    public boolean check(Class<?> targetClass) {
        return true;
    }

    @Override
    public Event convert(Class<?> eventClass, org.osgi.service.event.Event sourceEvent) {
        return null;
    }

    @Override
    public List<org.osgi.service.event.Event> convert(Event event) {
        return Collections.emptyList();
    }

}
