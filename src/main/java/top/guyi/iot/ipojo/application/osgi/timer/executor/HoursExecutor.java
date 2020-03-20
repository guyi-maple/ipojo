package top.guyi.iot.ipojo.application.osgi.timer.executor;

import top.guyi.iot.ipojo.application.ApplicationContext;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author guyi
 * 定时任务执行器
 * 任务间隔时间单位为小时
 */
public class HoursExecutor extends AbstractExecutor {

    public HoursExecutor(ApplicationContext context, ScheduledExecutorService service) {
        super(context, service);
    }

    @Override
    protected int maxLength() {
        return 24;
    }

    @Override
    protected TimeUnit unit() {
        return TimeUnit.HOURS;
    }

}
