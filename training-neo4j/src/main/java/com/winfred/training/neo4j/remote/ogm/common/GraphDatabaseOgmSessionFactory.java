package com.winfred.training.neo4j.remote.ogm.common;

import org.neo4j.ogm.config.ClasspathConfigurationSource;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.config.FileConfigurationSource;
import org.neo4j.ogm.session.SessionFactory;

import java.net.URL;

public class GraphDatabaseOgmSessionFactory {

    private static SessionFactory sessionFactory = Single.getSessionFactory();

    private static class Single {
        public static Configuration getConfiguration() {
            ClasspathConfigurationSource classpathConfigurationSource = new ClasspathConfigurationSource("neo4j-ogm.properties");
            return new Configuration
                    .Builder(classpathConfigurationSource)
                    .connectionLivenessCheckTimeout(2000)
                    .connectionPoolSize(150)
                    .build();
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
