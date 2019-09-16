package top.guyi.iot.ipojo.application.osgi.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import top.guyi.iot.ipojo.application.ApplicationContext;

@Data
@AllArgsConstructor
public abstract class ConfigurationRefreshInvoker {

    private Class<?> type;
    private String key;

    protected abstract void refresh(ApplicationContext context,Object value);

}
