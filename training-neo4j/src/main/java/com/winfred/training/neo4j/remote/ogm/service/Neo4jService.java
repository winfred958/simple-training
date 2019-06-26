package com.winfred.training.neo4j.remote.ogm.service;

import java.util.Collection;
import java.util.Iterator;

public interface Neo4jService<T> {

    Iterator<T> loadAll(Collection<T> collection);

    Iterator<T> loadAll(Collection<T> collection, int depth);

    T load(T t);

    void delete(T t);

    T createOrUpdate(T t);
}
