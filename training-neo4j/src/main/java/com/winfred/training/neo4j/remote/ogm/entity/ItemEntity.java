package com.winfred.training.neo4j.remote.ogm.entity;

import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

@NodeEntity
public class ItemEntity {

    @Id
    private String itemNumber;

    @Relationship(type = "click")
    private Set<ItemRelationship> itemRelationships;

    private Integer categoryId;

    private Integer sellerId;

    private Integer brandId;

    private String status;

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public Set<ItemRelationship> getItemRelationships() {
        return itemRelationships;
    }

    public void setItemRelationships(Set<ItemRelationship> itemRelationships) {
        this.itemRelationships = itemRelationships;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
