package com.winfred.elastic.entity;

import com.winfred.elastic.annotation.Id;
import lombok.Data;
import org.elasticsearch.common.geo.GeoPoint;

import java.util.List;

/**
 * 订单信息
 */
@Data
public class OrderInfo {

  @Id
  private String orderId;
  private Long orderTimestamp;

  private String transactionId;
  private Long transactionTimestamp;

  private String userId;

  private Integer orderStatus;
  private Integer payStatus;
  private Integer shippingStatus;

  private Double orderAmount;

  private String country;
  private String province;
  private String city;
  private String address;
  private String address2;

  private String ip;
  private String sourceFlag;

  private String version;
  private GeoPoint orderShippingLocation;
  private GeoPoint orderIpLocation;

  private List<OrderItem> orderItems;

  private Long lastUpdateTimestamp;
}
