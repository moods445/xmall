package com.modds.xmall.user.service;

import com.modds.xmall.user.api.UserApi;
import com.modds.xmall.user.domain.User;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

@Service
@DubboService
public class UserService implements UserApi {
    @Override
    public User getUser(long uid) {
        return User.builder()
                .id(uid)
                .build();
    }
}
