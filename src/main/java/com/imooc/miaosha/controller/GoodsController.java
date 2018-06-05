package com.imooc.miaosha.controller;

import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.redis.MiaoshaUserKey;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.service.MiaoshaUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    private static Logger logger = LoggerFactory.getLogger(GoodsController.class);


    private MiaoshaUserService miaoshaUserService;

    private RedisService redisService;

    @Autowired
    public GoodsController(MiaoshaUserService miaoshaUserService, RedisService redisService) {
        this.miaoshaUserService = miaoshaUserService;
        this.redisService = redisService;
    }

    @RequestMapping("/to_list")
    public String toGoods(Model model, MiaoshaUser user){
        //user对象由resolver注入
        model.addAttribute("user", user);
        return "goods_list";
    }
}
