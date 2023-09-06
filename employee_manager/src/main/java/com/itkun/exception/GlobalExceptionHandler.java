package com.itkun.exception;

import com.itkun.common.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/08/30/0:07
 * @Description:
 */
@ControllerAdvice(basePackages = "com.itkun.controller")
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //统一异常处理 @ExceptionHandler，主要用于Exception
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(HttpServletRequest request,Exception e){
        log.error("异常信息",e);
        return R.error("系统异常");
    }

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public R customError(HttpServletRequest request,CustomException e){
        return R.error(e.getMsg());
    }

}
















