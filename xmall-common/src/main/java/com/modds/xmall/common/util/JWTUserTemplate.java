package com.modds.xmall.common.util;


import com.modds.xmall.user.domain.User;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JWTUserTemplate {
    private static JWTTemplate jwtTemplate = JWTTemplate.getInstance();

    public String sign(User user, String secret) {
        Map<String, String> map = new HashMap<>();
        map.put("uid", user.getId().toString());

        return jwtTemplate.sign(map, secret);
    }

    public String getHeader() {
        return jwtTemplate.getHeader();
    }

    public String getUserName(String token) {
        return jwtTemplate.getClaim(token, "userName");
    }

    public boolean verify(String token, User user, String secret) {
        Map<String, String> map = new HashMap<>();
        map.put("uid", user.getId().toString());
        return jwtTemplate.verify(token, map, secret);
    }
}

