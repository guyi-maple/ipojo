## 发布事件

组件[AbstractEventPublisher](https://github.com/guyi-maple/ipojo/blob/master/src/main/java/tech/guyi/ipojo/application/osgi/event/AbstractEventPublisher.java) 可用于OSGI的事件发布。

* publish([Event](https://github.com/guyi-maple/ipojo/blob/master/src/main/java/tech/guyi/ipojo/application/osgi/event/interfaces/Event.java) event) 发布包装后的OSGI事件
* post(org.osgi.service.event.Event event) 发布OSGI原生事件

## 事件监听

#### 使用方法监听事件
在方法上添加注解[@ListenEvent](https://github.com/guyi-maple/ipojo/blob/master/src/main/java/tech/guyi/ipojo/application/osgi/event/annotation/ListenEvent.java) ，当事件触发时将会调用此方法，并传入包装后的事件对象

在方法上添加注解[@ListenNativeEvent](https://github.com/guyi-maple/ipojo/blob/master/src/main/java/tech/guyi/ipojo/application/osgi/event/annotation/ListenNativeEvent.java) ，当事件触发时将会调用此方法，并传入OSGI原生事件对象

```
@ListenEvent(Event.class)
public void onEvent(Event event){}
```

```
@ListenNativeEvent(NativeEvent.class)
public void onEvent(NativeEvent event){}
```

#### 使用监听器监听

实现接口[EventListener](https://github.com/guyi-maple/ipojo/blob/master/src/main/java/tech/guyi/ipojo/application/osgi/event/interfaces/EventListener.java)，将实现类注册进组件容器，即可实现事件监听（只支持包装后的事件）

```
@Component
public class TestEventListener implements EventListener<TestEvent> {}
```

## 事件内容格式化

实现接口[EventConverter](https://github.com/guyi-maple/ipojo/blob/master/src/main/java/tech/guyi/ipojo/application/osgi/event/interfaces/EventConverter.java)，将其实现类注册进组件容器，即可实现自己的OSGI原生事件包装

* order 排序数值
* check(Class<? extends Event> targetClass) 检查是否支持此事件类型的转换
* convert(Class<? extends Event> eventClass, org.osgi.service.event.Event sourceEvent) 转换OSGI原生事件内容
* convert(Event event) 将包装事件转换为原生事件