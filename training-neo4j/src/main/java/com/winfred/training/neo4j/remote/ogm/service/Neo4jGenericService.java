package com.winfred.training.neo4j.remote.ogm.service;

import org.neo4j.ogm.session.Session;

import java.util.Collection;
import java.util.Iterator;

public abstract class Neo4jGenericService<T> implements Neo4jService<T> {
    private static final int DEPTH_LIST = 0;
    private static final int DEPTH_ENTITY = 1;

    private Session session;

    @Override
    public Iterator<T> loadAll(Collection<T> collection) {
        return loadAll(collection, DEPTH_LIST);
    }

    @Override
    public Iterator<T> loadAll(Collection<T> collection, int depth) {
        Collection<T> tCollection = session.loadAll(collection, depth);
        return tCollection.iterator();
    }

    @Override
    public T load(T t) {

        return null;
    }

    @Override
    public void delete(T t) {

    }

    @Override
    public T createOrUpdate(T t) {
        return null;
    }

    private Object getAnnocationValue() {
        return null;
    }

    /**
     * 获取 entity 类型
     *
     * @return
     */
    abstract Class<T> getEntityType();
}
