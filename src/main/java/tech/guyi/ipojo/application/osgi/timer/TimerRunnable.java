package tech.guyi.ipojo.application.osgi.timer;

import tech.guyi.ipojo.application.osgi.timer.enums.TimeType;
import tech.guyi.ipojo.application.ApplicationContext;

import java.util.concurrent.TimeUnit;

/**
 * @author guyi
 * 定时任务
 */
public interface TimerRunnable {

    /**
     * 定时任务名称
     * @return 定时任务名称
     */
    String name();

    /**
     * 执行间隔时间
     * @return 执行间隔时间
     */
    int delay();

    /**
     * 任务开始延迟时间
     * @return 延迟时间
     */
    int initDelay();

    /**
     * 定时任务执行方式
     * @return 定时任务执行方式
     */
    TimeType type();

    /**
     * 间隔时间单位
     * @return 时间单位
     */
    TimeUnit unit();

    /**
     * 执行定时任务
     * @param context 容器上下文
     */
    void run(ApplicationContext context);

}
