package com.winfred.training.designpattern.structure;

import com.winfred.training.designpattern.structure.decorator.impl.Source;
import com.winfred.training.designpattern.structure.decorator.SourceDecorator;
import org.junit.Test;

public class DecoratorTest {

    @Test
    public void decorator(){
        SourceDecorator sourceDecorator = new SourceDecorator(new Source());
        sourceDecorator.doSomething();
    }
}
