package com.winfred.guice.bind.link.impl;

import com.winfred.guice.bind.link.UserService;
import com.winfred.guice.bind.link.entity.UserInfo;

public class UserServiceImpl implements UserService {

    @Override
    public UserInfo getUserInfo(String userId) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setRuleId("xxxxxxxxxxxxxx");
        return userInfo;
    }
}
