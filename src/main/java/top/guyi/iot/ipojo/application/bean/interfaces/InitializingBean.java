package top.guyi.iot.ipojo.application.bean.interfaces;

/**
 * @author guyi
 * 当组件注入完成
 */
public interface InitializingBean {

    /**
     * 当组件注入完成时调用此方法
     */
    void afterPropertiesSet();

}
