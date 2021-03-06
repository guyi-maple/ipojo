package tech.guyi.ipojo.application.osgi.timer;

import org.osgi.framework.BundleContext;
import tech.guyi.ipojo.application.ApplicationContext;
import tech.guyi.ipojo.application.bean.interfaces.ApplicationStartEvent;
import tech.guyi.ipojo.application.bean.interfaces.ApplicationStopEvent;
import tech.guyi.ipojo.application.osgi.log.StaticLogger;
import tech.guyi.ipojo.application.osgi.timer.enums.TimeType;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

/**
 * @author guyi
 * 定时任务管理器
 */
public abstract class AbstractTimerManager implements ApplicationStartEvent,ApplicationStopEvent {


    private ApplicationContext context;
    private ScheduledExecutorService service;

    private final Map<String,ScheduledFuture<?>> futures = new HashMap<>();

    public void register(final TimerRunnable runnable){
        Runnable run = new Runnable() {
            @Override
            public void run() {
                try {
                    runnable.run(context);
                }catch (Exception e){
                    StaticLogger.error("method timer task execute error",e);
                }
            }
        };

        if (this.futures.containsKey(runnable.name())){
            this.futures.get(runnable.name()).cancel(true);
        }

        ScheduledFuture<?> future;
        if (runnable.type() == TimeType.CYCLE){
            future = this.service.scheduleAtFixedRate(run,runnable.initDelay(),runnable.delay(),runnable.unit());
        }else{
            future = this.service.schedule(run,runnable.initDelay(),runnable.unit());
        }
        this.futures.put(runnable.name(),future);
    }

    /**
     * 注册所有方法级定时任务
     */
    protected abstract void registerAll();

    @Override
    public void onStart(ApplicationContext applicationContext, BundleContext bundleContext) {
        this.context = applicationContext;
        this.service = applicationContext.get(ScheduledExecutorService.class,true);
        this.registerAll();
    }

    @Override
    public void onStop(ApplicationContext applicationContext, BundleContext bundleContext) {
        for (ScheduledFuture<?> future : this.futures.values()) {
            if (future != null){
                future.cancel(true);
            }
        }
        this.futures.clear();
    }

}
