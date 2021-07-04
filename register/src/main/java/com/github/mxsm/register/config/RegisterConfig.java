package com.github.mxsm.register.config;

import java.util.UUID;

/**
 * @author mxsm
 * @Date 2021/7/4
 * @Since
 */
public class RegisterConfig {

    private String registerName = UUID.randomUUID().toString();


    public String getRegisterName() {
        return registerName;
    }

    public void setRegisterName(String registerName) {
        this.registerName = registerName;
    }
}
