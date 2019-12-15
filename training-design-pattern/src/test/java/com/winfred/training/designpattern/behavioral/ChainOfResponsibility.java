package com.winfred.training.designpattern.behavioral;

import com.winfred.training.designpattern.behavioral.chain.HtmlFilter;
import com.winfred.training.designpattern.behavioral.chain.TermFilter;
import com.winfred.training.designpattern.behavioral.chain.base.FilterChain;
import com.winfred.training.designpattern.behavioral.chain.base.FilterChainImpl;
import com.winfred.training.designpattern.behavioral.chain.entity.Request;
import com.winfred.training.designpattern.behavioral.chain.entity.Response;
import org.junit.Test;

public class ChainOfResponsibility {
    
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
}
