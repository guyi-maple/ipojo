package top.guyi.iot.ipojo.application.osgi.timer;

import org.osgi.framework.BundleContext;
import top.guyi.iot.ipojo.application.ApplicationContext;
import top.guyi.iot.ipojo.application.bean.interfaces.ApplicationStartEvent;
import top.guyi.iot.ipojo.application.bean.interfaces.ApplicationStopEvent;
import top.guyi.iot.ipojo.application.bean.interfaces.InitializingBean;
import top.guyi.iot.ipojo.application.osgi.timer.entry.TaskEntry;

import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public abstract class AbstractTimerManager implements InitializingBean,ApplicationStartEvent, ApplicationStopEvent {

    private static final int MAX_LENGTH = 60;

    private int index = 0;
    private List<List<TaskEntry>> taskTree;
    private Map<String,TaskEntry> taskMap;

    private int getIndex(){
        return index;
    }
    private int takeIndex(){
        if (this.index >= MAX_LENGTH){
            this.index = 0;
        }
        return index++;
    }

    private void onceRun(){
        int index = this.takeIndex();
        List<TaskEntry> list = new LinkedList<>();
        for (TaskEntry task : this.getTree(index)) {
            if (task.take()){
                task.getRunnable().run(this.applicationContext);
            }
            if (task.isEnable() && task.getCoefficient() != 0){
                list.add(task);
            }else{
                this.taskMap.remove(task.getName());
            }
        }
        this.setTree(index,list);

        if (this.taskMap.size() == 0 && this.future != null){
            this.future.cancel(false);
            this.future = null;
        }
    }

    private List<TaskEntry> getTree(int index){
        return this.taskTree.get(index);
    }

    private void setTree(int index,List<TaskEntry> list){
        this.taskTree.set(index,list);
    }

    public void register(TimerRunnable runnable){
        TaskEntry task = new TaskEntry(
                runnable.name(),
                MAX_LENGTH,
                this.getIndex() ,
                runnable.delay(),
                runnable
        );
        this.getTree(task.getIndex()).add(task);
        this.taskMap.put(runnable.name(),task);

        if (this.future == null){
            this.future = this.applicationContext.get(ScheduledExecutorService.class,true)
                    .scheduleAtFixedRate(new Runnable() {
                        @Override
                        public void run() {
                            onceRun();
                        }
                    },0,1, TimeUnit.MINUTES);
        }
    }

    private ApplicationContext applicationContext;
    private ScheduledFuture<?> future;

    protected abstract List<TimerRunnable> timerRunnableList();

    @Override
    public void afterPropertiesSet() {
        this.taskMap = new HashMap<>();
        this.taskTree = new ArrayList<>(MAX_LENGTH);
        for (int i = 0; i < MAX_LENGTH; i++) {
            this.taskTree.add(new LinkedList<TaskEntry>());
        }
    }

    @Override
    public void onStart(ApplicationContext applicationContext, BundleContext bundleContext) {
        this.applicationContext = applicationContext;
        for (TimerRunnable runnable : this.timerRunnableList()) {
            this.register(runnable);
        }
    }

    @Override
    public void onStop(ApplicationContext applicationContext, BundleContext bundleContext) {
        this.taskMap.clear();
        this.taskMap = null;
        this.taskTree.clear();
        this.taskTree = null;
        if (this.future != null){
            this.future.cancel(false);
        }
    }

}
