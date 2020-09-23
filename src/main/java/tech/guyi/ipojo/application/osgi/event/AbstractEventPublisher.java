package tech.guyi.ipojo.application.osgi.event;

import org.osgi.framework.BundleContext;
import org.osgi.service.event.EventAdmin;
import tech.guyi.ipojo.application.osgi.event.interfaces.Event;
import tech.guyi.ipojo.application.osgi.event.interfaces.EventConverter;
import tech.guyi.ipojo.application.ApplicationContext;
import tech.guyi.ipojo.application.bean.interfaces.ApplicationStartEvent;
import tech.guyi.ipojo.application.osgi.service.reference.BundleServiceReference;

import java.util.*;

/**
 * @author guyi
 * 事件发布者
 */
public abstract class AbstractEventPublisher implements ApplicationStartEvent {

    protected ApplicationContext applicationContext;

    private List<EventConverter> converters = new LinkedList<>();

    /**
     * 设置所有事件转换器
     */
    protected abstract void setAllEventConverter();

    /**
     * 添加事件转换器
     * @param converter 事件转换器
     */
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
