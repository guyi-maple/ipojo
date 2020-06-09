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

* [Home](https://github.com/guyi-maple/ipojo/wiki)
* [安装](https://github.com/guyi-maple/ipojo/wiki/install)
* [组件注册及注入](https://github.com/guyi-maple/ipojo/wiki/component-register-inject)
* [内置组件](https://github.com/guyi-maple/ipojo/wiki/builtIn-components)
* [上下文事件](https://github.com/guyi-maple/ipojo/wiki/application-event)
* [OSGI事件](https://github.com/guyi-maple/ipojo/wiki/osgi-event)
* [定时任务](https://github.com/guyi-maple/ipojo/wiki/timer)
* [服务注册及获取](https://github.com/guyi-maple/ipojo/wiki/service-register-get)
* [配置项读取](https://github.com/guyi-maple/ipojo/wiki/configuration)
* [日志](https://github.com/guyi-maple/ipojo/wiki/log)

## 重要说明
此项目只是一个注解式API，有部分实现的抽象类，但是不负责依赖注入等功能的具体实现

需要在项目中实现依赖注入等相关功能，请使用Maven编译插件 [ipojo-compile](https://github.com/guyi-maple/ipojo-compile.git) 编译你的OSGI-Bundle项目 