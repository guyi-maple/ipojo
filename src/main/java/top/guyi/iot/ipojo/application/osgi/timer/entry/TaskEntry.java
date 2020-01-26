package top.guyi.iot.ipojo.application.osgi.timer.entry;

import lombok.Data;
import top.guyi.iot.ipojo.application.osgi.timer.TimerRunnable;
import top.guyi.iot.ipojo.application.osgi.timer.enums.TimeType;

@Data
public class TaskEntry {

    private int delay;

    private String name;
    private boolean enable;
    private int index;
    private int coefficient;
    private TimeType type;
    private TimerRunnable runnable;

    public TaskEntry(String name,int maxLength,int current, int delay, TimeType type,TimerRunnable runnable){
        this.type = type;
        this.delay = delay;
        this.makeCoefficient(current,maxLength);
        this.enable = true;
        this.runnable = runnable;
        this.name = name;
    }

    public void makeCoefficient(int current,int maxLength){
        int delay = this.delay + current;
        if (delay <= maxLength){
            this.coefficient = 1;
            this.index = delay - 1;
        }else{
            this.index = (delay % maxLength) - 1;
            this.coefficient = (delay - this.index) / maxLength + 1;
        }
    }

    public boolean take(){
        this.coefficient--;
        return this.coefficient == 0;
    }

}
