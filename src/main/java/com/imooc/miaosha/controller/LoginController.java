package com.imooc.miaosha.controller;

import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.service.MiaoshaUserService;
import com.imooc.miaosha.util.ValidatorUtil;
import com.imooc.miaosha.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

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
    public Result<Boolean> doLogin(LoginVo loginVo) {
        /* *
         * 接收一个ajax提交的{mobile: *** ,password: ***}的json对象
         * @param [loginVo]
         * @return com.imooc.miaosha.result.Result<java.lang.Boolean>
         */
        log.info(loginVo.toString());
        //参数校验
        String passInput = loginVo.getPassword();
        String mobile = loginVo.getMobile();
        if (StringUtils.isEmpty(passInput)) {
            //传来的密码为空 CodeMsg(500211,"登录密码不能为空"))
            return Result.error(CodeMsg.PASSWORD_EMPTY);
        }
        if (StringUtils.isEmpty(mobile)) {
            //传来的电话号码为空 CodeMsg(500212,"手机号码不能为空")
            return Result.error(CodeMsg.MOBILE_EMPTY);
        }
        if (!ValidatorUtil.isMobile(mobile)) {
            //传来的号码不符合要求(以1开头接10位数字的正则) CodeMsg(500213,"手机号码格式错误")
            return Result.error(CodeMsg.MOBILE_ERROR);
        }
        //其他情况交给login()处理 (手机号不存在/密码错误)
        CodeMsg cm = miaoshaUserService.login(loginVo);
        if (cm.getCode() == 0) {
            return Result.success(true);
        } else {
            return Result.error(cm);
        }
    }

}
