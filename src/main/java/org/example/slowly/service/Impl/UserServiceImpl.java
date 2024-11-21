package org.example.slowly.service.Impl;

import org.example.slowly.mapper.UserMapper;
import org.example.slowly.pojo.User;
import org.example.slowly.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author njx
 * @date 2024/11/11
 * @Description
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public User loginUsers(String username) {
        return userMapper.loginUsers(username);
    }
}
