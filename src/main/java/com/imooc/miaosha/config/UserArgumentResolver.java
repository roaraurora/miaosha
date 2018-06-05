package com.imooc.miaosha.config;

import com.imooc.miaosha.controller.GoodsController;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.service.MiaoshaUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    private static Logger logger = LoggerFactory.getLogger(GoodsController.class);

    private final MiaoshaUserService userService;

    @Autowired
    public UserArgumentResolver(MiaoshaUserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        //需要匹配的参数类型 MiaoshaUser.class
        Class<?> clazz = methodParameter.getParameterType();
        return clazz == MiaoshaUser.class;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
              ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest,
              WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        //http请求头作为参数直接传递的token 兼容某些移动设备
        String paramToken = request.getParameter(MiaoshaUserService.COOKIE_NAME_TOKEN);
        //cookie中的token
        String cookieToken = getCookieValue(request, MiaoshaUserService.COOKIE_NAME_TOKEN);
        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
            return null;
        }
        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
        return userService.getByToken(response,token);
    }

    private String getCookieValue(HttpServletRequest request, String cookieName) {
        //从request的cookie列表里获取对应的cookie
        Cookie[] cookies = request.getCookies();
        for (Cookie c:cookies) {
            if (c.getName().equals(cookieName)){
                return c.getValue();
            }
        }
        return null;
    }
}
