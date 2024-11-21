package org.example.slowly.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.example.slowly.Common.R;
import org.example.slowly.pojo.User;
import org.example.slowly.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author njx
 * @date 2024/11/8
 * @Description
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    /**
     * 登录功能的实现
     * @param user
     * @param httpSession
     * @return
     */
    @PostMapping("/login")
    public R<Object> login(@RequestBody User user, HttpSession httpSession) {
        log.info("网页传递来的用户名：{},密码{}", user.getUsername(), user.getPassword());
        String password = user.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        User user1 = userService.loginUsers(user.getUsername());
        if (user1 == null) {
            return R.failed("登录失败，没有查询到结果");
        }
        if (!user1.getPassword().equals(password)) {
            return R.failed("用户名或者密码错误");
        }
        httpSession.setAttribute("currentUser", user1.getUsername());
        log.info("登陆成功");
        return R.succeed("登录成功");
    }


    /**
     * 获取用户名的实现
     * @param session
     * @return
     */
    @GetMapping("/getUsername")
    public R<String> getUsername(HttpSession session){
        log.info("将用户名封装到R中");
        return R.succeed((String) session.getAttribute("currentUser"));
    }

    /**
     * 退出功能的实现
     * @param session
     * @return
     */
    @PostMapping("/logout")
    public R<Object> logout(HttpSession session){
        session.invalidate(); // 清除当前会话
        log.info("用户已退出登录");
        return R.succeed("退出成功");
    }

}
