package top.guyi.iot.ipojo.application.osgi.service.reference;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.ANNOTATION_TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BundleServiceReference {

    Class<?>[] value();

    Class<? extends BundleServiceReferenceChecker> checker() default BundleServiceReferenceChecker.class;

}
