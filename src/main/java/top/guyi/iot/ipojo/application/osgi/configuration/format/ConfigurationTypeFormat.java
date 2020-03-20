package top.guyi.iot.ipojo.application.osgi.configuration.format;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * @author guyi
 * 配置内容转换器
 */
public class ConfigurationTypeFormat {

    private Gson gson = new Gson();
    private Map<Class<?>,TypeFormat> formatMap;

    public ConfigurationTypeFormat(){
        this.formatMap = new HashMap<>();
        this.addFormat();
    }

    public Object format(Class<?> type,Object value){
        if (this.formatMap.containsKey(type)){
            return this.formatMap.get(type).format(value);
        }
        return gson.fromJson(gson.toJson(value),type);
    }

    private void addFormat(){
        TypeFormat<Integer> integerTypeFormat = new TypeFormat<Integer>() {
            @Override
            public Integer format(Object value) {
                return value == null ? 0 : Integer.parseInt(value.toString());
            }
        };
        this.formatMap.put(Integer.class,integerTypeFormat);
        this.formatMap.put(int.class,integerTypeFormat);

        TypeFormat<Double> doubleTypeFormat = new TypeFormat<Double>() {
            @Override
            public Double format(Object value) {
                return value == null ? 0 : Double.parseDouble(value.toString());
            }
        };
        this.formatMap.put(Double.class,doubleTypeFormat);
        this.formatMap.put(double.class,doubleTypeFormat);

        TypeFormat<Float> floatTypeFormat = new TypeFormat<Float>() {
            @Override
            public Float format(Object value) {
                return value == null ? 0 : Float.parseFloat(value.toString());
            }
        };
        this.formatMap.put(Float.class,floatTypeFormat);
        this.formatMap.put(float.class,floatTypeFormat);

        TypeFormat<Boolean> booleanTypeFormat = new TypeFormat<Boolean>() {
            @Override
            public Boolean format(Object value) {
                return value != null && Boolean.parseBoolean(value.toString());
            }
        };
        this.formatMap.put(Boolean.class,booleanTypeFormat);
        this.formatMap.put(boolean.class,booleanTypeFormat);

        TypeFormat<String> stringTypeFormat = new TypeFormat<String>() {
            @Override
            public String format(Object value) {
                return value == null ? null : value.toString();
            }
        };
        this.formatMap.put(String.class,stringTypeFormat);
    }



}
