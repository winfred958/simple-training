package com.winfred.traning.designpattern.behavioral.chain.base;

import com.winfred.traning.designpattern.behavioral.chain.entity.Request;
import com.winfred.traning.designpattern.behavioral.chain.entity.Response;

public interface FilterChain {

    FilterChain add(Filter filter);

    void doFilter(Request request, Response response) throws RuntimeException;

}
