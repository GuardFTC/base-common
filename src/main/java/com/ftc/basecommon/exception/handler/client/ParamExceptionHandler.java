package com.ftc.basecommon.exception.handler.client;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.log.StaticLog;
import com.ftc.basecommon.exception.template.log.client.ClientExceptionLogTemplate;
import com.ftc.basecommon.result.RestfulResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-12-14 17:44:02
 * @describe: 客户端参数异常捕捉器
 */
@RestControllerAdvice
public class ParamExceptionHandler {

    /**
     * 请求体参数异常处理类
     *
     * @param request   HTTP请求,用于获取请求URL
     * @param exception 请求体参数异常
     * @return 异常信息
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RestfulResult<Object> handle(HttpServletRequest request, MethodArgumentNotValidException exception) {

        //1.定义异常结果集
        List<String> errorMessages = CollUtil.newArrayList();

        //2.获取URL、请求方式以及异常信息
        if (!ObjectUtil.isNull(exception) && !exception.getBindingResult().getAllErrors().isEmpty()) {

            //3.获取日志参数
            String ip = ServletUtil.getClientIP(request);
            String method = request.getMethod();
            String url = request.getRequestURI();
            for (ObjectError error : exception.getBindingResult().getAllErrors()) {

                //4.存入结果集
                String errorMessage = error.getDefaultMessage();
                errorMessages.add(errorMessage);

                //5.打印日志
                StaticLog.warn(StrUtil.format(ClientExceptionLogTemplate.BAD_REQUEST, ip, method, url, errorMessage));
            }
        }

        //6.返回异常信息
        return RestfulResult.ClientException.badRequest(errorMessages);
    }

    /**
     * URL参数异常处理类
     *
     * @param request   HTTP请求,用于获取请求URL
     * @param exception URL参数异常
     * @return 异常信息
     */
    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public RestfulResult<Object> handle(HttpServletRequest request, ConstraintViolationException exception) {

        //1.获取URL、请求方式以及异常信息
        String ip = ServletUtil.getClientIP(request);
        String method = request.getMethod();
        String url = request.getRequestURI();
        String errorMessage = exception.getMessage();
        StaticLog.warn(StrUtil.format(ClientExceptionLogTemplate.BAD_REQUEST, ip, method, url, errorMessage));

        //2.返回异常信息
        return RestfulResult.ClientException.badRequest(errorMessage);
    }
}
