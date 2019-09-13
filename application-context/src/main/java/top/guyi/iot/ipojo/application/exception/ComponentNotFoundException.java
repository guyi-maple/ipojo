package top.guyi.iot.ipojo.application.exception;

public class ComponentNotFoundException extends RuntimeException {

    public ComponentNotFoundException(Class<?> classes,String name){
        super(String.format("Component [%s][%s] is not found",classes,name));
    }

}
