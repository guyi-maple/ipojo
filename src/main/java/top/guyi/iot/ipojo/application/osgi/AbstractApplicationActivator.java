package top.guyi.iot.ipojo.application.osgi;

import com.google.gson.Gson;
import top.guyi.iot.ipojo.application.ApplicationContext;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import top.guyi.iot.ipojo.application.osgi.env.EnvMap;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author guyi
 * Bundle入口
 */
public abstract class AbstractApplicationActivator implements BundleActivator {

    /**
     * 注册所有组件
     * @param applicationContext 容器上下文
     * @param bundleContext OSGI上下文
     */
    protected abstract void registerComponent(ApplicationContext applicationContext, BundleContext bundleContext);

    /**
     * 获取Bundle名称
     * @return
     */
    protected abstract String getName();

    /**
     * 获取配置的环境变量
     * @return
     */
    protected abstract EnvMap getEnv();

    /**
     * 执行所有Bundle启动监听器
     * @param applicationContext 容器上下文
     * @param bundleContext OSGI上下文
     */
    protected abstract void onStart(ApplicationContext applicationContext,BundleContext bundleContext) throws Exception;

    /**
     * 执行所有Bundle启动成功监听器
     * @param applicationContext 容器上下文
     * @param bundleContext OSGI上下文
     */
    protected abstract void onStartSuccess(ApplicationContext applicationContext,BundleContext bundleContext) throws Exception;

    /**
     * 执行所有Bundle关闭监听器
     * @param applicationContext 容器上下文
     * @param bundleContext OSGI上下文
     */
    protected abstract void onStop(ApplicationContext applicationContext,BundleContext bundleContext);

    private ApplicationContext applicationContext;
    private ScheduledExecutorService executorService;

    private static final int THREAD_MAX_COUNT = 5;

    @Override
    public void start(BundleContext context) throws Exception {
        applicationContext = new ApplicationContext();

        int threadMaxCount = this.getEnv().get("thread.max.count") == null ?
                THREAD_MAX_COUNT : Integer.parseInt(this.getEnv().get("thread.max.count"));
        executorService = new DefaultScheduledExecutorService(threadMaxCount);

        applicationContext.register()
                .put(applicationContext)
                .put(BundleContext.class,context)
                .put(ScheduledExecutorService.class,executorService)
                .put(Gson.class,new Gson())
                .end();

        this.registerComponent(applicationContext,context);

        applicationContext.setName(this.getName());
        applicationContext.setEnv(this.getEnv());

        applicationContext.start(context,executorService);

        this.onStart(applicationContext,context);
        this.onStartSuccess(applicationContext,context);
    }

    @Override
    public void stop(BundleContext context) {
        this.onStop(applicationContext,context);
        applicationContext.stop(context);
        if (this.executorService != null){
            this.executorService.shutdownNow();
        }
    }

}
