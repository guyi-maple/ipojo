package top.guyi.iot.ipojo.application.component;

import top.guyi.iot.ipojo.application.ApplicationContext;

/**
 * @author guyi
 * 组件接口，所有组件都需要实现此接口
 */
public interface ComponentInterface {

    /**
     * 依赖注入
     * @param context 容器上下文
     */
    void inject(ApplicationContext context);

}
