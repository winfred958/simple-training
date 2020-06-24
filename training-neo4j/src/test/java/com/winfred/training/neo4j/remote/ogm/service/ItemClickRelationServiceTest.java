package com.winfred.training.neo4j.remote.ogm.service;


import com.winfred.training.neo4j.remote.ogm.common.GraphDatabaseOgmSessionFactory;
import com.winfred.training.neo4j.remote.ogm.entity.ItemEntity;
import com.winfred.training.neo4j.remote.ogm.entity.ItemRelationship;
import com.winfred.training.neo4j.remote.ogm.service.imp.ItemClickRelationServiceImp;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ItemClickRelationServiceTest {
  
  private Neo4jService<ItemEntity> neo4jService;
  
  @Before
  public void before() {
    neo4jService = new ItemClickRelationServiceImp();
  }
  
  @After
  public void close() {
    GraphDatabaseOgmSessionFactory.getInstance().close();
  }
  
  @Test
  public void createNode() {
    
    // 起始点
    ItemEntity startEntity = new ItemEntity();
    startEntity.setItemNumber(10000001L);
    
    // 结束点
    ItemEntity endEntity = new ItemEntity();
    endEntity.setItemNumber(10000002L);
    
    // 关系
    Set<ItemRelationship> itemRelationships = new HashSet<>();
    
    ItemRelationship relationship = new ItemRelationship();
    relationship.setId(0001L);
    relationship.setStartNode(startEntity);
    relationship.setEndNode(endEntity);
    
    relationship.getCount().incrementAndGet();
    relationship.setUpdateTimestamp(System.currentTimeMillis());
    
    // 点 + 关系
    itemRelationships.add(relationship);
    startEntity.setItemRelationships(itemRelationships);
    
    ItemEntity serviceOrUpdate = neo4jService.createOrUpdate(startEntity);
    System.out.println(serviceOrUpdate);
    
    
    System.out.println(serviceOrUpdate.getItemNumber());
    
    
    Iterator<ItemEntity> itemEntityIterator = neo4jService.loadAll();
    ItemEntity next = null;
    if (itemEntityIterator.hasNext()) {
      next = itemEntityIterator.next();
    }
    
    
  }
  
  
  @Test
  public void load() {
    ItemEntity requestEntity = new ItemEntity();
    requestEntity.setItemNumber(10000001L);
    
    Iterator<ItemEntity> itemEntityIterator = neo4jService.loadAll();
    
    ItemEntity next = null;
    if (itemEntityIterator.hasNext()) {
      next = itemEntityIterator.next();
    }
    
    ItemEntity responseEntity = neo4jService.load(requestEntity);
    
    if (null != responseEntity) {
      Set<ItemRelationship> itemRelationships = responseEntity.getItemRelationships();
      System.out.println(responseEntity.getItemNumber());
    }
  }
  
}
