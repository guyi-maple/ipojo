package top.guyi.iot.ipojo.application.osgi.configuration.format;

/**
 * @author guyi
 * 类型转换器
 */
public interface TypeFormat<T> {

    /**
     * 类型转换
     * @param value 要转换的数据
     * @return 转换后的数据
     */
    T format(Object value);

}
