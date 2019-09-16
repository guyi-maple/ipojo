package top.guyi.iot.ipojo.application.osgi.event;

import lombok.Getter;
import org.osgi.framework.BundleContext;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import top.guyi.iot.ipojo.application.ApplicationContext;
import top.guyi.iot.ipojo.application.bean.interfaces.ApplicationStartEvent;
import top.guyi.iot.ipojo.application.osgi.event.interfaces.Event;
import top.guyi.iot.ipojo.application.osgi.event.interfaces.EventConverter;
import top.guyi.iot.ipojo.application.osgi.event.interfaces.EventListener;
import top.guyi.iot.ipojo.application.osgi.event.invoker.EventInvoker;
import top.guyi.iot.ipojo.application.osgi.event.invoker.MethodEventInvoker;

import java.util.*;

public abstract class EventRegister implements ApplicationStartEvent {

    protected ApplicationContext applicationContext;
    protected BundleContext bundleContext;

    @Getter
    private List<EventConverter> converters = new LinkedList<>();
    protected abstract void setAllConverter();
    protected void setConverter(EventConverter converter){
        this.converters.add(converter);
    }

    protected abstract void registerAllListener();
    protected void registerListener(BundleContext bundleContext,EventListener listener){
        try {
            String[] topic = ((Event)listener.eventClass().newInstance()).topic();
            Dictionary<String,Object> props = new Hashtable<>();
            props.put(EventConstants.EVENT_TOPIC,topic);
            bundleContext.registerService(EventHandler.class.getName(),new EventInvoker(listener,this.converters),props);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    protected abstract void registerAllMethodListener();
    protected void registerMethodListener(BundleContext bundleContext, MethodEventInvoker invoker){
        try {
            String[] topic = invoker.getEventClass().newInstance().topic();
            Dictionary<String,Object> props = new Hashtable<>();
            props.put(EventConstants.EVENT_TOPIC,topic);
            invoker.setConverters(this.converters);
            bundleContext.registerService(EventHandler.class.getName(),invoker,props);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart(ApplicationContext applicationContext, BundleContext bundleContext) {
        this.applicationContext = applicationContext;
        this.bundleContext = bundleContext;

        this.setAllConverter();
        Collections.sort(this.converters, new Comparator<EventConverter>() {
            @Override
            public int compare(EventConverter o1, EventConverter o2) {
                return Integer.compare(o1.order(),o2.order());
            }
        });
        this.registerAllListener();
        this.registerAllMethodListener();
    }

}
