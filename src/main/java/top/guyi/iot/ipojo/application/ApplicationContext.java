package top.guyi.iot.ipojo.application;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import top.guyi.iot.ipojo.application.bean.BeanInfo;
import top.guyi.iot.ipojo.application.bean.defaults.DefaultBeanCreator;
import top.guyi.iot.ipojo.application.bean.interfaces.BeanCreator;
import top.guyi.iot.ipojo.application.bean.interfaces.FactoryBean;
import top.guyi.iot.ipojo.application.component.ComponentInterface;
import top.guyi.iot.ipojo.application.component.ForType;
import top.guyi.iot.ipojo.application.exception.ComponentCreateException;
import top.guyi.iot.ipojo.application.exception.ComponentNotFoundException;
import top.guyi.iot.ipojo.application.exception.ComponentRepeatException;
import top.guyi.iot.ipojo.application.utils.ReflectUtils;
import lombok.Getter;
import lombok.Setter;
import org.osgi.framework.BundleContext;
import top.guyi.iot.ipojo.application.utils.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.concurrent.ExecutorService;

public class ApplicationContext {

    @Setter
    @Getter
    private String name;
    @Getter
    @Setter
    private Map<String,String> env = Collections.emptyMap();

    private Map<String,Object> configurationFile = Collections.emptyMap();
    private void setConfigurationFile(){
        Gson gson = new Gson();
        File defaults = new File("default.configuration.json");
        File file = new File(String.format("%s.configuration.json",this.getName()));
        this.configurationFile = new HashMap<>();
        try {
            if (defaults.exists()){
                Map<String,Object> map = gson.fromJson(new FileReader(defaults),new TypeToken<Map<String,Object>>(){}.getType());
                this.configurationFile.putAll(map);
            }
            if (file.exists()){
                Map<String,Object> map = gson.fromJson(new FileReader(file),new TypeToken<Map<String,Object>>(){}.getType());
                this.configurationFile.putAll(map);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public <T> T getConfigurationFile(String key,Class<T> type,T origin){
        Object value = this.configurationFile.get(key);
        if (value == null){
            return origin;
        }
        return type.cast(value);
    }

    @Getter
    private ExecutorService service;

    private ApplicationContextRegister register;
    public ApplicationContextRegister register(){
        return this.register;
    }

    Map<Class<?>, BeanInfo> beanInfoMap;
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

        register = new ApplicationContextRegister(this);
    }

    private Object createBean(BeanInfo info) throws Exception {
        for (BeanCreator creator : this.beanCreators) {
            if (creator.forType(info.getClasses())){
                return creator.create(this,info,info.getClasses());
            }
        }
        throw new ComponentCreateException(info.getClasses());
    }

    void isBeanCreator(BeanInfo info) throws Exception {
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

    void isFactoryBean (BeanInfo info) throws Exception {
        if (ReflectUtils.subordinate(info.getClasses(),FactoryBean.class)){
            FactoryBean factoryBean = (FactoryBean) info.getTarget();
            if (factoryBean == null){
                factoryBean = (FactoryBean) this.createBean(info);
                info.setTarget(factoryBean);
            }
            this.register().put(
                    factoryBean.forType(),
                    factoryBean.create(this,factoryBean.forType())
            );
        }
    }


    public void start(BundleContext bundleContext,ExecutorService service) throws Exception {
        this.service = service;

        List<BeanInfo> infos = new LinkedList<>(this.beanInfoMap.values());
        Collections.sort(infos, new Comparator<BeanInfo>() {
            @Override
            public int compare(BeanInfo o1, BeanInfo o2) {
                return Integer.compare(o1.getComponent().getOrder(),o2.getComponent().getOrder());
            }
        });

        this.setConfigurationFile();

        for (BeanInfo beanInfo : infos) {
            if (!beanInfo.isCreate()){
                beanInfo.setTarget(this.createBean(beanInfo));
            }
        }

        for (BeanInfo beanInfo : infos) {
            if (beanInfo.getTarget() instanceof ComponentInterface){
                ((ComponentInterface) beanInfo.getBean()).inject(this);
            }
        }

        for (BeanInfo beanInfo : infos) {
            beanInfo.initializingBean();
        }

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

    public <T> List<T> getList(Class<T> classes){
        return this.getList(classes,null);
    }

    public <T> List<T> getList(Class<T> classes,String name){
        List<BeanInfo> beans = new LinkedList<>();
        for (Class<?> clazz : beanInfoMap.keySet()) {
            if (ReflectUtils.subordinate(clazz,classes)){
                beans.add(beanInfoMap.get(clazz));
            }
        }

        if (!StringUtils.isEmpty(name)){
            List<BeanInfo> tmp = new LinkedList<>();
            for (BeanInfo bean : beans) {
                if (name.equals(bean.getName())){
                    tmp.add(bean);
                }
            }
            beans = tmp;
        }

        List<T> list = new LinkedList<>();
        for (BeanInfo bean : beans) {
            list.add(classes.cast(bean.getTarget()));
        }

        return list;
    }

    public <T extends ForType> Map<String,T> getMap(Class<T> classes){
        return this.getMap(classes,null);
    }
    public <T extends ForType> Map<String,T> getMap(Class<T> classes,String name){
        List<T> list = this.getList(classes,name);
        Map<String,T> map = new HashMap<>();
        for (T bean : list) {
            map.put(bean.forType() == null ? null : bean.forType().toString(),bean);
        }
        return map;
    }

    public void stop(BundleContext bundleContext){

    }

}
