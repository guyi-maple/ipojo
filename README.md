# ipojo

目前接触到运营商的一些智能网关项目，需要编写OSGI-Bundle。
OSGI这个东西，有点太老旧，很多用顺手的东西，在OSGI上并不能使用。
最关键的是，很多操作都非常繁琐，完全不能忍。

说几个令人头疼的点：

* 依赖注入：能使用的依赖注入框架大部分都是基于XML的，太麻烦
* 服务获取：需要写好几行代码，有时还需要实现服务追踪器
* 服务注册：同上，写起来太麻烦
* 事件监听：必须实现监听器接口，还需要在BundleContext上注册，不能在方法上加注解监听，差评
* ......

主要是被Spring以及SpringBoot宠溺的我，承受不来这些重压啊

于是，ipojo出来了 （ps：和apache那个同名，主要是起名困难户😂）

为了干掉服务获取、事件监听、日志等等等，一切让人觉得恼火的体力活。

具体使用请见 [Wiki | 文档](https://github.com/guyi-maple/ipojo/wiki)