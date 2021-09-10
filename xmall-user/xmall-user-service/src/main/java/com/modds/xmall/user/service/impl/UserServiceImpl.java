package com.modds.xmall.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.modds.xmall.user.domain.User;
import com.modds.xmall.user.mapper.UserMapper;
import com.modds.xmall.user.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author
 * @since 2021-09-09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public boolean save(User entity) {
        boolean success;
        entity.setUpdateTime(new Date());
        if (entity.getId() == null) {
            entity.setCreateTime(new Date());
            success = super.save(entity);
        } else {
            success = updateById(entity);
        }

        return success;
    }
}
