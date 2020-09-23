package tech.guyi.ipojo.application.component.condition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author guyi
 * 在组件上添加，当容器中已经有指定类型的组件时不注册当前组件
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface ConditionOnMissBean {

    Class<?> value();

}
