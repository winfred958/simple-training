package com.winfred.training.neo4j.remote;

import org.neo4j.driver.v1.*;
import org.neo4j.graphdb.Node;

/**
 * 使用 java driver 语句太恶心, 放弃
 *
 * @author kevin
 */
public class GraphDatabaeSession {

    public Session getSession() {
        Driver driver = GraphDatabase
                .driver(
                        "bolt://cdh-172-16-1-36:7687",
                        AuthTokens.basic("neo4j", "Cwup9RGxgVEHUkJa5")
                );
        return driver.session();
    }


    public Node createNode() {
        Session session = getSession();


        session.writeTransaction(new TransactionWork<Object>() {
            @Override
            public Object execute(Transaction tx) {

                return null;
            }
        });

        return null;
    }
}
