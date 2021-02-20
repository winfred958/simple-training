package com.winfred.elastic.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

  private String sku;
  private String spu;

  private String itemDescribe;

  private String itemImage;

  private Boolean isGift;

  /**
   * 售价
   */
  private Double salePrice;

  /**
   * 原价
   */
  private Double originalPrice;

  /**
   * 实付款
   */
  private Double disbursements;

  private Integer quantity;

  private BrandInfo brandInfo;
  private CategoryInfo categoryInfo;
  private ShopInfo shopInfo;
}
