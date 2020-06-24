package com.winfred.training.designpattern.behavioral;

import com.winfred.training.designpattern.behavioral.observer.SubscriberOne;
import com.winfred.training.designpattern.behavioral.observer.SubscriberThree;
import com.winfred.training.designpattern.behavioral.observer.SubscriberTwo;
import com.winfred.training.designpattern.behavioral.observer.base.Publisher;
import com.winfred.training.designpattern.behavioral.observer.entity.Message;
import org.junit.Test;

public class ObserverTest {
  
  @Test
  public void observerTest() {
    Publisher publisher = new Publisher();
    
    publisher.addObserver(new SubscriberOne());
    publisher.addObserver(new SubscriberTwo());
    publisher.addObserver(new SubscriberThree());
    
    publisher.pub(new Message("你好"));
    System.out.println("==");
    publisher.pub(new Message("123"));
  }
  
}
