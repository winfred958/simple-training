package com.winfred.training.designpattern.behavioral.chain.general;

import com.winfred.training.designpattern.behavioral.chain.general.base.FilterChain;
import com.winfred.training.designpattern.behavioral.chain.general.base.FilterChainImpl;
import com.winfred.training.designpattern.behavioral.chain.general.entity.Request;
import com.winfred.training.designpattern.behavioral.chain.general.entity.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class ChainClient {

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

        log.info(request.getBody());
        log.info(response.getBody());
    }
}
