package com.modds.xmall.user.controller.app;

import com.modds.xmall.user.domain.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/user/")
public class UserAppController {

    @GetMapping("/{uid}")
    public User getUsers(@PathVariable("uid") long uid) {
        return new User();
    }
}
