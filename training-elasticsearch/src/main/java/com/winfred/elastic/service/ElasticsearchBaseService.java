package com.winfred.elastic.service;

import com.winfred.elastic.common.ResultsExtractor;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchRequest;

import java.util.List;

public interface ElasticsearchBaseService {
  
  BulkResponse bulkIndex(List<?> data, String indexName);
  
  <T> T query(SearchRequest searchRequest, ResultsExtractor<T> resultsExtractor);
}
