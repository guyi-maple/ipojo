为OSGI环境提供一个与Spring风格类似的依赖注入API

目前仅支持apache-felix容器

* @Component 声明组件
* @Resource 注入组件
* @Service 注册OSGI服务
* @Primary 多个组件时优先注入
* @ConfigurationKey 编译期间注入配置信息
* @ListenEvent 监听事件
* @ListenNativeEvent 监听OSGI原生事件
* @Log 注入osgi-logger
* @BundleServiceReference 从OSGI容器中获取服务
* @Timer 定时任务

## 功能文档

* [安装](install.md)
* [组件注册及注入](component-register-inject.md)
* [内置组件](builtIn-components.md)
* [上下文事件](application-event.md)
* [OSGI事件](osgi-event.md)
* [定时任务](timer.md)
* [服务注册及获取](service-register-get.md)
* [配置项读取](configuration.md)
* [日志](log.md)

## 重要说明
此项目只是一个注解式API，有部分实现的抽象类，但是不负责依赖注入等功能的具体实现

需要在项目中实现依赖注入等相关功能，请使用Maven编译插件 [ipojo-compile](https://github.com/guyi-maple/ipojo-compile.git) 编译你的OSGI-Bundle项目 