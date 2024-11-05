# 登录功能的实现

总结一下，我觉得之前设置不合理的地方在于，我的登陆密码的处理，完全是依靠后端进行的

```java
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public R<String> login(HttpServletRequest request, @RequestBody User user){
//        log.info("网页传递过来的用户名{}， 密码{}",user.getUsername(),user.getPassword());
        String password = user.getPassword();
//        password = DigestUtils.md5DigestAsHex(password.getBytes());
        User user1 = userService.userLogin(user.getUsername());
        if (user1 == null) return R.failure("登陆失败，数据库没有查询到结果");
//        log.info("前端传递过来的密码md5加密之后的是{}",password);
//        log.info("数据库中的用户名{},密码{}",user1.getUsername(),user1.getPassword());
        if (!user1.getPassword().equals(password)) return R.failure("登陆失败，密码错误le");
        request.getSession().setAttribute("sessionId",user1.getId());
        return R.success("登陆成功");
    }
```



```vue
 const encPassword = CryptoJS.MD5(form.value.password).toString();      //在LoginView.vue的165行
```

减少了后端的压力
