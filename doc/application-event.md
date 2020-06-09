
* [InitializingBean](https://github.com/guyi-maple/ipojo/blob/master/src/main/java/top/guyi/iot/ipojo/application/bean/interfaces/InitializingBean.java)

当实现此接口的组件初始化、注入操作完成后触发
* [ApplicationStartEvent](https://github.com/guyi-maple/ipojo/blob/master/src/main/java/top/guyi/iot/ipojo/application/bean/interfaces/ApplicationStartEvent.java) 

当上下文中的所有组件完成初始化、注入操作后触发
* [ApplicationStartSuccessEvent](https://github.com/guyi-maple/ipojo/blob/master/src/main/java/top/guyi/iot/ipojo/application/bean/interfaces/ApplicationStartSuccessEvent.java) 

当所有操作执行完成，即Bundle启动完成后触发
* [ApplicationStopEvent](https://github.com/guyi-maple/ipojo/blob/master/src/main/java/top/guyi/iot/ipojo/application/bean/interfaces/ApplicationStopEvent.java)

当上下文关闭，即Bundle被OSGI容器关闭时触发