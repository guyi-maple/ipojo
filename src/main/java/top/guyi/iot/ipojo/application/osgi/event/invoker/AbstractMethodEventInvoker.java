package top.guyi.iot.ipojo.application.osgi.event.invoker;

import lombok.Getter;
import lombok.Setter;
import org.osgi.service.event.EventHandler;
import top.guyi.iot.ipojo.application.ApplicationContext;
import top.guyi.iot.ipojo.application.osgi.event.interfaces.Event;
import top.guyi.iot.ipojo.application.osgi.event.interfaces.EventConverter;
import top.guyi.iot.ipojo.application.osgi.log.StaticLogger;

import java.util.List;

/**
 * @author guyi
 * 方法事件执行者
 */
public abstract class AbstractMethodEventInvoker implements EventHandler {

    @Getter
    private Class<? extends Event> eventClass;
    private ApplicationContext applicationContext;
    public AbstractMethodEventInvoker(Class<? extends Event> eventClass, ApplicationContext applicationContext) {
        this.eventClass = eventClass;
        this.applicationContext = applicationContext;
    }

    @Setter
    private List<EventConverter> converters;

    /**
     * 指定事件监听方法
     * @param context 容器上下文
     * @param event 事件实体
     * @throws Exception
     */
    protected abstract void invoke(ApplicationContext context,Event event) throws Exception;

    @Override
    public void handleEvent(org.osgi.service.event.Event sourceEvent){
        Event event = null;
        for (EventConverter converter : converters) {
            if (converter.check(this.eventClass)){
                event = converter.convert(this.eventClass,sourceEvent);
                break;
            }
        }
        if (event != null){
            try {
                this.invoke(this.applicationContext,event);
            }catch (Exception e){
                e.printStackTrace();
                StaticLogger.error("事件执行错误 {}",e.getMessage(),e);
            }
        }
    }

}
