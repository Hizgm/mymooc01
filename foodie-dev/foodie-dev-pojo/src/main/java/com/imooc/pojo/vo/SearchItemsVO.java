package com.imooc.pojo.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * ๅๅๆ็ดข
 * Created by guoming.zhang on 2021/2/26.
 */
@Getter
@Setter
public class SearchItemsVO {
    private String itemId;
    private String itemName;
    private int sellCounts;
    private String imgUrl;
    private int price;
}
