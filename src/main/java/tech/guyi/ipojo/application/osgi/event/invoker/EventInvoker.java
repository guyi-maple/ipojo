package tech.guyi.ipojo.application.osgi.event.invoker;

import lombok.AllArgsConstructor;
import org.osgi.service.event.EventHandler;
import tech.guyi.ipojo.application.osgi.event.interfaces.Event;
import tech.guyi.ipojo.application.osgi.event.interfaces.EventConverter;
import tech.guyi.ipojo.application.osgi.event.interfaces.EventListener;

import java.util.List;

/**
 * @author guyi
 * 事件监听执行器
 */
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
