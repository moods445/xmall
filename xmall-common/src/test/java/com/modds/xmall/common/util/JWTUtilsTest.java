package com.modds.xmall.common.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class JWTUtilsTest {
    JWTTemplate jwtTemplate = JWTTemplate.getInstance();

    @Test
    public void test() {
        String secret = "secret";
        Map<String, String> parameters = new HashMap<>();
        parameters.put("name", "name");

        String token = jwtTemplate.sign(parameters, secret);

        boolean verify = jwtTemplate.verify(token, parameters, secret);

        Assertions.assertTrue(verify);
    }
}

