package tech.guyi.ipojo.application.exception;

/**
 * @author guyi
 * 容器中找不到指定组件
 */
public class ComponentNotFoundException extends RuntimeException {

    public ComponentNotFoundException(Class<?> classes,String name){
        super(String.format("Component [%s][%s] is not found",classes,name));
    }

}
