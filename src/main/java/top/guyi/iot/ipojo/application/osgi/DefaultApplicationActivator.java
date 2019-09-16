package top.guyi.iot.ipojo.application.osgi;

import com.google.gson.Gson;
import top.guyi.iot.ipojo.application.ApplicationContext;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import top.guyi.iot.ipojo.application.bean.ComponentInfo;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public abstract class DefaultApplicationActivator implements BundleActivator {

    protected abstract void registerComponent(ApplicationContext applicationContext, BundleContext bundleContext);
    protected abstract String getName();

    private ApplicationContext applicationContext;

    @Override
    public void start(BundleContext context) throws Exception {
        applicationContext = new ApplicationContext();

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);

        applicationContext.register()
                .put(applicationContext)
                .put(BundleContext.class,context)
                .put(ScheduledExecutorService.class,executorService)
                .put(Gson.class,new Gson())
                .end();

        this.registerComponent(applicationContext,context);

        applicationContext.setName(this.getName());

        applicationContext.start(context);
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        applicationContext.stop(context);
    }

}
