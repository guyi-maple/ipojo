package top.guyi.iot.ipojo.application.component;

/**
 * @author guyi
 * 用于Map注入，使用Map注入的组件必须实现此接口
 */
public interface ForType<T> {

    /**
     * Map键值类型
     * @return 键值类型
     */
    T forType();

}
