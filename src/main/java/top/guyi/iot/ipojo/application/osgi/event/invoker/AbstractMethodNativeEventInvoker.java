package top.guyi.iot.ipojo.application.osgi.event.invoker;

import lombok.Getter;
import org.osgi.service.event.EventHandler;
import top.guyi.iot.ipojo.application.ApplicationContext;
import top.guyi.iot.ipojo.application.osgi.event.NativeEvent;
import top.guyi.iot.ipojo.application.osgi.log.StaticLogger;

/**
 * @author guyi
 * OSGI原生事件方法监听执行器
 */
public abstract class AbstractMethodNativeEventInvoker implements EventHandler {

    @Getter
    private String topic;
    private ApplicationContext applicationContext;
    public AbstractMethodNativeEventInvoker(String topic, ApplicationContext applicationContext) {
        this.topic = topic;
        this.applicationContext = applicationContext;
    }

    /**
     * 执行事件监听方法
     * @param context 容器上下文
     * @param event 事件实体
     * @throws Exception
     */
    protected abstract void invoke(ApplicationContext context, NativeEvent event) throws Exception;

    @Override
    public void handleEvent(org.osgi.service.event.Event sourceEvent){
        try {
            this.invoke(this.applicationContext,new NativeEvent(sourceEvent));
        }catch (Exception e){
            e.printStackTrace();
            StaticLogger.error("事件执行错误 {}",e.getMessage(),e);
        }
    }

}
