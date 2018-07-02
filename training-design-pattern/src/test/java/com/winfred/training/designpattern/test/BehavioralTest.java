package com.winfred.training.designpattern.test;

import com.winfred.training.designpattern.behavioral.chain.HtmlFilter;
import com.winfred.training.designpattern.behavioral.chain.TermFilter;
import com.winfred.training.designpattern.behavioral.chain.base.FilterChain;
import com.winfred.training.designpattern.behavioral.chain.base.FilterChainImpl;
import com.winfred.training.designpattern.behavioral.chain.entity.Request;
import com.winfred.training.designpattern.behavioral.chain.entity.Response;
import com.winfred.training.designpattern.behavioral.observer.SubscriberOne;
import com.winfred.training.designpattern.behavioral.observer.SubscriberTwo;
import com.winfred.training.designpattern.behavioral.observer.base.Publisher;
import com.winfred.training.designpattern.behavioral.observer.entity.Message;
import com.winfred.training.designpattern.behavioral.proxy.jdk.MyInvocationHandler;
import com.winfred.training.designpattern.behavioral.proxy.jdk.UserService;
import com.winfred.training.designpattern.behavioral.proxy.jdk.UserServiceImpl;
import org.junit.Test;


public class BehavioralTest {

    @Test
    public void filterChainTest() {
        FilterChain filterChain = new FilterChainImpl();

        Request request = new Request();
        request.setBody("request: ");

        Response response = new Response();
        response.setBody("response: ");

        filterChain.add(new HtmlFilter());
        filterChain.add(new TermFilter());
        filterChain.doFilter(request, response);

        System.out.printf(request.getBody());
        System.out.println();
        System.out.printf(response.getBody());
    }


    @Test
    public void observerTest() {
        Publisher publisher = new Publisher();

        publisher.addObserver(new SubscriberOne());
        publisher.addObserver(new SubscriberTwo());

        publisher.pub(new Message("你好"));
        System.out.println("==");
        publisher.pub(new Message("123"));
    }

    @Test
    public void jdkProxyTest() {
        // jdk proxy 只支持被代理的对象必须要实现接口
        UserService userService = new UserServiceImpl();
        MyInvocationHandler myInvocationHandler = new MyInvocationHandler(userService);

        UserService proxy = (UserService) myInvocationHandler.getProxy();

        proxy.testBefore();
        System.out.println("=====================");
        proxy.testAfter();
        System.out.println("=====================");
        proxy.testAround();
        System.out.println("=====================");
    }
}
