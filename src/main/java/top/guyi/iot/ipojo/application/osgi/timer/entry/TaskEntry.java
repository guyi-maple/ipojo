package top.guyi.iot.ipojo.application.osgi.timer.entry;

import lombok.Data;
import top.guyi.iot.ipojo.application.osgi.timer.TimerRunnable;

@Data
public class TaskEntry {

    private String name;
    private boolean enable;
    private int index;
    private int coefficient;
    private TimerRunnable runnable;

    public TaskEntry(String name,int maxLength,int current, int delay, TimerRunnable runnable){
        delay = delay + current;
        if (delay <= maxLength){
            this.coefficient = 1;
            this.index = delay - 1;
        }else{
            this.index = delay % maxLength - 1;
            this.coefficient = (delay - this.index) / maxLength + 1;
        }
        this.enable = true;
        this.runnable = runnable;
        this.name = name;
    }

    public boolean take(){
        this.coefficient--;
        return this.coefficient == 0;
    }

}
