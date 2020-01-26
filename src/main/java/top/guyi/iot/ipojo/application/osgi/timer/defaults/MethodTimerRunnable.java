package top.guyi.iot.ipojo.application.osgi.timer.defaults;

import top.guyi.iot.ipojo.application.osgi.timer.TimerRunnable;
import top.guyi.iot.ipojo.application.osgi.timer.enums.TimeType;

public abstract class MethodTimerRunnable implements TimerRunnable {

    private String name;
    private int delay;
    private TimeType type;

    public MethodTimerRunnable(String name, int delay, TimeType type) {
        this.name = name;
        this.delay = delay;
        this.type = type;
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

}
