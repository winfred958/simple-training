package com.winfred.elastic.config;

import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ElasticsearchConfig {
  
  @Bean
  public RestHighLevelClient getElasticClient() {
    RestClientBuilder restClientBuilder = RestClient.builder(
        new HttpHost("es-8gp5f0ej.public.tencentelasticsearch.com", 9200, "https")
    );
    
    List<BasicHeader> headers = new ArrayList<>();
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
