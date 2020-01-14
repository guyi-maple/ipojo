package top.guyi.iot.ipojo.application.osgi;

import com.google.gson.Gson;
import top.guyi.iot.ipojo.application.ApplicationContext;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import top.guyi.iot.ipojo.application.osgi.env.EnvMap;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public abstract class DefaultApplicationActivator implements BundleActivator {

    protected abstract void registerComponent(ApplicationContext applicationContext, BundleContext bundleContext);
    protected abstract String getName();
    protected abstract EnvMap getEnv();

//    protected abstract void onAfterPropertiesSet(ApplicationContext applicationContext,BundleContext bundleContext);
    protected abstract void onStart(ApplicationContext applicationContext,BundleContext bundleContext);
    protected abstract void onStartSuccess(ApplicationContext applicationContext,BundleContext bundleContext);
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

//        this.onAfterPropertiesSet(applicationContext,context);
        this.onStart(applicationContext,context);
        this.onStartSuccess(applicationContext,context);
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        this.onStop(applicationContext,context);
        applicationContext.stop(context);
        if (this.executorService != null){
            this.executorService.shutdownNow();
        }
    }

}
