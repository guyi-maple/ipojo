package top.guyi.iot.ipojo.application.osgi.event;

import org.osgi.framework.BundleContext;
import org.osgi.service.event.EventAdmin;
import top.guyi.iot.ipojo.application.ApplicationContext;
import top.guyi.iot.ipojo.application.bean.interfaces.ApplicationStartEvent;
import top.guyi.iot.ipojo.application.osgi.event.interfaces.Event;
import top.guyi.iot.ipojo.application.osgi.event.interfaces.EventConverter;
import top.guyi.iot.ipojo.application.osgi.service.reference.BundleServiceReference;

import java.util.*;

public abstract class EventPublisher implements ApplicationStartEvent {

    protected ApplicationContext applicationContext;

    private List<EventConverter> converters = new LinkedList<>();
    protected abstract void setAllEventConverter();
    protected void addConverter(EventConverter converter){
        this.converters.add(converter);
    }

    private EventAdmin admin;
    @BundleServiceReference(EventAdmin.class)
    public void setEventAdmin(EventAdmin admin){
        this.admin = admin;
        this.publishCache();
    }

    private Queue<Event> cache = new LinkedList<>();
    public void publishCache(){
        Event event;
        while ((event = cache.poll()) != null){
            this.publish(event);
        }
    }

    public void publish(Event event){
        if (this.admin == null){
            this.cache.add(event);
        }else{
            List<org.osgi.service.event.Event> target = Collections.emptyList();
            for (EventConverter converter : this.converters) {
                if (converter.check(event.getClass())){
                    target = converter.convert(event);
                    break;
                }
            }
            for (org.osgi.service.event.Event event1 : target) {
                this.post(event1);
            }
        }
    }

    public void post(org.osgi.service.event.Event event){
        this.admin.postEvent(event);
    }

    @Override
    public void onStart(ApplicationContext applicationContext, BundleContext bundleContext) throws Exception {
        this.applicationContext = applicationContext;
        this.setAllEventConverter();
        Collections.sort(this.converters, new Comparator<EventConverter>() {
            @Override
            public int compare(EventConverter o1, EventConverter o2) {
                return Integer.compare(o1.order(),o2.order());
            }
        });
    }
}
