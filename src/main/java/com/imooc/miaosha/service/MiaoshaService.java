package com.imooc.miaosha.service;

import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.domain.OrderInfo;
import com.imooc.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MiaoshaService {

    private GoodsService goodsService;
    private OrderService orderService;

    @Autowired
    public MiaoshaService(GoodsService goodsService, OrderService orderService) {
        this.goodsService = goodsService;
        this.orderService = orderService;
    }

    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goods) {
        /* *
         * @description:减库存 下订单 写入秒杀订单
         * 调用dao对象对应的service来处理 让程序逻辑更加清晰
         * @param: [user, goods]
         * @return: com.imooc.miaosha.domain.OrderInfo
         */
        goodsService.reduceStock(goods);
        return orderService.createOrder(user, goods);

    }
}
