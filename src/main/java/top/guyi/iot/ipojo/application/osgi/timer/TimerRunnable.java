package top.guyi.iot.ipojo.application.osgi.timer;

import top.guyi.iot.ipojo.application.ApplicationContext;
import top.guyi.iot.ipojo.application.osgi.timer.enums.TimeType;

public interface TimerRunnable extends Runnable {

    String name();

    int delay();

    TimeType type();

    void run(ApplicationContext context);

}
