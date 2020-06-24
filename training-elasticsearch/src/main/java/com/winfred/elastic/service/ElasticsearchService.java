package com.winfred.elastic.service;

import com.winfred.elastic.common.ResultsExtractor;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;

import java.util.List;

public interface ElasticsearchService {
  
  BulkResponse bulkIndex(List<?> data, String indexName);
  
  <T> T query(SearchRequestBuilder requestBuilder, ResultsExtractor<T> resultsExtractor);
}
