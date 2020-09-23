package tech.guyi.ipojo.application.osgi;

import tech.guyi.ipojo.application.osgi.log.StaticLogger;

import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author guyi
 * 默认的线程池
 */
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
