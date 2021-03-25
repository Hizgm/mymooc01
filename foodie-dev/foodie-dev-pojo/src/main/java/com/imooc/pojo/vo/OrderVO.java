package com.imooc.pojo.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderVO {

    private String orderId;         // 订单号
    private MerchantOrdersVO merchantOrdersVO;   // 订单VO
}