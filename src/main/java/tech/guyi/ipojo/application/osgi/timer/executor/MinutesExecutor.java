package tech.guyi.ipojo.application.osgi.timer.executor;

import tech.guyi.ipojo.application.ApplicationContext;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author guyi
 * 定时任务执行器
 * 任务间隔时间单位为分钟
 */
public class MinutesExecutor extends AbstractExecutor {

    public MinutesExecutor(ApplicationContext context, ScheduledExecutorService service) {
        super(context, service);
    }

    @Override
    protected int maxLength() {
        return 60;
    }

    @Override
    protected TimeUnit unit() {
        return TimeUnit.MINUTES;
    }

}
