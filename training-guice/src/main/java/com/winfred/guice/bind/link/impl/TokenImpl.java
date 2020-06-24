package com.winfred.guice.bind.link.impl;

import com.alibaba.fastjson.JSON;
import com.google.inject.Inject;
import com.winfred.guice.bind.link.Token;
import com.winfred.guice.bind.link.UserService;
import com.winfred.guice.bind.link.entity.UserInfo;

public class TokenImpl implements Token {
  
  
  /**
   * field inject
   */
  @Inject
  private UserService userService;
  
  /**
   * construct inject
   *
   * @param userService
   */
  @Inject
  public TokenImpl(UserService userService) {
    this.userService = userService;
  }
  
  @Override
  public String getToken(String userId) {
    UserInfo userInfo = userService.getUserInfo(userId);
    return JSON.toJSONString(userInfo);
  }
}
