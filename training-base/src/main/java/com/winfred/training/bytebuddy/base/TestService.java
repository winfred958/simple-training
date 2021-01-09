package com.winfred.training.bytebuddy.base;

import com.winfred.training.bytebuddy.aop.MyLogger;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * @author winfred958
 */
public class TestService {

    @Setter
    private String uuid;

    @Getter
    private String name;

    @MyLogger
    public String getUuid() {
        uuid = UUID.randomUUID().toString();
        return uuid;
    }

    @MyLogger
    public void setName(String name) {
        this.name = name;
    }
}
