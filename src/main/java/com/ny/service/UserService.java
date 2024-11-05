package com.ny.service;
import com.ny.pojo.User;

/**
 * @author njx
 * @date 2024/11/4
 * @Description
 */
public interface UserService {
    //根据用户名查询登录人员信息
    User userLogin(String username);
}
