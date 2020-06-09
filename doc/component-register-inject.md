
## 注册组件

在类上添加注解 [@Component](https://github.com/guyi-maple/ipojo/blob/master/src/main/java/top/guyi/iot/ipojo/application/annotation/Component.java) 即可将类注册为一个可用于注入的组件

Component注解拥有如下属性：
* name：组件名称，用于注入时的筛选
* order：排序，在注入时如果存在多个组件，且不存在@Primary，则通过此属性排序，取第一个组件注入

### [@Primary](https://github.com/guyi-maple/ipojo/blob/master/src/main/java/top/guyi/iot/ipojo/application/annotation/Primary.java)

在类上添加此注解，则在存在多个组件可注入时，优先使用此类注入

## 注入组件

在字段上添加注解 [@Resource](https://github.com/guyi-maple/ipojo/blob/master/src/main/java/top/guyi/iot/ipojo/application/annotation/Resource.java) 即可从组件容器中选择符合条件的组件注入

top.guyi.iot.ipojo.application.annotation.Resource 与 javax.annotation.Resource 同义，均可执行注入操作

如无特殊说明，后续所说的Resource注解均表示 top.guyi.iot.ipojo.application.annotation.Resource

Resource注解拥有如下属性：
* name：只注入名称为此属性值的组件
* equals：是否使用字段类型（Class）直接正在组件容器中完整匹配，忽略可能存在的子类。接口或抽线类不能使用此属性

### 注入List

支持List<组件类型>的注入，当不存在指定类型的组件时，注入一个空List

### 注入Map

支持Map<Key,组件类型 extends ForType<Key>> 的注入，当不存在指定类型的组件时，注入一个空Map

* 指定的组件类型必须实现接口[ForType](https://github.com/guyi-maple/ipojo/blob/master/src/main/java/top/guyi/iot/ipojo/application/component/ForType.java)
* 实现ForType接口时泛型必须与Map的键值类型一致
* 键值类型不能为Class<?>

