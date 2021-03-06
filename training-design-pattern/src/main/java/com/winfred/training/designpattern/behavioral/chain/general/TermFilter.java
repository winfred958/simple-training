package com.winfred.training.designpattern.behavioral.chain.general;


import com.winfred.training.designpattern.behavioral.chain.general.base.Filter;
import com.winfred.training.designpattern.behavioral.chain.general.entity.Request;
import com.winfred.training.designpattern.behavioral.chain.general.entity.Response;

public class TermFilter implements Filter {

  @Override
  public void requestHandler(Request request) {
    request.setBody(request.getBody() + " term-filter ->");

  }

  @Override
  public void responseHandeler(Response response) {
    response.setBody(response.getBody() + " term-filter ->");

  }

}
