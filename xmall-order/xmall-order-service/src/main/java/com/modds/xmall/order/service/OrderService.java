package com.modds.xmall.order.service;

import com.modds.xmall.user.api.UserApi;
import com.modds.xmall.user.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderService {
    @DubboReference
    private UserApi userApi;

    public void getOrderByUid(long uid) {
        User user = userApi.getUser(uid);
        log.info("user: {}", user);
    }
}
