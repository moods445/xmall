package com.modds.xmall.common.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Map;

@Slf4j
public class JWTTemplate implements IJwt {
    public static final JWTTemplate instance = new JWTTemplate();
    public static final String TOKEN_HEADER = "X-Token";
    // 过期时间3 天
    private final static long EXPIRE_TIME = 3L * 24 * 60 * 60 * 1000;

    public static JWTTemplate getInstance() {
        return instance;
    }

    private JWTTemplate() {
    }

    /**
     * @param secret 用户的密码
     * @return 加密的token
     */
    public String sign(Map<String, String> parameters, String secret, Date expireDate) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        JWTCreator.Builder builder = JWT.create()
                .withIssuedAt(new Date());
        parameters.forEach(builder::withClaim);
        return builder.withExpiresAt(expireDate)
                .sign(algorithm);
    }

    /**
     * @param secret 用户的密码
     * @return 加密的token
     */
    public String sign(Map<String, String> parameters, String secret) {
        Date expireDate = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        return sign(parameters, secret, expireDate);
    }

    public String getHeader() {
        return TOKEN_HEADER;
    }

    /**
     * @return token中包含的用户名
     */
    public String getClaim(String token, String name) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim(name).asString();
    }

    /**
     * 校验token是否正确
     *
     * @param token  密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public boolean verify(String token, Map<String, String> parameters, String secret) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        Verification verification = JWT.require(algorithm);
        parameters.forEach(verification::withClaim);

        try {
            verification.build().verify(token);
            return true;
        } catch (JWTVerificationException e) {
            log.warn("verify token error: {}", token, e);
            return false;
        }
    }
}

