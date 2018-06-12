package com.imooc.miaosha.controller;

import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.redis.MiaoshaUserKey;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.service.GoodsService;
import com.imooc.miaosha.service.MiaoshaUserService;
import com.imooc.miaosha.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    private static Logger logger = LoggerFactory.getLogger(GoodsController.class);

    private final GoodsService goodsService;

    private MiaoshaUserService miaoshaUserService;

    private RedisService redisService;

    @Autowired
    public GoodsController(MiaoshaUserService miaoshaUserService, RedisService redisService, GoodsService goodsService) {
        this.miaoshaUserService = miaoshaUserService;
        this.redisService = redisService;
        this.goodsService = goodsService;
    }

    @RequestMapping("/to_list")
    public String toGoods(Model model, MiaoshaUser user) {
        //user对象由resolver注入
        model.addAttribute("user", user);
        //查询商品列表
        List<GoodsVo> goodsVoList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsVoList);
        return "goods_list";
    }

    @RequestMapping("/to_detail/{goodsId}")
    public String toDetail(Model model, MiaoshaUser user, @PathVariable("goodsId") long goodsId) {
        //user对象由resolver注入
        model.addAttribute("user", user);
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods",goods);
        //
        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();
        int miaoshaStatus;
        int remainSeconds;
        if (now < startAt) {
            //秒杀还没开始,倒计时
            miaoshaStatus = 0;
            remainSeconds = (int)(startAt - now)/1000;
        } else if (now > endAt) {
            //秒杀已结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        } else {
            //秒杀进行中
            miaoshaStatus = 1;
            remainSeconds =0;
        }
        model.addAttribute("miaoshaStatus", miaoshaStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        return "goods_detail";
    }

}
