package com.winfred.training.neo4j.remote.ogm.service;

import java.util.Collection;
import java.util.Iterator;

public interface Neo4jService<T> {

    Iterator<T> loadAll();

    Iterator<T> loadAll(Collection<T> collection);

    Iterator<T> loadAll(Collection<T> collection, int depth);

    T load(T t);

    T load(T t, int depth);

    void delete(T t);

    T createOrUpdate(T t);
}
