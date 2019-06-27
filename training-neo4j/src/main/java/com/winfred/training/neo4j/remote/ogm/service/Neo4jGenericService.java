package com.winfred.training.neo4j.remote.ogm.service;

import com.winfred.training.neo4j.remote.ogm.common.ReflactorUtil;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.session.Session;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
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

    /**
     * 默认 depth = 1
     *
     * @param t
     * @return
     */
    @Override
    public T load(T t) {
        return load(t, DEPTH_ENTITY);
    }

    @Override
    public T load(T t, int depth) {
        String id = getAnnotationFieldValue(t, Id.class);
        if (id == null) {
            return null;
        }
        return session.load(getEntityType(), id, depth);
    }

    @Override
    public void delete(T t) {
        session.delete(t);
    }

    @Override
    public T createOrUpdate(T t) {
        session.save(t, DEPTH_ENTITY);
        return load(t);
    }

    private String getAnnotationFieldValue(T t, Class<? extends Annotation> clazz) {
        Field field = ReflactorUtil.getAnnotationField(t, clazz);
        if (field == null) {
            return null;
        }
        try {
            Object value = field.get(t);
            if (null == value) {
                return null;
            }
            return String.valueOf(value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取 entity 类型
     *
     * @return
     */
    abstract Class<T> getEntityType();
}