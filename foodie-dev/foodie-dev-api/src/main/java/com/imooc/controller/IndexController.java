package com.imooc.controller;

import com.imooc.enums.CategoryType;
import com.imooc.enums.YesOrNo;
import com.imooc.pojo.Carousel;
import com.imooc.pojo.Category;
import com.imooc.pojo.vo.CategoryVO;
import com.imooc.pojo.vo.NewItemsVO;
import com.imooc.service.CarouselService;
import com.imooc.service.CategoryService;
import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by guoming.zhang on 2021/2/23.
 */
@Api(value = "首页", tags = {"首页展示的相关接口"})
@RestController
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private CarouselService carouselService;

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "获取轮播图列表", notes = "获取轮播图列表", httpMethod = "GET")
    @GetMapping("/carousel")
    public IMOOCJSONResult carousel() {
        List<Carousel> carousels = carouselService.queryAll(YesOrNo.YES.type);

        return IMOOCJSONResult.ok(carousels);
    }

    /**
     * 首页分类展示需求：
     * 1. 第一次刷新主页查询大分类， 渲染展示到首页
     * 2. 如果鼠标移到大分类，则加载其子分类的内容，如果已经存在子分类，则不需要加载（懒加载）
     */
    @ApiOperation(value = "获取商品一级分类", notes = "获取商品一级分类", httpMethod = "GET")
    @GetMapping("/cats")
    public IMOOCJSONResult cats() {
        List<Category> categories = categoryService.queryAllRootLevelCat(CategoryType.first.type);
        return IMOOCJSONResult.ok(categories);
    }

    @ApiOperation(value = "获取商品二级分类", notes = "获取商品二级分类", httpMethod = "GET")
    @GetMapping("/subCat/{rootCatId}")
    public IMOOCJSONResult subCat(
            @ApiParam(name = "rootCatId", value = "一级分类ID", required = true)
            @PathVariable Integer rootCatId) {
        if (rootCatId == null) {
            return IMOOCJSONResult.errorMsg("分类不存在");
        }
        List<CategoryVO> subCatList = categoryService.getSubCatList(rootCatId);
        return IMOOCJSONResult.ok(subCatList);
    }

    @ApiOperation(value = "查询每个一级分类下的最新6条商品数据", notes = "获取最新商品数据", httpMethod = "GET")
    @GetMapping("/sixNewItems/{rootCatId}")
    public IMOOCJSONResult sixNewItems(
            @ApiParam(name = "rootCatId", value = "一级分类ID", required = true)
            @PathVariable Integer rootCatId) {
        if (rootCatId == null) {
            return IMOOCJSONResult.errorMsg("分类不存在");
        }
        List<NewItemsVO> sixNewItemsList = categoryService.getSixNewItemsLazy(rootCatId);
        return IMOOCJSONResult.ok(sixNewItemsList);
    }
}
