package top.guyi.iot.ipojo.application.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author guyi
 * 组件注入组件，在字段上添加此注解，组件初始化完成后会自动注入
 */
@Target({ElementType.ANNOTATION_TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Resource {

    /**
     * 只注入名称为此属性值的组件
     * @return 名称
     */
    String name() default "";

    /**
     * 是否使用字段类型（Class）直接正在组件容器中完全匹配，忽略可能存在的子类。接口或抽线类不能使用此属性
     * @return 是否完全匹配
     */
    boolean equals() default true;

}
