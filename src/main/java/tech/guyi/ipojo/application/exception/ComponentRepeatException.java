package tech.guyi.ipojo.application.exception;

/**
 * @author guyi
 * 同种类型存在优先级相同的组件
 */
public class ComponentRepeatException extends RuntimeException {

    public ComponentRepeatException(Class<?> classes){
        super(String.format("Unable to inject duplicate components [%s]",classes));
    }

}
