package com.winfred.elastic.service.imp;

import com.winfred.elastic.common.ResultsExtractor;
import com.winfred.elastic.service.ElasticsearchService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class ElasticsearchServiceImpl implements ElasticsearchService {
  
  @Qualifier("getElasticClient")
  @Autowired
  private RestHighLevelClient restHighLevelClient;
  
  
  @Override
  public BulkResponse bulkIndex(List<?> data, String indexName) {
    BulkRequest bulkRequest = new BulkRequest();
    
    // TODO: build bulkRequest
    
    BulkResponse bulkResponse = null;
    try {
      bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
      log.info("[bulkIndex] bulk index took {} ms", bulkResponse.getIngestTookInMillis());
    } catch (IOException e) {
      log.error("[elasticsearch] index failed.", e);
    }
    return bulkResponse;
  }
  
  @Override
  public <T> T query(SearchRequestBuilder requestBuilder, ResultsExtractor<T> resultsExtractor) {
    SearchRequest searchRequest = requestBuilder.request();
    T t = null;
    try {
      SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
      log.info("[query] query took {} ms", searchResponse.getTook().getMillis());
      t = resultsExtractor.extract(searchResponse);
    } catch (IOException e) {
      log.error("[elasticsearch] query failed.", e);
    }
    return t;
  }
}
