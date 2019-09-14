package top.guyi.iot.ipojo.application.exception;

public class ComponentRepeatException extends RuntimeException {

    public ComponentRepeatException(Class<?> classes){
        super(String.format("Unable to inject duplicate components [%s]",classes));
    }

}
