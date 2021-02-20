package com.winfred.elastic.config;

import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration(value = "restHighLevelClient")
public class ElasticsearchConfig implements FactoryBean<RestHighLevelClient>, InitializingBean, DisposableBean {

  private RestHighLevelClient restHighLevelClient;

  @Override
  public void destroy() throws Exception {
    restHighLevelClient.close();
  }

  @Override
  public RestHighLevelClient getObject() throws Exception {
    return restHighLevelClient;
  }

  @Override
  public Class<?> getObjectType() {
    return RestHighLevelClient.class;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    restHighLevelClient = getElasticClient();
  }

  @Override
  public boolean isSingleton() {
    return false;
  }

  private RestHighLevelClient getElasticClient() {
    RestClientBuilder restClientBuilder = RestClient.builder(
        new HttpHost("es-8gp5f0ej.public.tencentelasticsearch.com", 9200, "https")
    );

    List<BasicHeader> headers = new ArrayList<>(16);
    headers.add(new BasicHeader("Authorization", "Basic ZWxhc3RpYzptYyFWYVlAOW5nI2tJXlEq"));

    restClientBuilder
        .setHttpClientConfigCallback((clientBuilder) -> {
          clientBuilder.setMaxConnTotal(200);
          clientBuilder.setDefaultHeaders(headers);
          return clientBuilder;
        });

    return new RestHighLevelClient(restClientBuilder);
  }
}
