package top.guyi.iot.ipojo.application.bean.defaults;

import top.guyi.iot.ipojo.application.ApplicationContext;
import top.guyi.iot.ipojo.application.bean.BeanInfo;
import top.guyi.iot.ipojo.application.component.ComponentInterface;
import lombok.AllArgsConstructor;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

@AllArgsConstructor
public class DefaultMethodInterceptor implements MethodInterceptor {

    private BeanInfo beanInfo;
    private ApplicationContext context;

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        if (!beanInfo.isInitializing()){
            if (beanInfo.getBean() instanceof ComponentInterface){
                ((ComponentInterface) beanInfo.getBean()).inject(context);
            }
            beanInfo.initializingBean();
        }
        return proxy.invoke(beanInfo.getBean(),args);
    }

}
