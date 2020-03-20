package top.guyi.iot.ipojo.application.osgi.event.interfaces;

import java.util.List;

/**
 * @author guyi
 * 事件内容转换器
 */
public interface EventConverter {

    /**
     * 排序
     * @return 排序数值
     */
    int order();

    /**
     * 检查是否支持此事件类型的转换
     * @param targetClass 事件类型
     * @return 是否支持
     */
    boolean check(Class<? extends Event> targetClass);

    /**
     * 转换事件内容
     * @param eventClass 事件类型
     * @param sourceEvent 原生事件
     * @return 事件实体
     */
    Event convert(Class<? extends Event> eventClass, org.osgi.service.event.Event sourceEvent);

    /**
     * 将IPOJO事件转换为原生事件
     * @param event 事件实体
     * @return 原生事件
     */
    List<org.osgi.service.event.Event> convert(Event event);
}
