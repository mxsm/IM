package com.github.mxsm.magpiebridge.config;

/**
 * @author mxsm
 * @Date 2021/7/22
 * @Since 0.1
 */
public abstract class Seed {

    public static long seed(){
        return (long)(Math.random() * 100);
    }

}
