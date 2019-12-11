package top.guyi.iot.ipojo.application.osgi.event.invoker;

import lombok.AllArgsConstructor;
import lombok.Setter;
import org.osgi.service.event.EventHandler;
import top.guyi.iot.ipojo.application.osgi.event.interfaces.Event;
import top.guyi.iot.ipojo.application.osgi.event.interfaces.EventConverter;
import top.guyi.iot.ipojo.application.osgi.event.interfaces.EventListener;

import java.util.List;

@AllArgsConstructor
public class EventInvoker implements EventHandler {

    private EventListener listener;
    private List<EventConverter> converters;

    @Override
    public void handleEvent(org.osgi.service.event.Event sourceEvent){
        Event event = null;
        for (EventConverter converter : converters) {
            if (converter.check(listener.eventClass())){
                event = converter.convert(listener.eventClass(),sourceEvent);
                break;
            }
        }
        try{
            this.listener.onEvent(event);
        }catch (Exception e){
            this.listener.onError(event,e);
        }
    }

}
