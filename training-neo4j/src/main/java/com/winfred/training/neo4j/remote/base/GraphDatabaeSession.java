package com.winfred.training.neo4j.remote.base;

import com.winfred.training.neo4j.remote.ogm.entity.ItemEntity;
import org.neo4j.driver.v1.*;
import org.neo4j.graphdb.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * 使用 java driver CQL 语句太恶心, 放弃
 * <p>
 * 使用 OGM api
 *
 * @author kevin
 */
public class GraphDatabaeSession {

    public Session getSession() {
        Driver driver = GraphDatabase
                .driver(
                        "bolt://cdh-172-16-1-36:7687",
                        AuthTokens.basic("neo4j", "xxxxxxxxxxxxxxxxxx")
                );
        return driver.session();
    }


    public Node createNode() {
        Session session = getSession();


        session.writeTransaction(new ItemTransactionWork(new ItemEntity()));

        return null;
    }

    static class ItemTransactionWork implements TransactionWork<StatementResult> {

        private ItemEntity itemEntity;

        public ItemTransactionWork(ItemEntity itemEntity) {
            itemEntity = itemEntity;
        }

        @Override
        public StatementResult execute(Transaction tx) {
            Map<String, Object> parameterMap = new HashMap<>();

            parameterMap.put("item_number", itemEntity.getItemNumber());

            StatementResult statementResult = tx.run(
                    "CREATE (a:ItemEntity) SET id = {item_number} RETURN a",
                    parameterMap
            );
            Record record = statementResult.next();

            return statementResult;
        }
    }
}
