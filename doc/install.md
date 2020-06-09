#### 方式一：使用源码

克隆源码到本地使用

```
git clone https://github.com/guyi-maple/ipojo.git
```

#### 方式二：Maven引入

```
<dependency>
  <groupId>top.guyi.iot.ipojo</groupId>
  <artifactId>ipojo</artifactId>
  <version>1.0.0.0</version>
</dependency>
```

因为没有放入到Maven中央仓库，所以需要加上仓库配置

```
<repository>
    <id>guyi</id>
    <name>guyi</name>
    <url>http://nexus.guyi-maple.top/repository/osgi-iot/</url>
</repository>
```

