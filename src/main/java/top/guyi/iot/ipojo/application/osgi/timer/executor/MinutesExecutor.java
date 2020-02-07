package top.guyi.iot.ipojo.application.osgi.timer.executor;

import top.guyi.iot.ipojo.application.ApplicationContext;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
