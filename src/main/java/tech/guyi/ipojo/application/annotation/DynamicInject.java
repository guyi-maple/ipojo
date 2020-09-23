package tech.guyi.ipojo.application.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author guyi
 * 动态注入
 * 在类上添加此注解，当此组件没有被引用时，将不会被注册到容器中
 */
@Target({ElementType.ANNOTATION_TYPE,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DynamicInject {

    boolean superEquals() default true;

}
