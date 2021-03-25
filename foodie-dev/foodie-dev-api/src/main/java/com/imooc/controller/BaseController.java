package com.imooc.controller;

import org.springframework.stereotype.Controller;

/**
 * Created by guoming.zhang on 2021/2/25.
 */
@Controller
public class BaseController {

    static final String FOODIE_SHOPCART = "shopcart";
    static final Integer COMMENT_PAGE_SIZE = 10;
    static final Integer PAGE_SIZE = 20;

    // 支付中心的调用地址
    static final String paymentUrl = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";// produce

    //微信支付成功 -> 支付中心 -> 天天吃货平台
    static final String payReturnUrl = "http://localhost:8088/orders/notifyMerchantOrderPaid";
}
