package com.ny.service.impl;

import com.ny.mapper.userMapper;
import com.ny.pojo.User;
import com.ny.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author njx
 * @date 2024/11/4
 * @Description
 */
@Service
public class userServiceImpl implements UserService {
    @Autowired
    private userMapper userMapper;
    @Override
    public User userLogin(String username) {
        return userMapper.userLogin(username);
    }
}
