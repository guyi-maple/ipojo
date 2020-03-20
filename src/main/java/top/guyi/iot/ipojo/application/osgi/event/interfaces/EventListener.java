package top.guyi.iot.ipojo.application.osgi.event.interfaces;

/**
 * @author guyi
 * 事件监听器
 */
public interface EventListener<E extends Event> {

    /**
     * 事件类型
     * @return 事件类型
     */
    Class<E> eventClass();

    /**
     * 接收事件
     * @param event 事件实体
     * @throws Exception
     */
    void onEvent(E event) throws Exception;

    /**
     * 当处理事件出现异常时调用
     * @param event 事件
     * @param e 异常
     */
    void onError(E event,Exception e);

}
