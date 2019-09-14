package top.guyi.iot.ipojo.application;

import top.guyi.iot.ipojo.application.bean.BeanInfo;
import top.guyi.iot.ipojo.application.bean.defaults.DefaultBeanCreator;
import top.guyi.iot.ipojo.application.bean.interfaces.BeanCreator;
import top.guyi.iot.ipojo.application.bean.interfaces.FactoryBean;
import top.guyi.iot.ipojo.application.component.ComponentInterface;
import top.guyi.iot.ipojo.application.exception.ComponentCreateException;
import top.guyi.iot.ipojo.application.exception.ComponentNotFoundException;
import top.guyi.iot.ipojo.application.exception.ComponentRepeatException;
import top.guyi.iot.ipojo.application.utils.ReflectUtils;
import lombok.Getter;
import lombok.Setter;
import org.osgi.framework.BundleContext;

import java.util.*;

public class ApplicationContext {

    @Setter
    @Getter
    private String name;

    private Map<Class<?>, BeanInfo> beanInfoMap;
    private List<BeanCreator> beanCreators;

    private Comparator<BeanCreator> createBeanComparator = new Comparator<BeanCreator>(){
        @Override
        public int compare(BeanCreator o1, BeanCreator o2) {
            return Integer.compare(o1.order(), o2.order());
        }
    };

    public ApplicationContext(){
        beanInfoMap = new HashMap<>();

        beanCreators = new LinkedList<>();
        beanCreators.add(new DefaultBeanCreator());
    }

    private Object createBean(BeanInfo info) throws Exception {
        for (BeanCreator creator : this.beanCreators) {
            if (creator.forType(info.getClasses())){
                return creator.create(this,info,info.getClasses());
            }
        }
        throw new ComponentCreateException(info.getClasses());
    }

    private void isBeanCreator(BeanInfo info) throws Exception {
        if (ReflectUtils.subordinate(info.getClasses(),BeanCreator.class)){
            BeanCreator creator = (BeanCreator) info.getTarget();
            if (creator == null){
                creator = (BeanCreator) this.createBean(info);
                info.setTarget(creator);
            }
            this.beanCreators.add(creator);
            Collections.sort(this.beanCreators,this.createBeanComparator);
        }
    }

    private void isFactoryBean (BeanInfo info) throws Exception {
        if (ReflectUtils.subordinate(info.getClasses(),FactoryBean.class)){
            FactoryBean factoryBean = (FactoryBean) info.getTarget();
            if (factoryBean == null){
                factoryBean = (FactoryBean) this.createBean(info);
                info.setTarget(factoryBean);
            }
            this.register(factoryBean.forType(),factoryBean.create(this,factoryBean.forType()));
        }
    }

    public void register(Object bean) {
        BeanInfo info = new BeanInfo(bean.getClass());
        info.setBean(bean);
        this.register(info);
    }

    public void register(Class<?> classes,Object bean) {
        BeanInfo info = new BeanInfo(classes);
        info.setTarget(bean);
        this.register(info);
    }

    public void register(Class<?> classes) {
        BeanInfo info = new BeanInfo(classes);
        this.register(info);
    }

    public void register(BeanInfo info) {
        this.beanInfoMap.put(info.getClasses(),info);

        try {
            this.isBeanCreator(info);
            this.isFactoryBean(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start(BundleContext bundleContext) throws Exception {
        for (BeanInfo beanInfo : this.beanInfoMap.values()) {
            if (!beanInfo.isCreate()){
                beanInfo.setTarget(this.createBean(beanInfo));
            }
        }

        for (BeanInfo beanInfo : this.beanInfoMap.values()) {
            if (beanInfo.getTarget() instanceof ComponentInterface){
                ((ComponentInterface) beanInfo.getBean()).inject(this);
            }
        }

        for (BeanInfo beanInfo : this.beanInfoMap.values()) {
            beanInfo.initializingBean();
        }

        for (BeanInfo beanInfo : this.beanInfoMap.values()) {
            beanInfo.onStart(this,bundleContext);
        }
    }

    public <T> List<T> getList(Class<T> classes){
        List<BeanInfo> beans = new LinkedList<>();
        for (Class<?> clazz : beanInfoMap.keySet()) {
            if (ReflectUtils.subordinate(clazz,classes)){
                beans.add(beanInfoMap.get(clazz));
            }
        }
        List<T> list = new LinkedList<>();
        for (BeanInfo bean : beans) {
            list.add(classes.cast(bean.getTarget()));
        }

        return list;
    }

    public <T> T getOrNull(Class<T> classes){
        try {
            return this.get(classes,classes.getSimpleName());
        }catch (ComponentNotFoundException e){
            return null;
        }
    }

    public <T> T get(Class<T> classes){
        return this.get(classes,classes.getSimpleName());
    }

    public <T> T get(Class<T> classes,String name){
        List<BeanInfo> beans = new LinkedList<>();
        for (Class<?> clazz : beanInfoMap.keySet()) {
            if (ReflectUtils.subordinate(clazz,classes)){
                beans.add(beanInfoMap.get(clazz));
            }
        }

        T bean = null;
        if (beans.size() == 1){
            bean = classes.cast(beans.get(0).getTarget());
        }

        if (beans.size() > 1){
            BeanInfo primaryInfo = null;
            BeanInfo nameInfo = null;
            BeanInfo allInfo = null;
            for (BeanInfo beanInfo : beans) {
                if (beanInfo.isPrimary()){
                    primaryInfo = beanInfo;
                }
                if (beanInfo.getName().equals(name)){
                    nameInfo = beanInfo;
                }
                if (beanInfo.isPrimary() && beanInfo.getName().equals(name)){
                    allInfo = beanInfo;
                }

                if (allInfo != null){
                    bean = classes.cast(allInfo.getTarget());
                } else if (nameInfo != null){
                    bean = classes.cast(nameInfo.getTarget());
                } else if (primaryInfo != null){
                    bean = classes.cast(primaryInfo.getTarget());
                }

                if (bean == null){
                    throw new ComponentRepeatException(classes);
                }
            }
        }

        if (bean == null){
            throw new ComponentNotFoundException(classes,name);
        }

        return bean;
    }

    public <T> T get(Class<T> classes,boolean eq){
        if (eq){
            BeanInfo info = this.beanInfoMap.get(classes);
            if (info == null){
                throw new ComponentNotFoundException(classes,classes.getSimpleName());
            }
            return classes.cast(info.getTarget());
        }else{
            return this.get(classes);
        }
    }

    public List<Object> getAll(){
        List<Object> beans = new LinkedList<>();
        for (BeanInfo beanInfo : this.beanInfoMap.values()) {
            if (beanInfo.isCreate()){
                beans.add(beanInfo.getTarget());
            }
        }
        return beans;
    }

}
