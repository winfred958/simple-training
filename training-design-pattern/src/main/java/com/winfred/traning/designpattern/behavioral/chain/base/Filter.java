package com.winfred.traning.designpattern.behavioral.chain.base;


import com.winfred.traning.designpattern.behavioral.chain.entity.Request;
import com.winfred.traning.designpattern.behavioral.chain.entity.Response;

public interface Filter {

    default void doFilter(Request request, Response response, FilterChain filterChain) throws RuntimeException {
        requestHandler(request);
        filterChain.doFilter(request, response);
        responseHandeler(response);
    }

    ;

    void requestHandler(Request request);

    void responseHandeler(Response response);
}
