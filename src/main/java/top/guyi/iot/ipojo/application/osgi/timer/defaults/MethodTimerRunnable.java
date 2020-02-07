package top.guyi.iot.ipojo.application.osgi.timer.defaults;

import top.guyi.iot.ipojo.application.osgi.timer.TimerRunnable;
import top.guyi.iot.ipojo.application.osgi.timer.enums.TimeType;

import java.util.concurrent.TimeUnit;

public abstract class MethodTimerRunnable implements TimerRunnable {

    private String name;
    private int delay;
    private TimeType type;
    private TimeUnit unit;

    public MethodTimerRunnable(String name, int delay, TimeType type,TimeUnit unit) {
        this.name = name;
        this.delay = delay;
        this.type = type;
        this.unit = unit;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public int delay() {
        return this.delay;
    }

    @Override
    public TimeType type() {
        return this.type;
    }

    @Override
    public TimeUnit unit() {
        return this.unit;
    }
}
