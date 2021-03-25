package com.imooc.controller;

import com.imooc.pojo.Bo.ShopCatBO;
import com.imooc.pojo.ItemsSpec;
import com.imooc.pojo.vo.ShopCartItemsVO;
import com.imooc.service.ItemService;
import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by guoming.zhang on 2021/3/4.
 */
@Api(value = "购物车相关接口", tags = "购物车相关接口")
@RequestMapping("/shopcart")
@RestController
public class ShopCatController {

    @ApiOperation(value = "添加购物车", notes = "添加购物车", httpMethod = "POST")
    @PostMapping("/add")
    public IMOOCJSONResult add(@ApiParam(name = "userId", value = "用户id", required = true)@RequestParam String userId,
                               @ApiParam(name = "shopCatBo", value = "客户端商品传过来的实体")@RequestBody ShopCatBO shopCatBO,
                               HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isBlank(userId)) {
            return IMOOCJSONResult.errorMsg("");
        }
        //TODO 前端用户在登录的情况下，添加商品到购物车，会同时在后端同步购物车到redis缓存
        System.out.println("shopCat:" + shopCatBO);

        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "删除购物车中的商品", notes = "删除购物车中的商品", httpMethod = "POST")
    @PostMapping("/del")
    public IMOOCJSONResult add(@ApiParam(name = "userId", value = "用户id", required = true)@RequestParam String userId,
                               @ApiParam(name = "itemSpecId", value = "商品规格id")@RequestParam String itemSpecId,
                               HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(itemSpecId)) {
            return IMOOCJSONResult.errorMsg("");
        }
        //TODO 用户在页面删除购物车中的商品数据，如果此时用户已经登录，则需要同步删除后端购物车中的数据

        return IMOOCJSONResult.ok();
    }

}
