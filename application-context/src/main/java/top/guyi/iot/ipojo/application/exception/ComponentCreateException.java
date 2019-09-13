package top.guyi.iot.ipojo.application.exception;

public class ComponentCreateException extends RuntimeException {

    public ComponentCreateException(Class<?> classes){
        super(String.format("Component [%s] create err",classes));
    }

}
