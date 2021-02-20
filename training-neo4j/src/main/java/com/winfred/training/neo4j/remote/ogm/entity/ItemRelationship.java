package com.winfred.training.neo4j.remote.ogm.entity;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@RelationshipEntity(type = "click")
public class ItemRelationship implements Serializable {

  private static final long serialVersionUID = 972699365672859733L;

  private Long id;

  @StartNode
  ItemEntity startNode;

  @EndNode
  ItemEntity endNode;

  AtomicLong count;

  Long updateTimestamp;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ItemEntity getStartNode() {
    return startNode;
  }

  public void setStartNode(ItemEntity startNode) {
    this.startNode = startNode;
  }

  public ItemEntity getEndNode() {
    return endNode;
  }

  public void setEndNode(ItemEntity endNode) {
    this.endNode = endNode;
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

  public Long getUpdateTimestamp() {
    return updateTimestamp;
  }

  public void setUpdateTimestamp(Long updateTimestamp) {
    this.updateTimestamp = updateTimestamp;
  }

  @Override
  public int hashCode() {
    return endNode.getItemNumber().hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ItemRelationship that = (ItemRelationship) o;
    return Objects.equals(startNode, that.startNode) &&
        Objects.equals(endNode, that.endNode) &&
        Objects.equals(count, that.count) &&
        Objects.equals(updateTimestamp, that.updateTimestamp);
  }
}
