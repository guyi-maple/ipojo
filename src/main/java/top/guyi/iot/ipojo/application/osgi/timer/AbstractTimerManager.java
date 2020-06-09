package top.guyi.iot.ipojo.application.osgi.timer;

import org.osgi.framework.BundleContext;
import top.guyi.iot.ipojo.application.ApplicationContext;
import top.guyi.iot.ipojo.application.bean.interfaces.ApplicationStartEvent;
import top.guyi.iot.ipojo.application.bean.interfaces.ApplicationStopEvent;
import top.guyi.iot.ipojo.application.osgi.log.StaticLogger;
import top.guyi.iot.ipojo.application.osgi.timer.enums.TimeType;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

/**
 * @author guyi
 * 定时任务管理器
 */
public abstract class AbstractTimerManager implements ApplicationStartEvent,ApplicationStopEvent {


    private ApplicationContext context;
    private ScheduledExecutorService service;

    private final List<ScheduledFuture<?>> futures = new LinkedList<>();

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

        ScheduledFuture<?> future;
        if (runnable.type() == TimeType.CYCLE){
            future = this.service.scheduleAtFixedRate(run,runnable.initDelay(),runnable.delay(),runnable.unit());
        }else{
            future = this.service.schedule(run,runnable.initDelay(),runnable.unit());
        }
        this.futures.add(future);
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
        for (ScheduledFuture<?> future : this.futures) {
            if (future != null){
                future.cancel(true);
            }
        }
    }

}
