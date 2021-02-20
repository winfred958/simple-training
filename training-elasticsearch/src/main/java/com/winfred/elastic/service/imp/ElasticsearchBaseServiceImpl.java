package com.winfred.elastic.service.imp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.winfred.elastic.annotation.Id;
import com.winfred.elastic.common.ForkJoinUtils;
import com.winfred.elastic.common.ReflectUtils;
import com.winfred.elastic.common.ResultsExtractor;
import com.winfred.elastic.service.ElasticsearchBaseService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
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
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ElasticsearchBaseServiceImpl implements ElasticsearchBaseService {

  private int MAX_BULK_SIZE = 1500;


  @Autowired
  @Qualifier(value = "restHighLevelClient")
  private RestHighLevelClient restHighLevelClient;

  /**
   * 批量索引数据
   *
   * @param data
   * @param indexName
   * @return
   */
  @Override
  public List<BulkResponse> bulkIndex(List<?> data, String indexName) {
    ForkJoinTask<List<BulkResponse>> forkJoinTask = ForkJoinUtils
        .getInstance()
        .submit(new BulkIndexTask(data, indexName));

    try {
      List<BulkResponse> bulkResponses = forkJoinTask.get();
      List<Long> longList = bulkResponses
          .stream()
          .map(response -> {
            return response.getIngestTookInMillis();
          })
          .collect(Collectors.toList());

      log.info("[bulkIndex] bulk index  partition={} took {} ms", bulkResponses.size(), JSON.toJSONString(longList));
      return bulkResponses;
    } catch (InterruptedException e) {
      log.error("[bulkIndex] bulk index failed.", e);
    } catch (ExecutionException e) {
      log.error("[bulkIndex] bulk index failed.", e);
    }
    return null;
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

  @Override
  public void bulkUpdate() {

  }


  class BulkIndexTask extends RecursiveTask<List<BulkResponse>> {
    private List<?> data;
    private String indexName;

    public BulkIndexTask(List<?> data, String indexName) {
      this.data = data;
      this.indexName = indexName;
    }

    @Override
    protected List<BulkResponse> compute() {
      int size = data.size();
      if (size < MAX_BULK_SIZE) {
        BulkRequest bulkRequest = buildBulkRequest(this.data, this.indexName);
        BulkResponse bulkResponse = null;
        try {
          bulkResponse = restHighLevelClient
              .bulk(bulkRequest, RequestOptions.DEFAULT);
          log.info("[bulkIndex] bulk index success took {} ms", bulkResponse == null ? "xxx" : bulkResponse.getIngestTookInMillis());
        } catch (IOException e) {
          log.error("[elasticsearch] index failed.", e);
        }
        List<BulkResponse> result = new CopyOnWriteArrayList<>();
        result.add(bulkResponse);
        return result;
      } else {
        BulkIndexTask bulkIndexTask1 = new BulkIndexTask(data.subList(0, size / 2), indexName);
        BulkIndexTask bulkIndexTask2 = new BulkIndexTask(data.subList(size / 2, size), indexName);

        bulkIndexTask1.fork();
        bulkIndexTask2.fork();

        List<BulkResponse> join1 = bulkIndexTask1.join();
        List<BulkResponse> join2 = bulkIndexTask2.join();
        join1.addAll(join2);
        return join1
            .stream()
            .filter(entity -> {
              return entity != null;
            })
            .collect(Collectors.toList());
      }
    }
  }

  class BulkIndexTaskAsync extends RecursiveTask<Void> {
    private List<?> data;
    private String indexName;

    public BulkIndexTaskAsync(List<?> data, String indexName) {
      this.data = data;
      this.indexName = indexName;
    }

    @Override
    protected Void compute() {
      int size = data.size();
      if (size < MAX_BULK_SIZE) {
        BulkRequest bulkRequest = buildBulkRequest(this.data, this.indexName);
        BulkResponse bulkResponse = null;

        // Async index
        restHighLevelClient
            .bulkAsync(bulkRequest, RequestOptions.DEFAULT, new ActionListener<BulkResponse>() {
              @Override
              public void onResponse(BulkResponse bulkItemResponses) {
                log.info("[bulkIndex] bulk index success took {} ms", bulkItemResponses.getIngestTookInMillis());
              }

              @Override
              public void onFailure(Exception e) {
                log.error("[bulkIndex] bulk index failed.", e);
              }
            });
      } else {
        BulkIndexTask bulkIndexTask1 = new BulkIndexTask(data.subList(0, size / 2), indexName);
        BulkIndexTask bulkIndexTask2 = new BulkIndexTask(data.subList(size / 2, size), indexName);

        bulkIndexTask1.fork();
        bulkIndexTask2.fork();

        bulkIndexTask1.join();
        bulkIndexTask2.join();
      }
      return null;
    }
  }
}
