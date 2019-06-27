package com.winfred.training.neo4j.remote.ogm.common;

import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.config.FileConfigurationSource;
import org.neo4j.ogm.session.SessionFactory;

import java.net.URL;

public class GraphDatabaseOgmSessionFactory {

    private static SessionFactory sessionFactory = Single.getSessionFactory();

    private static class Single {
        public static Configuration getConfiguration() {
            URL url = Thread.currentThread().getContextClassLoader().getResource("neo4j-ogm.properties");
            Configuration configuration = new Configuration
                    .Builder(new FileConfigurationSource(String.valueOf(url)))
                    .uri("bolt://localhost:7687")
                    .credentials("neo4j", "123456")
                    .connectionLivenessCheckTimeout(2000)
                    .connectionPoolSize(150)
                    .build();
            return configuration;
        }


        public static SessionFactory getSessionFactory() {
            // 加入扫描 entity 包
            return new SessionFactory(getConfiguration(), "com.winfred.training.neo4j.remote.ogm.entity");
        }
    }

    public static SessionFactory getInstance() {
        return sessionFactory;
    }

}
