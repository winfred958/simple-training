package com.winfred.training.neo4j.remote.ogm.service;

import com.winfred.training.neo4j.remote.ogm.common.GraphDatabaseOgmSessionFactory;
import com.winfred.training.neo4j.remote.ogm.common.ReflactorUtil;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.transaction.Transaction;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;


public abstract class Neo4jGenericService<T> implements Neo4jService<T> {
  private static final int DEPTH_LIST = 0;
  private static final int DEPTH_ENTITY = 1;

  private static Session session = GraphDatabaseOgmSessionFactory.getInstance().openSession();

  @Override
  public Iterator<T> loadAll() {
    return session.loadAll(getEntityType()).iterator();
  }

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
    Long id = getAnnotationFieldValue(t, Id.class);
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
    Transaction transaction = session.beginTransaction();
    session.save(t, DEPTH_ENTITY);
    transaction.commit();
    return load(t);
  }

  private Long getAnnotationFieldValue(T t, Class<? extends Annotation> clazz) {
    Field field = ReflactorUtil.getAnnotationField(t, clazz);
    if (field == null) {
      return null;
    }
    try {
      field.setAccessible(true);
      Object value = field.get(t);
      if (null == value) {
        return null;
      }
      return Long.valueOf(String.valueOf(value));
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
  public abstract Class<T> getEntityType();
}
