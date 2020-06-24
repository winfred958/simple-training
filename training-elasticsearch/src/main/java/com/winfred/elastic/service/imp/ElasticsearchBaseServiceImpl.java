package com.winfred.elastic.service.imp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.winfred.elastic.annotation.Id;
import com.winfred.elastic.common.ReflectUtils;
import com.winfred.elastic.common.ResultsExtractor;
import com.winfred.elastic.service.ElasticsearchBaseService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class ElasticsearchBaseServiceImpl implements ElasticsearchBaseService {
  
  @Autowired
  @Qualifier("getElasticClient")
  private RestHighLevelClient restHighLevelClient;
  
  /**
   * 批量索引数据
   *
   * @param data
   * @param indexName
   * @return
   */
  @Override
  public BulkResponse bulkIndex(List<?> data, String indexName) {
    // TODO: 可以考虑 data 切分, 控制每个请求粒度
    BulkRequest bulkRequest = buildBulkRequest(data, indexName);
    BulkResponse bulkResponse = null;
    try {
      bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
      log.info("[bulkIndex] bulk index took {} ms", bulkResponse.getIngestTookInMillis());
    } catch (IOException e) {
      log.error("[elasticsearch] index failed.", e);
    }
    return bulkResponse;
  }
  
  /**
   * @param data      数据列表
   * @param indexName Elasticsearch 索引名称
   * @return
   */
  private BulkRequest buildBulkRequest(List<?> data, String indexName) {
    BulkRequest bulkRequest = new BulkRequest(indexName);
    
    data
        .stream()
        .map(obj -> {
          IndexRequest indexRequest = new IndexRequest();
          indexRequest.index(indexName);
          String id = ReflectUtils.getAnnotationFiledValue(obj, Id.class);
          indexRequest.id(id);
          indexRequest.source(JSON.toJSONBytes(obj, SerializerFeature.SortField, SerializerFeature.DisableCircularReferenceDetect), XContentType.JSON);
          return indexRequest;
        })
        .forEach(bulkRequest::add);
    
    return bulkRequest;
  }
  
  @Override
  public <T> T query(SearchRequest searchRequest, ResultsExtractor<T> resultsExtractor) {
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
