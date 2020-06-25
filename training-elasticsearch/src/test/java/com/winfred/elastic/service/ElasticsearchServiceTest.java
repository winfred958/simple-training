package com.winfred.elastic.service;

import com.winfred.elastic.base.BaseTest;
import com.winfred.elastic.entity.OrderItem;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.suggest.Suggest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class ElasticsearchServiceTest extends BaseTest {
  
  @Autowired
  private ElasticsearchBaseService elasticsearchService;
  
  List<OrderItem> data = new ArrayList<>();
  
  @Before
  public void createData() {
    data.add(new OrderItem("1", "ttttttttttttttt1"));
    data.add(new OrderItem("2", "ttttttttttttttt2"));
  }
  
  @Test
  public void bulkIndexTest() {
    elasticsearchService.bulkIndex(data, "bulk_index_test");
  }
  
  @Test
  public void queryTest() {
    
    SearchRequest searchRequest = new SearchRequest();
    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    searchSourceBuilder.query(QueryBuilders.matchAllQuery());
    searchSourceBuilder.size(10);
    searchRequest.indices(".monitoring-es-7-2020.06.24");
    searchRequest.source(searchSourceBuilder);
    
    SearchResponse searchResponse = elasticsearchService
        .query(searchRequest, (response -> {
          SearchHits searchHits = response.getHits();
          
          SearchHit[] hits = searchHits.getHits();
          List<String> collect = Arrays.stream(hits).map(hit -> {
            String sourceStr = hit.getSourceAsString();
            return sourceStr;
          }).collect(Collectors.toList());
          
          
          return response;
        }));
    SearchHits searchHits = searchResponse.getHits();
    Aggregations aggregations = searchResponse.getAggregations();
    Suggest suggest = searchResponse.getSuggest();
    int totalShards = searchResponse.getTotalShards();
  }
  
}
