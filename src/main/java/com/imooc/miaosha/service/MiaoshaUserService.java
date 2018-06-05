package com.imooc.miaosha.service;

import com.imooc.miaosha.controller.GoodsController;
import com.imooc.miaosha.dao.MiaoshaUserDao;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.exception.GlobalException;
import com.imooc.miaosha.redis.MiaoshaUserKey;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.util.MD5Util;
import com.imooc.miaosha.util.UUIDUtil;
import com.imooc.miaosha.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class MiaoshaUserService {

    //cookie(name, value)中存放token的name值
    public static final String COOKIE_NAME_TOKEN = "token";

    private static Logger logger = LoggerFactory.getLogger(GoodsController.class);

    private RedisService redisService;

    private MiaoshaUserDao miaoshaUserDao;

    @Autowired
    public MiaoshaUserService(MiaoshaUserDao miaoshaUserDao, RedisService redisService) {
        this.miaoshaUserDao = miaoshaUserDao;
        this.redisService = redisService;
    }

    private MiaoshaUser getById(long id) {
        return miaoshaUserDao.getById(id);
    }

    public MiaoshaUser getByToken(HttpServletResponse response, String token) {
        //通过token得到对应的session的user对象
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        //todo 这里默认用户上传的token是合法的token 并没有对token有误 (user=null)的情况进行处理
        MiaoshaUser user = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
        if (user != null) {
            //更新cookie有效期
            updateCookie(response, token);
        }
        return user;
    }

    public boolean login(HttpServletResponse response, LoginVo loginVo) {
        if (loginVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        // 判断手机号是否存在
        MiaoshaUser user = getById(Long.parseLong(mobile));
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //验证密码 已经过一次固定盐md5散列的密码(formPass)
        String dbPass = user.getPassword();
        String saltDB = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
        if (!calcPass.equals(dbPass)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        setCookie(response, user);
        return true;
    }

    private void setCookie(HttpServletResponse response, MiaoshaUser user) {
        //生成cookie 并添加给response对象
        String token = UUIDUtil.uuid();
        redisService.set(MiaoshaUserKey.token, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/"); //todo figure out what path is this
        response.addCookie(cookie);
    }

    private void updateCookie(HttpServletResponse response, String token) {
        /* *
         * @description: 生成一个cookie,token不变只更新了过期时间
         * @param: [response, token]
         * @return: void
         */
        redisService.updateExpire(MiaoshaUserKey.token, token);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
    
}
