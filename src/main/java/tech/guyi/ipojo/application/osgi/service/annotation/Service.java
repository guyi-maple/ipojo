package tech.guyi.ipojo.application.osgi.service.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author guyi
 * 在组件上使用此注解，组件会被以OSGI服务的形式注册到OSGI容器中
 */
@Target({ElementType.ANNOTATION_TYPE,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Service {

    /**
     * 注册服务使用的接口类型
     * 值必须是组件已经实现的接口
     * @return 接口类型
     */
    Class<?> value();

    /**
     * 是否将接口类型的包名添加到MANIFEST.MF文件的Export-Package字段中
     * @return 是否添加
     */
    boolean export() default true;

    /**
     * 导出的版本号
     * @return 版本号
     */
    String version() default "";

}
