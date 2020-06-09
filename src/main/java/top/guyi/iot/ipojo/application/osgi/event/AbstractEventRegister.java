package top.guyi.iot.ipojo.application.osgi.event;

import lombok.Getter;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import top.guyi.iot.ipojo.application.ApplicationContext;
import top.guyi.iot.ipojo.application.bean.interfaces.ApplicationStartEvent;
import top.guyi.iot.ipojo.application.bean.interfaces.ApplicationStopEvent;
import top.guyi.iot.ipojo.application.osgi.event.interfaces.Event;
import top.guyi.iot.ipojo.application.osgi.event.interfaces.EventConverter;
import top.guyi.iot.ipojo.application.osgi.event.interfaces.EventListener;
import top.guyi.iot.ipojo.application.osgi.event.invoker.EventInvoker;
import top.guyi.iot.ipojo.application.osgi.event.invoker.AbstractMethodEventInvoker;
import top.guyi.iot.ipojo.application.osgi.event.invoker.AbstractMethodNativeEventInvoker;

import java.util.*;

/**
 * @author guyi
 * 事件内容转换器
 */
public abstract class AbstractEventRegister implements ApplicationStartEvent, ApplicationStopEvent {

    protected ApplicationContext applicationContext;
    protected BundleContext bundleContext;

    @Getter
    private final List<EventConverter> converters = new LinkedList<>();

    private final List<ServiceRegistration<?>> registrations = new LinkedList<>();

    /**
     * 注册所有事件内容转换者
     */
    protected abstract void setAllConverter();

    protected void setConverter(EventConverter converter){
        this.converters.add(converter);
    }

    /**
     * 注册所有事件监听器
     */
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

    /**
     * 注册所有方法事件监听器
     */
    protected abstract void registerAllMethodListener();

    protected void registerMethodListener(BundleContext bundleContext, AbstractMethodEventInvoker invoker){
        try {
            String[] topic = invoker.getEventClass().newInstance().topic();
            Dictionary<String,Object> props = new Hashtable<>();
            props.put(EventConstants.EVENT_TOPIC,topic);
            invoker.setConverters(this.converters);
            this.registrations.add(bundleContext.registerService(EventHandler.class.getName(),invoker,props));
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    protected void registerNativeMethodListener(BundleContext bundleContext, AbstractMethodNativeEventInvoker invoker){
        String[] topic = new String[]{invoker.getTopic()};
        Dictionary<String,Object> props = new Hashtable<>();
        props.put(EventConstants.EVENT_TOPIC,topic);
        bundleContext.registerService(EventHandler.class.getName(),invoker,props);
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

    @Override
    public void onStop(ApplicationContext applicationContext, BundleContext bundleContext) {
        for (ServiceRegistration<?> registration : this.registrations) {
            registration.unregister();
        }
    }
}
