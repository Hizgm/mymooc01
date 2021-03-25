package com.imooc.controller.center;

import com.imooc.pojo.Bo.center.CenterUserBO;
import com.imooc.pojo.Users;
import com.imooc.service.center.CenterUserService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Created by guoming.zhang on 2021/3/24.
 */

@Api(value = "用户信息接口", tags = {"用户信息相关接口"})
@RequestMapping("userInfo")
@RestController
public class CenterUserController {

    @Autowired
    private CenterUserService centerUserService;

    @PostMapping("/update")
    @ApiOperation(value = "修改用户信息", notes = "修改用户信息", httpMethod = "POST")
    public IMOOCJSONResult update(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @RequestBody @Valid CenterUserBO centerUserBO,
            HttpServletRequest request,
            HttpServletResponse response) {
        Users users = centerUserService.updateUserInfo(userId, centerUserBO);
        Users usersResult = setNullProperty(users);
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(usersResult), true);
        //TODO 后续要改，增加令牌token，会整合进redis, 分布式会话
        return IMOOCJSONResult.ok(usersResult);
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
