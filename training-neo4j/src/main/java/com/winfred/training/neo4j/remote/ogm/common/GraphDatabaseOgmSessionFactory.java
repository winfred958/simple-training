package com.winfred.training.neo4j.remote.ogm.common;

import org.neo4j.ogm.config.ClasspathConfigurationSource;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.SessionFactory;

public class GraphDatabaseOgmSessionFactory {

  private static SessionFactory sessionFactory = Single.getSessionFactory();

  private static class Single {
    public static Configuration getConfiguration() {
      ClasspathConfigurationSource classpathConfigurationSource = new ClasspathConfigurationSource("neo4j-ogm.properties");
      Configuration configuration = new Configuration
          .Builder(classpathConfigurationSource)
//                    .uri("bolt://cdh-172-16-1-36:7687")
//                    .credentials("neo4j", "Cwup9RGxgVEHUkJa5")
          .build();


      return configuration;
    }


    public static SessionFactory getSessionFactory() {
      // 加入扫描 entity 包
      Configuration configuration = getConfiguration();
      return new SessionFactory(configuration, "com.winfred.training.neo4j.remote.ogm.entity");
    }
  }

  public static SessionFactory getInstance() {
    return sessionFactory;
  }

}
