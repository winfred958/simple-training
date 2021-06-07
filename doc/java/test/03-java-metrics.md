# java metrics

## 作用

```text
这个工具包可以让你在生产环境中产生度量的一些数据，并且支持不同的输出方式。
它可以度量代码中关键组件，响应时间、计数器等都可以采集，也可以取操作系统信息。
```

## 依赖

-

```xml

<dependency>
  <groupId>io.dropwizard.metrics</groupId>
  <artifactId>metrics-core</artifactId>
  <version>4.1.18</version>
</dependency>
```

## 使用

```java

public class ExcelParseTest {
    private static final MetricRegistry metrics = new MetricRegistry();

    /**
     * 选择输出方式: ConsoleReporter, Slf4jReporter
     *
     */
    private static ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics).build();
    /**
     * 实例化一个Meter
     */
    private static final Meter meter = metrics.meter("test");

    @Test
    public void mergerSubOrderV3() {
        for (xxx; xxx; xxx) {
            // 业务逻辑 ...
            // 使用, 出发标记事件
            meter.mark();
        }
    }
}
```