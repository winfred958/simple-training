package com.winfred.elastic.service;

import com.winfred.elastic.common.ResultsExtractor;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchRequest;

import java.util.List;

public interface ElasticsearchBaseService {

  <T> T query(SearchRequest searchRequest, ResultsExtractor<T> resultsExtractor);

  List<BulkResponse> bulkIndex(List<?> data, String indexName);


  void bulkUpdate();
}
