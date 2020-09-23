package tech.guyi.ipojo.application.osgi.timer.executor;

import tech.guyi.ipojo.application.ApplicationContext;
import tech.guyi.ipojo.application.osgi.log.StaticLogger;
import tech.guyi.ipojo.application.osgi.timer.TimerRunnable;
import tech.guyi.ipojo.application.osgi.timer.entry.TaskEntry;
import tech.guyi.ipojo.application.osgi.timer.enums.TimeType;

import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author guyi
 * 定时任务
 */
public abstract class AbstractExecutor {

    private int index = 0;
    private List<List<TaskEntry>> taskTree;
    private Map<String,TaskEntry> taskMap;

    private ApplicationContext context;
    private ScheduledExecutorService service;
    public AbstractExecutor(ApplicationContext context,ScheduledExecutorService service) {
        this.context = context;
        this.service = service;
        this.taskMap = new HashMap<>();
        this.taskTree = new ArrayList<>(this.maxLength());
        for (int i = 0; i < this.maxLength(); i++) {
            this.taskTree.add(new LinkedList<TaskEntry>());
        }
    }

    /**
     * 定时任务容器的最大长度
     * @return
     */
    protected abstract int maxLength();

    /**
     * 定时任务执行周期的时间单位
     * @return
     */
    protected abstract TimeUnit unit();

    public void start(){
        this.service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try{
                    execute();
                }catch (Exception e){
                    StaticLogger.error("method timer task execute error",e);
                }
            }
        },0,1, this.unit());
    }

    public void stop(){
        this.taskMap.clear();
        this.taskMap = null;
        this.taskTree.clear();
        this.taskTree = null;
    }

    public void register(TimerRunnable runnable){
        TaskEntry task = new TaskEntry(
                runnable.name(),
                this.maxLength(),
                this.getIndex(),
                runnable.delay(),
                runnable.type(),
                runnable
        );
        this.getTree(task.getIndex()).add(task);
        this.taskMap.put(runnable.name(),task);
    }

    private int getIndex(){
        return this.index;
    }

    private int takeIndex(){
        if (this.index >= this.maxLength()){
            this.index = 0;
        }
        return index++;
    }

    private List<TaskEntry> getTree(int index){
        return this.taskTree.get(index);
    }

    private void setTree(int index,List<TaskEntry> list){
        this.taskTree.set(index,list);
    }

    private void execute(){
        int index = this.takeIndex();
        List<TaskEntry> list = new LinkedList<>();
        List<TaskEntry> cycleTasks = new LinkedList<>();
        for (TaskEntry task : this.getTree(index)) {
            if (task.take()){
                task.getRunnable().run(this.context);
            }
            if (task.isEnable() && task.getCoefficient() != 0){
                list.add(task);
            }else{
                if (task.getType() == TimeType.CYCLE && task.getCoefficient() == 0){
                    task.makeCoefficient(this.getIndex(),this.maxLength());
                    cycleTasks.add(task);
                }else{
                    this.taskMap.remove(task.getName());
                }
            }
        }
        this.setTree(index,list);
        for (TaskEntry task : cycleTasks) {
            this.getTree(task.getIndex()).add(task);
        }
    }


}
