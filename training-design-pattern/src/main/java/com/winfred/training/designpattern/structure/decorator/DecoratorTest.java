package com.winfred.training.designpattern.structure.decorator;

import com.winfred.training.designpattern.structure.decorator.impl.Source;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 装饰器模式 (Decorator Pattern), (Wrapper Pattern)包装器模式
 * <p>
 * 扩展原有类的功能
 * <p>
 * 指再不改变原有对象的基础上, 将功能附加到对象上, 提供了比继承更有弹性的替代方案.
 */
@Slf4j
public class DecoratorTest {

    @Test
    public void decorator() {
        // 被装饰的对象
        Source source = new Source();

        log.info("============ [{}] ============", "装饰器A");
        // 装饰器A, 给对象增加A功能
        Decorator sourceDecoratorA = new SourceDecoratorA(source);
        sourceDecoratorA.doSomething();

        log.info("============ [{}] ============", "装饰器B");
        // 装饰器B, 给对象增加B功能
        Decorator sourceDecoratorB = new SourceDecoratorB(source);
        sourceDecoratorB.doSomething();
        
        log.info("============ [{}] ============", "装饰器嵌套");
        // 装饰器的装饰器
        SourceDecoratorB sourceDecoratorB1 = new SourceDecoratorB(sourceDecoratorA);
        sourceDecoratorB1.doSomething();

    }
}
