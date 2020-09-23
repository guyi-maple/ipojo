package tech.guyi.ipojo.application.exception;

/**
 * @author guyi
 * 组件初始化失败
 */
public class ComponentCreateException extends RuntimeException {

    public ComponentCreateException(Class<?> classes){
        super(String.format("Component [%s] create err",classes));
    }

}
