package com.imooc.controller;

import com.imooc.pojo.Bo.UserBo;
import com.imooc.pojo.Users;
import com.imooc.service.UserService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.JsonUtils;
import com.imooc.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Created by guoming.zhang on 2021/1/8.
 */
@Api(value = "注册登录", tags = {"用于注册登录的相关接口"})
@RestController
@RequestMapping("/passport")
public class PassportController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户名是否存在", notes = "用户名是否存在", httpMethod = "GET")
    @GetMapping("/usernameIsExist")
    public IMOOCJSONResult usernameIsExist(@RequestParam(value = "username", required = false) String username) {//requestParam 添加参数
        // 1.判断用户名不能为空
        if(StringUtils.isBlank(username)) {
            return IMOOCJSONResult.errorMsg("用户名不为空");
        }
        // 2.查找注册的用户名是否存在
        boolean isExist = userService.queryUserNameIsExist(username);
        if (isExist) {
            return IMOOCJSONResult.errorMsg("用户名已存在");
        }

        // 3.请求成功，用户名没有重复
        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "用户的注册", notes = "用户的注册", httpMethod = "POST")
    @PostMapping("/regist")
    public IMOOCJSONResult regist(@RequestBody @Valid UserBo userBo,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {//request body 请求的是一个对象
        String username = userBo.getUsername();
        String password = userBo.getPassword();
        String confirmPassword = userBo.getConfirmPassword();
        //0 判断用户名和密码必须不为空
        if (StringUtils.isBlank(confirmPassword)) {
            return IMOOCJSONResult.errorMsg("用户名或密码不能为空");
        }
        //1.查询用户名是否存在
        if (userService.queryUserNameIsExist(username)) {
            return IMOOCJSONResult.errorMsg("用户名已存在");
        }
        //2.密码长度不能少于6位
        if (confirmPassword.length() < 6) {
            return IMOOCJSONResult.errorMsg("密码长度不能少于6位数");
        }
        //3.判断两次密码是否一致
        if (!password.equals(confirmPassword)) {
            return IMOOCJSONResult.errorMsg("输入的两次密码不一致");
        }
        //4.实现注册
        Users user = userService.createUser(userBo);
        // 对cookie 加密
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(user), true);
        return IMOOCJSONResult.ok(user);
    }

    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    @PostMapping("/login")
    public IMOOCJSONResult login(@RequestBody @Valid UserBo userBo,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        String username = userBo.getUsername();
        String password = userBo.getPassword();
        //1. 实现登录
        Users users = userService.queryUserForLogin(username, MD5Utils.getMD5Str(password));

        if (users == null) {
            return IMOOCJSONResult.errorMsg("用户名或密码不正确");
        }

        Users usersResult = setNullProperty(users);
        // 对cookie 加密
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(usersResult), true);
        // TODO 生成用户token, 存入redis会话
        // TODO 同步购物车数据
        return IMOOCJSONResult.ok(usersResult);
    }

    @ApiOperation(value = "用户退出", notes = "用户退出", httpMethod = "POST")
    @PostMapping("/logout")
    public IMOOCJSONResult logout(@RequestParam String userId,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {
        //清除用户相关信息的cookie
        CookieUtils.deleteCookie(request, response, "user");

        //TODO 用户退出登录，需要清空购物车
        //TODO 分布式会话中需要清除用户数据

        return IMOOCJSONResult.ok();
    }

    private Users setNullProperty(Users userResult) {
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);
        return userResult;
    }
}
