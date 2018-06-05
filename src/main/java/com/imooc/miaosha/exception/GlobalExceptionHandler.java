package com.imooc.miaosha.exception;

import com.imooc.miaosha.controller.GoodsController;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ControllerAdvice //AOP
@ResponseBody //返回对象和controller一样
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class) //value 为需要拦截的异常
    public Result<String> exceptionHandler(HttpServletRequest request, Exception e){
        e.printStackTrace();
        if (e instanceof GlobalException) {
            //捕获MiaoshaUserService.login()发出的GlobalException
            GlobalException exception = (GlobalException) e;
            return Result.error(exception.getCodeMsg());
        }
        else if (e instanceof BindException) {
            //捕获validator发出的BindException
            BindException exception =  (BindException) e;
            List<ObjectError> errors = exception.getAllErrors();
            ObjectError error = errors.get(0);
            String msg = error.getDefaultMessage();
            return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
        }else{
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }

}
