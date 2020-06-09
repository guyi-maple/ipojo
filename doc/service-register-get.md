## 服务注册
在类上添加注解[@Service](https://github.com/guyi-maple/ipojo/blob/master/src/main/java/top/guyi/iot/ipojo/application/osgi/service/annotation/Service.java) 即可将其作为OSGI服务暴露给其他Bundle

```
@Component
@Service(TestInterface.class)
public class TestImpl implements TestInterface {}
```

* value 注册服务使用的接口类型，值必须是组件已经实现的接口
* export 是否将接口类型的包名添加到MANIFEST.MF文件的Export-Package字段中，默认为true
* version 导出的版本号，默认为空字符串，不导出

## 服务获取
在方法上添加注解[@BundleServiceReference](https://github.com/guyi-maple/ipojo/blob/master/src/main/java/top/guyi/iot/ipojo/application/osgi/service/reference/BundleServiceReference.java)，在服务获取成功时会调用此方法，并将服务作为参数传入

```
@BundleServiceReference(TestInterface.class)
public void onService(TestInterface test){}
```

* value 要获取的OSGI服务类型，可为单个Class或数组
* checker 判断是否符合方法调用条件，默认为服务获取成功后调用。如果需要自定义，请传入自定义Checker的Class。 [BundleServiceReferenceChecker](https://github.com/guyi-maple/ipojo/blob/master/src/main/java/top/guyi/iot/ipojo/application/osgi/service/reference/BundleServiceReferenceChecker.java)

#### 同时获取多个服务
```
@BundleServiceReference({TestInterface.class,TestInterface1.class})
public void onService(TestInterface test,TestInterface1 test1){}
```

#### 调用时注入其他组件
```
@BundleServiceReference({TestInterface.class,TestInterface1.class})
public void onService(TestInterface test,TestInterface1 test1，ApplicationContext context){}
```