# ipojo

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

[Wiki | 文档](https://github.com/guyi-maple/ipojo/wiki)