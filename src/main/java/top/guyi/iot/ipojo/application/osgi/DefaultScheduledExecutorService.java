package top.guyi.iot.ipojo.application.osgi;

import top.guyi.iot.ipojo.application.osgi.log.StaticLogger;

import java.util.concurrent.ScheduledThreadPoolExecutor;

public class DefaultScheduledExecutorService extends ScheduledThreadPoolExecutor {

    public DefaultScheduledExecutorService(int corePoolSize) {
        super(corePoolSize);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        if (t != null){
            t.printStackTrace();
            StaticLogger.error("",t);
        }
        super.afterExecute(r, t);
    }
}
