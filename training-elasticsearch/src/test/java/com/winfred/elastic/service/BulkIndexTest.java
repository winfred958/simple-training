package com.winfred.elastic.service;

import com.winfred.elastic.base.BaseTest;
import com.winfred.elastic.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomUtils;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.common.geo.GeoPoint;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class BulkIndexTest extends BaseTest {
  
  @Autowired
  private ElasticsearchBaseService elasticsearchService;
  
  private List<OrderInfo> orderInfoList;
  private Integer MAX_SIZE_PRE = 9999999;
  
  private AtomicLong orderId = new AtomicLong(99999);
  
  @Autowired
  private ElasticsearchBaseService elasticsearchBaseService;
  
  @Before
  public void randomData() {
    for (int i = 0; i < MAX_SIZE_PRE; i++) {
      orderInfoList.add(getRandomOrderInfo());
    }
  }
  
  @Test
  public void bulkIndexTest() {
    List<BulkResponse> bulkResponses = elasticsearchBaseService.bulkIndex(orderInfoList, "");
    
    log.info("{}", bulkResponses.size());
  }
  
  
  public List<OrderInfo> randomData(int partition, int batchSize) {
    
    return null;
  }
  
  
  private OrderInfo getRandomOrderInfo() {
    OrderInfo orderInfo = new OrderInfo();
    
    
    orderInfo.setOrderId(String.valueOf(orderId.incrementAndGet()));
    long currentTimeMillis = System.currentTimeMillis();
    orderInfo.setOrderTimestamp(currentTimeMillis - getRandomInt(5000, 30000));
    orderInfo.setTransactionId(DigestUtils.sha1Hex(String.valueOf(orderId).getBytes()));
    orderInfo.setOrderTimestamp(currentTimeMillis);
    
    orderInfo.setUserId(String.valueOf(getRandomInt(9999, 999999)));
    
    orderInfo.setOrderStatus(6);
    orderInfo.setPayStatus(6);
    orderInfo.setShippingStatus(6);
    
    List<OrderItem> orderItems = getOrderItemList();
    
    orderInfo.setOrderItems(orderItems);
    
    orderInfo.setOrderAmount(getOrderAmount(orderItems));
    orderInfo.setCountry("");
    orderInfo.setProvince("");
    orderInfo.setCity("");
    orderInfo.setAddress("");
    orderInfo.setAddress2("");
    
    // 维度
    Double lat = 0.0;
    // 精度
    Double lon = RandomUtils.nextDouble(20.0, 50.0);
    GeoPoint orderGeo = new GeoPoint();
    if (RandomUtils.nextInt(100, 103) / 2 == 0) {
      lat = RandomUtils.nextDouble(-11.0, 135.0);
      orderGeo.reset(lat, lon);
    } else {
      lat = RandomUtils.nextDouble(-138.0, -60.0);
      orderGeo.reset(lat, lon);
    }
    
    GeoPoint ipPoint = new GeoPoint(orderGeo.lat() + getRandomInt(-5, 5), orderGeo.lon() + getRandomInt(-5, 5));
    
    orderInfo.setOrderShippingLocation(orderGeo);
    orderInfo.setOrderIpLocation(ipPoint);
    
    orderInfo.setLastUpdateTimestamp(currentTimeMillis);
    
    return orderInfo;
  }
  
  private List<OrderItem> getOrderItemList() {
    int quantity = getRandomInt(2, 20);
    List<OrderItem> orderItems = new ArrayList<>(quantity);
    for (int i = 0; i < quantity; i++) {
      OrderItem orderItem = getRandomOrderItem();
      orderItems.add(orderItem);
    }
    return orderItems;
  }
  
  private Double getOrderAmount(List<OrderItem> orderItems) {
    return orderItems
        .stream()
        .map(item -> {
          BigDecimal quantity = new BigDecimal(item.getQuantity());
          BigDecimal price = new BigDecimal(item.getDisbursements());
          
          return quantity.divide(price);
        })
        .reduce((a, b) -> {
          return a.add(b);
        })
        .get()
        .doubleValue();
  }
  
  private OrderItem getRandomOrderItem() {
    int itemId = getRandomInt(10000000, 99999999);
    
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(itemId);
    stringBuffer.append("-");
    stringBuffer.append(0);
    String spu = stringBuffer.toString();
    
    String sku = getHashName(itemId);
    OrderItem orderItem = new OrderItem();
    orderItem.setSku(sku);
    orderItem.setSpu(spu);
    
    orderItem.setItemDescribe("商品描述");
    orderItem.setItemImage("xxxxxxx/xxxx");
    orderItem.setIsGift(false);
    
    orderItem.setOriginalPrice(0.0);
    orderItem.setSalePrice(0.0);
    orderItem.setDisbursements(0.0);
    
    orderItem.setQuantity(getRandomInt(1, 5));
    
    orderItem.setBrandInfo(getRandomBrande());
    orderItem.setCategoryInfo(getRandomCategory());
    orderItem.setShopInfo(getRandomShopInfo());
    
    return orderItem;
  }
  
  private BrandInfo getRandomBrande() {
    int randomId = getRandomInt(1, 20000);
    BrandInfo categoryInfo = new BrandInfo();
    categoryInfo.setBrandId(randomId);
    categoryInfo.setBrandName(getHashName(randomId));
    return categoryInfo;
  }
  
  private CategoryInfo getRandomCategory() {
    int randomId = getRandomInt(1, 5000);
    CategoryInfo categoryInfo = new CategoryInfo();
    categoryInfo.setCategoryId(randomId);
    categoryInfo.setCategoryName(getHashName(randomId));
    return categoryInfo;
  }
  
  private ShopInfo getRandomShopInfo() {
    int shopId = getRandomInt(1, 20000);
    ShopInfo shopInfo = new ShopInfo();
    shopInfo.setShopId(shopId);
    shopInfo.setShopName(getHashName(shopId));
    return shopInfo;
  }
  
  private int getRandomInt(int min, int max) {
    return RandomUtils.nextInt(min, max);
  }
  
  private String getHashName(String id) {
    return DigestUtils.sha1Hex(id.getBytes()).substring(0, 16);
  }
  
  private String getHashName(int id) {
    return DigestUtils.sha1Hex(String.valueOf(id).getBytes()).substring(0, 16);
  }
  
  
}

