
## 注入org.osgi.service.log.Logger

在字段上添加 [@Log](https://github.com/guyi-maple/ipojo/blob/master/src/main/java/top/guyi/iot/ipojo/application/osgi/log/Log.java)注解即可注入Logger对象

```
@Log
private Logger logger;
```

当前Bundle的名称会自动配置到Logger对象中

## [StaticLogger](https://github.com/guyi-maple/ipojo/blob/master/src/main/java/top/guyi/iot/ipojo/application/osgi/log/StaticLogger.java)

在静态方法等无法使用组件的情况下，可以使用StaticLogger进行日志输出

StaticLogger不保证日志的完整性，当OSGI的日志服务没有就绪时，通过StaticLogger输出的日志会被丢弃

## 依赖

日志相关功能依赖于felix-log，使用时请确保你的容器内存在此Bundle

```
<dependency>
    <groupId>org.apache.felix</groupId>
    <artifactId>org.apache.felix.log</artifactId>
    <version>1.2.0</version>
</dependency>
```