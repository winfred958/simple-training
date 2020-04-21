package com.winfred.training.designpattern.behavioral.chain.auth;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginHandler implements Handler {

    @Override
    public void doHandler(MemberEntity member) {
        log.info("{}", this.getClass().getName());
    }
}
