package com.modds.xmall.user.api;

import com.modds.xmall.user.domain.User;

public interface UserApi {
    User getUser(long uid);
}
