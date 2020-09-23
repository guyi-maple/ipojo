package tech.guyi.ipojo.application.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

/**
 * @author guyi
 * 反射工具
 */
public class ReflectUtils {

    /**
     * source是否为target的子类或接口实现者
     * @param source
     * @param target
     * @return
     */
    public static boolean subordinate(Class<?> source,Class<?> target){
        if(target == null || source == null){
            return false;
        }
        return target.isAssignableFrom(source);
    }

    /**
     * 获取字段泛型
     * @param field
     * @return
     */
    public static Class<?> getGeneric(Field field){
        return getGeneric(field.getGenericType());
    }
    public static Class<?> getGeneric(Type type){
        try {
            Type[] types = (Type[])type.getClass().getMethod("getActualTypeArguments").invoke(type);
            return (Class<?>)types[0];
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取字段泛型
     * @param field
     * @return
     */
    public static List<Class<?>> getGenerics(Field field){
        return getGenerics(field.getGenericType());
    }

    public static List<Class<?>> getGenerics(Type type){
        List<Class<?>> list = new LinkedList<>();
        try {
            Type[] types = (Type[])type.getClass().getMethod("getActualTypeArguments").invoke(type);
            for (Type t : types) {
                list.add((Class<?>)t);
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static Set<Class<?>> getSuperClasses(Class<?> classes){
        Set<Class<?>> classList = new HashSet<>();
        getSuperClasses(classes,classList);
        return classList;
    }
    private static void getSuperClasses(Class<?> classes,Set<Class<?>> classList){
        Class<?> tmp = classes.getSuperclass();
        if(tmp != null && tmp != Object.class && tmp != Annotation.class){
            classList.add(tmp);
            getSuperClasses(tmp,classList);
        }
        Class<?>[] arr = classes.getInterfaces();
        if(arr != null && arr.length > 0){
            for (Class<?> clazz : arr) {
                classList.add(clazz);
                getSuperClasses(clazz,classList);
            }
        }
    }

    /**
     * 根据注解获取方法
     * @param annotationClass
     * @param classes
     * @return
     */
    public static List<Method> getMethodWithAnnotation(Class<? extends Annotation> annotationClass,Class<?> classes){
        Set<Method> methods = new HashSet<>(Arrays.asList(classes.getMethods()));
        methods.addAll(Arrays.asList(classes.getDeclaredMethods()));

        List<Method> result = new LinkedList<>();
        for (Method method : methods){
            if(AnnotationUtils.getAnnotation(annotationClass,method) != null){
                result.add(method);
            }
        }

        return result;
    }

    /**
     * 根据注解获取字段
     * @param classes
     * @param fieldList 存储集合
     */
    public static void getFields(Class<?> classes,List<Field> fieldList){
        if(classes != null && classes != Object.class){
            Set<Field> fields = new HashSet<>();
            fields.addAll(Arrays.asList(classes.getDeclaredFields()));

            for (Field field : fields){
                fieldList.add(field);
            }

            getFields(classes.getSuperclass(),fieldList);
        }
    }

    /**
     * 设置字段值
     * @param source
     * @param field
     * @param value
     */
    public static void setFieldValue(Object source, Field field, Object value) {
        try {
            Method setMethod = source.getClass().getMethod("set" +
                    field.getName().substring(0,1).toUpperCase() + field.getName().substring(1),
                    field.getType()
            );
            setMethod.invoke(source,value);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            field.setAccessible(true);
            try {
                field.set(source,value);
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * 获取字段值
     * @param source
     * @param field
     * @return
     */
    public static Object getFieldValue(Object source, Field field) {
        try {
            Method getMethod = source.getClass().getMethod("get" +
                    field.getName().substring(0,1).toUpperCase() + field.getName().substring(1)
            );
            return getMethod.invoke(source);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            field.setAccessible(true);
            try {
                return field.get(source);
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

}