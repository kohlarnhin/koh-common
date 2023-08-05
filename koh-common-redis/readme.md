# Koh Common Redis

## 引入依赖
```xml
        <dependency>
            <groupId>com.koh.common.core</groupId>
            <artifactId>koh-common-core</artifactId>
            <version>1.0.0</version>
            <url>https://raw.github.com/kohlarnhin/koh-repository/main</url>
        </dependency>
```

## 使用方法
1. yml
```yaml
repeat:
  submit:
    enable: true   # 是否开启重复提交校验
    service-name: ${spring.application.name} # 服务名称 不写默认就是当前服务名称
```
2. 接口上添加注解
```java
public class RepeatSubmitTestController {
    /**
     * 重复提交测试
     * interval: 间隔时间 默认1000毫秒
     * timeUnit: 时间单位 默认毫秒
     * @return String
     */
    @RepeatSubmit(interval = 1000, timeUnit = TimeUnit.MICROSECONDS)
    @GetMapping("/demo")
    public String demo() {
        return "Hello, world!";
    }
}
```
3. 实现RequestIdProvider返回requestId
```java
/**
 * 必须实现，不然报错
 */
public class RequestIdProviderImpl implements RequestIdProvider {
    @Override
    public String getRequestId() {
        //这里主要是提供一个requestId，需要唯一，比如用户id
        return RequestIdContext.getRequestId();
    }
}
```