## [ConfigurationKey](https://github.com/guyi-maple/ipojo/blob/master/src/main/java/top/guyi/iot/ipojo/application/osgi/configuration/annotation/ConfigurationKey.java)

在字段上添加 @ConfigurationKey 注解，即可在编译时将配置项的值注入到字段中。

* key：配置项的名称
* file：是否在启动时从配置文件中读取值

#### 从文件中读取

当file为true [@ConfigurationKey(...,file = true)] 时，当Bundle启动后，会从配置文件中读取配置并赋值给字段。当此字段已有数据时，值将被覆盖。 

数据优先级为 file > compile > class

#### 配置文件

##### 插件配置文件
插件配置文件的名称格式为  bundleName.configuration.json。

假设Bundle的Bundle-Name为 test ，那么当他启动时，读取的配置文件名称为 test.configuration.json

##### 默认配置文件
Bundle启动时除了会读取符合自身名称的配置文件外，还会读取默认配置文件 default.configuration.json

##### 读取优先级

插件配置文件 > 默认配置文件

如果存在相同配置项，优先级较低的值将会被覆盖

##### 存放路径

* Bundle内部
* 容器运行目录

读取优先级为 容器运行目录 >  Bundle内部

如果存在相同配置项，优先级较低的值将会被覆盖

