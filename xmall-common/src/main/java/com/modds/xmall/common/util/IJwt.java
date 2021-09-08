package com.modds.xmall.common.util;

import java.util.Date;
import java.util.Map;

public interface IJwt {
    String sign(Map<String, String> parameters, String secret, Date expireDate);
    String sign(Map<String, String> parameters, String secret) ;
    String getHeader() ;
    String getClaim(String token, String name) ;
    boolean verify(String token, Map<String, String> parameters, String secret);
}
