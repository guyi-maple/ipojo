
在方法上添加 [@Timer](https://github.com/guyi-maple/ipojo/blob/master/src/main/java/top/guyi/iot/ipojo/application/osgi/timer/annotation/Timer.java) 注解，即可将此方法设置为定时任务

* name 
定时任务的名称

* delay 
定时任务的执行间隔时间，当定时任务类型为循环执行时此配置项有效

* initDelay 
任务初次执行的延迟时间，表示任务将在多久后开始执行，默认为0

* type 
任务执行方式，ONCE - 仅执行一次；CYCLE - 循环执行， 默认为循环执行，[TimeType](https://github.com/guyi-maple/ipojo/blob/master/src/main/java/top/guyi/iot/ipojo/application/osgi/timer/enums/TimeType.java)

* unit 
时间单位，默认为分钟，java.util.concurrent.TimeUnit

## 示例

### 执行一次
```
@Timer(initDelay = 10, type = TimeType.ONCE)
public void task(){......}
```
十分钟后调用此方法

### 循环执行
```
@Timer(initDelay = 10, delay = 1)
public void task(){......}
```
十分钟后第一次调用此方法，随后每分钟调用一次此方法