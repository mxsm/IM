package com.github.mxsm.magpiebridge.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author mxsm
 * @Date 2021/7/22
 * @Since
 */
class SeedTest {

    @Test
    void seed() {
        long seed = Seed.seed();
        Assertions.assertTrue(seed < 100);
    }
}