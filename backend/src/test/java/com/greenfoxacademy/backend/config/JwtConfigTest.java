package com.greenfoxacademy.backend.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtConfigTest {

    @Autowired
    private JwtConfig jwtConfig = new JwtConfig();

    @Test
    void getExpirationTime() {
        assertEquals(86400000, jwtConfig.getExpirationTime());
    }
}