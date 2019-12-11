package top.guyi.iot.ipojo.application.osgi.configuration.format;

public interface TypeFormat<T> {

    T format(Object value);

}
