package com.winfred.training.designpattern.structure.decorator.general;

import com.winfred.training.designpattern.structure.decorator.general.impl.Source;
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
public class DecoratorClient {

  @Test
  public void decorator() {
    // 被装饰的对象
    ISource source = new Source();

    log.info("============ [{}] ============", "装饰器A");
    // 装饰器A, 给对象增加A功能
    ISource sourceAddEgg = new SourceDecoratorAddEgg(source);
    showSource(sourceAddEgg);

    log.info("============ [{}] ============", "装饰器B");
    // 装饰器B, 给对象增加B功能
    ISource sourceAddSausage = new SourceDecoratorAddSausage(source);
    showSource(sourceAddSausage);

    log.info("============ [{}] ============", "装饰器嵌套");
    // 装饰器的嵌套
    ISource sourceDecoratorB1 = new SourceDecoratorAddSausage(sourceAddEgg);
    showSource(sourceDecoratorB1);

    ISource sourceDecoratorB2 = new SourceDecoratorAddSausage(sourceDecoratorB1);
    showSource(sourceDecoratorB2);

  }

  private void showSource(ISource source) {
    log.info("{}, 总价: {}", source.getMsg(), source.getPrice());
  }
}
