package top.guyi.iot.ipojo.application.osgi.log;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

@Data
@AllArgsConstructor
public class LoggerMethodInterceptor implements MethodInterceptor {

    private DefaultLogger logger;

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        if (this.logger == null){
            return null;
        }
        return proxy.invoke(this.logger,args);
    }

}
