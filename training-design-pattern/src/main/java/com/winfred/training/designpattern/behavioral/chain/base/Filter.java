package com.winfred.training.designpattern.behavioral.chain.base;


import com.winfred.training.designpattern.behavioral.chain.entity.Request;
import com.winfred.training.designpattern.behavioral.chain.entity.Response;

public interface Filter {

    /**
     * 执行过滤
     *
     * @param request     请求实体
     * @param response    返回
     * @param filterChain 过滤链
     * @throws RuntimeException
     */
    default void doFilter(Request request, Response response, FilterChain filterChain) throws RuntimeException {
        requestHandler(request);
        filterChain.doFilter(request, response);
        responseHandeler(response);
    }

    void requestHandler(Request request);

    void responseHandeler(Response response);
}
