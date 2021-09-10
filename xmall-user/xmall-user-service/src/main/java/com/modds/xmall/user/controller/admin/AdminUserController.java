package com.modds.xmall.user.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modds.xmall.user.domain.User;
import com.modds.xmall.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/")
public class AdminUserController {
    @Autowired
    private IUserService userService;

    @GetMapping("/users")
    public Page<User> getUsers(@RequestParam(value = "page", defaultValue = "1") int page,
                               @RequestParam(value = "size", defaultValue = "10") int size,
                               @RequestParam(value = "sort", defaultValue = "") String sortStr) {
        Page<User> pg = new Page<>(page, size);
        return userService.page(pg);
    }

    @DeleteMapping("/user/{id}")
    public void del(@PathVariable("id") long uid) {
        userService.removeById(uid);
    }

    @PutMapping("/user")
    public void save(@Validated @RequestBody User user) {
        userService.save(user);
    }
}