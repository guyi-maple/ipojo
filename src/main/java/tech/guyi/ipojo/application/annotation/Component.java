package tech.guyi.ipojo.application.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author guyi
 * 注册组件
 * 在类上添加此注解，此类将会以组件的形式注册到容器中
 */
@Target({ElementType.ANNOTATION_TYPE,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Component {

    /**
     * 组件名称
     * @return 组件名称
     */
    String name() default "";

    /**
     * 是否使用代理
     * @return 是否代理
     */
    @Deprecated
    boolean proxy() default false;

    /**
     * 注入排序
     * @return 排序数值
     */
    int order() default 999;

}
