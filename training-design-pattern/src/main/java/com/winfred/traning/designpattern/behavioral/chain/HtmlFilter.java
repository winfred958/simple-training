package com.winfred.traning.designpattern.behavioral.chain;

import com.winfred.traning.designpattern.behavioral.chain.base.Filter;
import com.winfred.traning.designpattern.behavioral.chain.entity.Request;
import com.winfred.traning.designpattern.behavioral.chain.entity.Response;

public class HtmlFilter implements Filter {

    @Override
    public void requestHandler(Request request) {
        request.setBody(request.getBody() + " HTML-filter ->");

    }

    @Override
    public void responseHandeler(Response response) {
        response.setBody(response.getBody() + " HTML-filter ->");

    }

}
