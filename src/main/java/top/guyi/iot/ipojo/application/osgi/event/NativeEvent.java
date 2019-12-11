package top.guyi.iot.ipojo.application.osgi.event;

import org.osgi.service.event.Event;

import java.util.*;

public class NativeEvent implements top.guyi.iot.ipojo.application.osgi.event.interfaces.Event {

    private Event event;

    public NativeEvent(){
    }
    public NativeEvent(Event event){
        this.event = event;
    }

    @Override
    public String[] topic() {
        return new String[]{this.event.getTopic()};
    }

    public <T> T get(String key){
        return (T) this.event.getProperty(key);
    }

    public Set<String> keySet(){
        return new HashSet<>(Arrays.asList(this.event.getPropertyNames()));
    }

    public Map<String,Object> all(){
        Map<String,Object> data = new HashMap<>();
        for (String key : this.keySet()) {
            data.put(key,this.get(key));
        }
        return data;
    }

}
