package top.guyi.iot.ipojo.application.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.ANNOTATION_TYPE,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Component {

    String name() default "";

    /**
     * 是否使用代理
     * @return 是否代理
     */
    @Deprecated
    boolean proxy() default false;

    int order() default 999;

}
