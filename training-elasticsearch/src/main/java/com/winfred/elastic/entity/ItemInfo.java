package com.winfred.elastic.entity;

import com.winfred.elastic.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemInfo {
  
  @Id
  private String itemId;
  
  private String describe;
}
