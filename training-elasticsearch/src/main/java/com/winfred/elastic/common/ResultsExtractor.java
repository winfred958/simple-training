package com.winfred.elastic.common;

import org.elasticsearch.action.search.SearchResponse;

public interface ResultsExtractor<T> {
  T extract(SearchResponse response);
}
