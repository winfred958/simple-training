package com.winfred.elastic.app;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(
        scanBasePackages = {
                "com.winfred.elastic.config",
                "com.winfred.elastic.service"
        }
)
public class ElasticApplication {
  
  public static void main(String[] args) {
    SpringApplication.run(ElasticApplication.class, args);
  }
}
