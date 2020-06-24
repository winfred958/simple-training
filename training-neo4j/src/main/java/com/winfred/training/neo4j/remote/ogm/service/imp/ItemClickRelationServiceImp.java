package com.winfred.training.neo4j.remote.ogm.service.imp;

import com.winfred.training.neo4j.remote.ogm.entity.ItemEntity;
import com.winfred.training.neo4j.remote.ogm.service.Neo4jGenericService;

public class ItemClickRelationServiceImp extends Neo4jGenericService<ItemEntity> {
  
  @Override
  public Class<ItemEntity> getEntityType() {
    return ItemEntity.class;
  }
}
