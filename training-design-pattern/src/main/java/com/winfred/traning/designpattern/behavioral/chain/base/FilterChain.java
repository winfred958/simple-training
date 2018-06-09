package com.winfred.traning.designpattern.behavioral.chain.base;

import com.winfred.traning.designpattern.behavioral.chain.entity.Request;
import com.winfred.traning.designpattern.behavioral.chain.entity.Response;

public interface FilterChain {

    /**
     * 添加过滤器
     *
     * @param filter
     * @return
     */
    FilterChain add(Filter filter);

    /**
     * 执行filter
     *
     * @param request
     * @param response
     * @throws RuntimeException
     */
    void doFilter(Request request, Response response) throws RuntimeException;

}
