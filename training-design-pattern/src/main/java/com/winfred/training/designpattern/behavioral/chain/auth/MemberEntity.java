package com.winfred.training.designpattern.behavioral.chain.auth;

import lombok.Data;

@Data
public class MemberEntity {
    private String userName;
    private String password;
    private String token;
    private String role;
    private String permission;
}
