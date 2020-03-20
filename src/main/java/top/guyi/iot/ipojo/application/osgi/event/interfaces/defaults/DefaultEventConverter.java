package top.guyi.iot.ipojo.application.osgi.event.interfaces.defaults;

import com.google.gson.Gson;
import top.guyi.iot.ipojo.application.osgi.event.interfaces.Event;
import top.guyi.iot.ipojo.application.osgi.event.interfaces.EventConverter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author guyi
 * 默认的事件转换器
 */
public class DefaultEventConverter implements EventConverter {

    private static final String JSON_CONTENT_NAME = "JSON_CONTENT";

    private static Gson gson = new Gson();

    @Override
    public int order() {
        return 999;
    }

    @Override
    public boolean check(Class<? extends Event> targetClass) {
        return true;
    }

    @Override
    public Event convert(Class<? extends Event> eventClass, org.osgi.service.event.Event sourceEvent) {
        Object json = sourceEvent.getProperty(JSON_CONTENT_NAME);
        if (json != null){
            return gson.fromJson(json.toString(),eventClass);
        }
        return null;
    }

    @Override
    public List<org.osgi.service.event.Event> convert(Event event) {
        Map<String,String> map = Collections.singletonMap(
                JSON_CONTENT_NAME,
                gson.toJson(event)
        );
        List<org.osgi.service.event.Event> events = new LinkedList<>();
        for (String topic : event.topic()) {
            events.add(new org.osgi.service.event.Event(topic,map));
        }
        return events;
    }

}
