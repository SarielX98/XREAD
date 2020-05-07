package com.sarielx.study.xr.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    //捕获全局异常，处理所有不可知的异常
    @ExceptionHandler(value=Exception.class)
    //@ResponseBody
    public Object handleException(Exception e, HttpServletRequest request) {
        LOG.error("msg:{},url:{}", e.getMessage(), request.getRequestURL());

        Map<String, Object> map = new HashMap<>();
        map.put("code", 100);
        map.put("msg", e.getMessage());
        map.put("url", request.getRequestURL());
        return map;
    }

    //自定义异常
    //需要添加thymeleaf依赖
    //路径：src/main/resources/templates/error.html
    @ExceptionHandler(value=MyException.class)
    public Object handleMyException(MyException e, HttpServletRequest request) {
        //返回Json数据，由前端进行界面跳转
        Map<String, Object> map = new HashMap<>();
        map.put("code", e.getCode());
        map.put("msg", e.getMsg());
        map.put("url", request.getRequestURL());
        return map;

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultInfo<?> validationErrorHandler(MethodArgumentNotValidException ex) {
        // 同样是获取BindingResult对象，然后获取其中的错误信息
        // 如果前面开启了fail_fast，事实上这里只会有一个信息
        //如果没有，则可能又多个
        List<String> errorInformation = ex.getBindingResult().getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
        return new ResultInfo<>(400, errorInformation.toString(), null);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResultInfo<?> validationErrorHandler(ConstraintViolationException ex) {
        List<String> errorInformation = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
        return new ResultInfo<>(400, errorInformation.toString(), null);
    }
}