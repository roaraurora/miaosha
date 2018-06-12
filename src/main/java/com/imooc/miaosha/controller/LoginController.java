package com.imooc.miaosha.controller;

import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.service.MiaoshaUserService;
import com.imooc.miaosha.validator.IsMobile;
import com.imooc.miaosha.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class LoginController {

    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    private MiaoshaUserService miaoshaUserService;

    @Autowired
    public LoginController(MiaoshaUserService miaoshaUserService) {
        this.miaoshaUserService = miaoshaUserService;
    }


    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    @IsMobile
    public Result<Boolean> doLogin(HttpServletResponse response, @Valid LoginVo loginVo) {
        // 接收一个ajax提交的{mobile: *** ,password: ***}的json对象
        log.info(loginVo.toString());
        //登录失败会抛出GlobalException 进而被ExceptionHandler处理
        miaoshaUserService.login(response, loginVo);
        return Result.success(true);
    }

}
