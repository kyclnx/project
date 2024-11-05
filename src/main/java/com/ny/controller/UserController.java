package com.ny.controller;

import com.ny.common.R;
import com.ny.pojo.User;
import com.ny.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


/**
 * @author njx
 * @date 2024/11/3
 * @Description
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public R<String> login(HttpServletRequest request, @RequestBody User user){
        log.info("网页传递过来的用户名{}， 密码{}",user.getUsername(),user.getPassword());
        String password = user.getPassword();
//        password = DigestUtils.md5DigestAsHex(password.getBytes());
        User user1 = userService.userLogin(user.getUsername());
        if (user1 == null) return R.failure("登陆失败，数据库没有查询到结果");
//        log.info("前端传递过来的密码md5加密之后的是{}",password);
        log.info("数据库中的用户名{},密码{}",user1.getUsername(),user1.getPassword());
        if (!user1.getPassword().equals(password)) return R.failure("登陆失败，密码错误le");
        request.getSession().setAttribute("sessionId",user1.getId());
        return R.success("登陆成功");
    }

}