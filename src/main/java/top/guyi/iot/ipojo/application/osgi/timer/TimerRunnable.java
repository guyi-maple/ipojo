package top.guyi.iot.ipojo.application.osgi.timer;

import top.guyi.iot.ipojo.application.ApplicationContext;
import top.guyi.iot.ipojo.application.osgi.timer.enums.TimeType;

import java.util.concurrent.TimeUnit;

public interface TimerRunnable {

    String name();

    int delay();

    TimeType type();

    TimeUnit unit();

    void run(ApplicationContext context);

}
