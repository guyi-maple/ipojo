package top.guyi.iot.ipojo.application.utils;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author guyi
 * 注解工具
 */
public class AnnotationUtils {

    /**
     * 是否存在指定注解
     * @param annotationClass
     * @param classes
     * @return
     */
    public static boolean hasAnnotation(Class<? extends Annotation> annotationClass,Class<?> classes){
        return getAnnotation(annotationClass,classes) != null;
    }

    /**
     * 是否存在指定注解
     * @param annotationClass
     * @param field
     * @return
     */
    public static boolean hasAnnotation(Class<? extends Annotation> annotationClass,Field field){
        return getAnnotation(annotationClass,field) != null;
    }

    /**
     * 是否存在指定注解
     * @param annotationClass
     * @param method
     * @return
     */
    public static boolean hasAnnotation(Class<? extends Annotation> annotationClass,Method method){
        return getAnnotation(annotationClass,method) != null;
    }

    /**
     * 获取注解
     * @param annotationClass
     * @param classes
     * @param <T>
     * @return
     */
    public static <T extends Annotation> T getAnnotation(Class<T> annotationClass,Class<?> classes){
        if(classes != null && classes != Object.class){
            T annotation = classes.getAnnotation(annotationClass);
            if(annotation == null){
                for (Annotation a : classes.getAnnotations()){
                    if(a instanceof Documented){
                        break;
                    }
                    annotation = getAnnotation(annotationClass,a.annotationType());
                    if(annotation != null){
                        break;
                    }
                }
            }
            if(annotation == null){
                return getAnnotation(annotationClass,classes.getSuperclass());
            }
            return annotation;
        }
        return null;
    }

    /**
     * 获取注解
     * @param annotationClass
     * @param classes
     * @param <T>
     * @return
     */
    public static <T extends Annotation> List<T> getAnnotation(List<T> result,Class<T> annotationClass, Class<?> classes){
        if(classes != null && classes != Object.class && classes != Documented.class){
            T annotation = classes.getAnnotation(annotationClass);
            if(annotation == null){
                for (Annotation a : classes.getAnnotations()){
                    annotation = getAnnotation(annotationClass,a.annotationType());
                    if(annotation != null){
                        break;
                    }
                }
            }
            if(annotation == null){
                getAnnotation(annotationClass,classes.getSuperclass());
            }
            result.add(annotation);
        }
        return result;
    }

    /**
     * 获取注解
     * @param annotationClass
     * @param method
     * @param <T>
     * @return
     */
    public static <T extends Annotation> T getAnnotation(Class<T> annotationClass, Method method){
        T annotation = method.getAnnotation(annotationClass);
        if(annotation == null){
            for (Annotation a : method.getAnnotations()){
                annotation = getAnnotation(annotationClass,a.annotationType());
                if(annotation != null){
                    break;
                }
            }
        }
        return annotation;
    }
    /**
     * 获取注解
     * @param annotationClass
     * @param method
     * @param <T>
     * @return
     */
    public static <T extends Annotation> List<T> getAnnotation(List<T> result,Class<T> annotationClass, Method method){
        T annotation = method.getAnnotation(annotationClass);
        if(annotation != null){
            result.add(annotation);
        }
        for (Annotation a : method.getAnnotations()){
            getAnnotation(result,annotationClass,a.annotationType());
        }
        return result;
    }

    /**
     * 获取注解
     * @param annotationClass
     * @param field
     * @param <T>
     * @return
     */
    public static <T extends Annotation> T getAnnotation(Class<T> annotationClass, Field field){
        T annotation = field.getAnnotation(annotationClass);
        if(annotation == null){
            for (Annotation a : field.getAnnotations()){
                annotation = getAnnotation(annotationClass,a.annotationType());
                if(annotation != null){
                    break;
                }
            }
        }
        return annotation;
    }
    /**
     * 获取注解
     * @param annotationClass
     * @param field
     * @param <T>
     * @return
     */
    public static <T extends Annotation> List<T> getAnnotation(List<T> result,Class<T> annotationClass, Field field){
        T annotation = field.getAnnotation(annotationClass);
        if(annotation != null){
            result.add(annotation);
        }
        for (Annotation a : field.getAnnotations()){
            getAnnotation(result,annotationClass,a.annotationType());
        }
        return result;
    }

    /**
     * 获取注解
     * @param annotationClass
     * @param annotations
     * @param <T>
     * @return
     */
    public static <T extends Annotation> T getAnnotation(Class<T> annotationClass,Annotation[] annotations){
        for (Annotation annotation : annotations){
            if(annotation.annotationType().isAssignableFrom(annotationClass)){
                return (T)annotation;
            }
        }
        return null;
    }

    /**
     * 获取注解默认值
     * @param annotation
     * @return
     */
    public static Object getAnnotationValue(Annotation annotation){
        try {
            Method method = annotation.getClass().getMethod("value");
            return method.invoke(annotation);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
