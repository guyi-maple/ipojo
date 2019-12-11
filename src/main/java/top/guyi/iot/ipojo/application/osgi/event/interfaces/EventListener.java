package top.guyi.iot.ipojo.application.osgi.event.interfaces;

public interface EventListener<E extends Event> {

    Class<E> eventClass();

    void onEvent(E event) throws Exception;

    void onError(E event,Exception e);

}
