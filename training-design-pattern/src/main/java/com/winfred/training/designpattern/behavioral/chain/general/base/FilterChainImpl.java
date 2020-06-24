package com.winfred.training.designpattern.behavioral.chain.general.base;

import com.winfred.training.designpattern.behavioral.chain.general.entity.Request;
import com.winfred.training.designpattern.behavioral.chain.general.entity.Response;
import lombok.Data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
public class FilterChainImpl implements FilterChain {
  
  private List<Filter> filters;
  private Iterator<Filter> iterator;
  
  @Override
  public FilterChain add(Filter filter) {
    if (null == filters) {
      filters = new ArrayList<>();
    }
    filters.add(filter);
    return this;
  }
  
  @Override
  public void doFilter(Request request, Response response) {
    if (iterator == null) {
      iterator = filters.iterator();
    }
    
    while (iterator.hasNext()) {
      Filter filter = iterator.next();
      filter.doFilter(request, response, this);
    }
    
  }
  
}
