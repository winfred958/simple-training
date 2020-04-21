package com.winfred.training.designpattern.behavioral.chain.general.base;

import com.winfred.training.designpattern.behavioral.chain.general.entity.Request;
import com.winfred.training.designpattern.behavioral.chain.general.entity.Response;

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
