package tech.guyi.ipojo.application.osgi.service.reference;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author guyi
 * OSGI服务获取
 * 在方法上添加此注解，当获取到服务后会调用此方法
 */
@Target({ElementType.ANNOTATION_TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BundleServiceReference {

    /**
     * 要获取的OSGI服务类型
     * @return OSGI服务类型
     */
    Class<?>[] value();

    /**
     * 自定义服务检查的规则
     * @return 服务检查器
     */
    Class<? extends BundleServiceReferenceChecker> checker() default BundleServiceReferenceChecker.class;

}
