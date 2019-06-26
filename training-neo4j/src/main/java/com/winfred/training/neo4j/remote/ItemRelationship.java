package com.winfred.training.neo4j.remote;

import org.neo4j.graphdb.RelationshipType;

import java.util.concurrent.atomic.AtomicLong;

public class ItemRelationship implements RelationshipType {

    private String name;
    private AtomicLong count;

    public ItemRelationship(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    public AtomicLong getCount() {
        if (null == count) {
            count = new AtomicLong(0);
        }
        return count;
    }

    public void setCount(AtomicLong count) {
        this.count = count;
    }
}
