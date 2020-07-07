package com.example.common.exception;

import com.example.common.lang.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value =Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req,Exception e){
        log.error("捕获到的异常",e);
        if(e instanceof HwException){

        }

        ModelAndView m=new ModelAndView();
        m.addObject("exception",e);
        m.addObject("message",e.getMessage());
        m.addObject("url:",req.getRequestURL());
        m.setViewName("error");
        return m;
    }
    @ExceptionHandler(value = HwException.class)
    @ResponseBody
    public Result jsonErrorHandler(HttpServletRequest req, HwException e){
        return Result.fail(e.getMessage(),"some error data");
    }
}
