package com.winfred.training.designpattern.test;

import com.winfred.traning.designpattern.behavioral.chain.HtmlFilter;
import com.winfred.traning.designpattern.behavioral.chain.TermFilter;
import com.winfred.traning.designpattern.behavioral.chain.base.FilterChain;
import com.winfred.traning.designpattern.behavioral.chain.base.FilterChainImpl;
import com.winfred.traning.designpattern.behavioral.chain.entity.Request;
import com.winfred.traning.designpattern.behavioral.chain.entity.Response;
import com.winfred.traning.designpattern.behavioral.observer.SubscriberOne;
import com.winfred.traning.designpattern.behavioral.observer.SubscriberTwo;
import com.winfred.traning.designpattern.behavioral.observer.base.Publisher;
import com.winfred.traning.designpattern.behavioral.observer.entity.Message;
import org.junit.Test;

import java.util.Observer;


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
}
