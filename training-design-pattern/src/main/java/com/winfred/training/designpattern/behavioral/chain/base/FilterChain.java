package com.winfred.training.designpattern.behavioral.chain.base;

import com.winfred.training.designpattern.behavioral.chain.entity.Response;
import com.winfred.training.designpattern.behavioral.chain.entity.Request;

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
