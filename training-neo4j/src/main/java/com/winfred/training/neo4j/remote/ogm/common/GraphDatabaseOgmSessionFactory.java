package com.winfred.training.neo4j.remote.ogm.common;

import com.winfred.training.neo4j.remote.ogm.entity.ItemEntity;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.config.FileConfigurationSource;
import org.neo4j.ogm.model.Node;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

import java.net.URL;

public class GraphDatabaseOgmSessionFactory {

    public Configuration getConfiguration() {
        URL url = Thread.currentThread().getContextClassLoader().getResource("neo4j-ogm.properties");
        Configuration configuration = new Configuration
                .Builder(new FileConfigurationSource(String.valueOf(url)))
                .connectionLivenessCheckTimeout(2000)
                .connectionPoolSize(150)
                .build();
        return configuration;
    }


    public SessionFactory getSessionFactory() {
        // 加入扫描 entity 包
        return new SessionFactory(getConfiguration(), "com.winfred.training.neo4j.entity");
    }


    public Session getSession() {
        SessionFactory sessionFactory = getSessionFactory();
        return sessionFactory.openSession();
    }


    public Node createNode(ItemEntity itemEntity) {
        Session session = getSession();

        session.save(itemEntity);


        ItemEntity entity = session.load(ItemEntity.class, itemEntity.getItemNumber());


        return null;
    }
}
