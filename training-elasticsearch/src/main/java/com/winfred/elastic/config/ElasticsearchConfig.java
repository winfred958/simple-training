package com.winfred.elastic.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {
  
  @Bean
  public RestHighLevelClient getElasticClient() {
    RestClientBuilder restClientBuilder = RestClient.builder(new HttpHost("es-8gp5f0ej.public.tencentelasticsearch.com", 9200, "https"));
    restClientBuilder
            .setHttpClientConfigCallback((clientBuilder) -> {
              clientBuilder.setMaxConnTotal(200);
              return clientBuilder;
            });
    return new RestHighLevelClient(restClientBuilder);
  }
  
  @Bean
  public RestClient getElasticLowClient() {
    return getElasticClient().getLowLevelClient();
  }
}
