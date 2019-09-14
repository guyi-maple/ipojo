package top.guyi.iot.ipojo.application.osgi;

import top.guyi.iot.ipojo.application.ApplicationContext;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public abstract class DefaultApplicationActivator implements BundleActivator {

    protected abstract void registerComponent(ApplicationContext applicationContext, BundleContext bundleContext);
    protected abstract String getName();

    @Override
    public void start(BundleContext context) throws Exception {
        ApplicationContext applicationContext = new ApplicationContext();
        applicationContext.register(applicationContext);
        applicationContext.register(BundleContext.class,context);

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
        applicationContext.register(ScheduledExecutorService.class,executorService);
        this.registerComponent(applicationContext,context);
        applicationContext.setName(this.getName());

        applicationContext.start(context);
    }

    @Override
    public void stop(BundleContext context) throws Exception {

    }

}
