package top.guyi.iot.ipojo.application.osgi.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import top.guyi.iot.ipojo.application.ApplicationContext;

/**
 * @author guyi
 * 配置刷新执行者
 */
@Data
@AllArgsConstructor
public abstract class AbstractConfigurationRefreshInvoker {

    private Class<?> type;
    private String key;

    /**
     * 刷新配置
     * @param context 容器上下文
     * @param value 配置内容
     */
    protected abstract void refresh(ApplicationContext context,Object value);

}
