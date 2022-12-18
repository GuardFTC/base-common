package com.ftc.basecommon.exception.handler.client;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.log.StaticLog;
import com.ftc.basecommon.exception.exception.client.NotAcceptException;
import com.ftc.basecommon.exception.exception.client.NotFoundException;
import com.ftc.basecommon.exception.template.log.client.ClientExceptionLogTemplate;
import com.ftc.basecommon.result.RestfulResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-12-14 17:44:02
 * @describe: 自定义客户端参数异常捕捉器
 */
@RestControllerAdvice
public class CustomClientExceptionHandler {

    /**
     * 数据不接受异常处理类
     *
     * @param exception 数据不接受异常
     * @return 异常信息
     */
    @ResponseBody
    @ExceptionHandler(NotAcceptException.class)
    public RestfulResult<Object> handle(HttpServletRequest request, NotAcceptException exception) {

        //1.获取URL、请求方式以及异常信息
        String ip = ServletUtil.getClientIP(request);
        String method = request.getMethod();
        String url = request.getRequestURI();
        String errorMessage = exception.getMessage();
        StaticLog.warn(StrUtil.format(ClientExceptionLogTemplate.NOT_ACCEPT, ip, method, url, errorMessage));

        //2.返回异常信息
        return RestfulResult.ClientException.notAccept(errorMessage);
    }

    /**
     * 数据未找到异常处理类
     *
     * @param exception 数据未找到异常
     * @return 异常信息
     */
    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    public RestfulResult<Object> handle(HttpServletRequest request, NotFoundException exception) {

        //1.获取URL、请求方式以及异常信息
        String ip = ServletUtil.getClientIP(request);
        String method = request.getMethod();
        String url = request.getRequestURI();
        String errorMessage = exception.getMessage();
        StaticLog.error(StrUtil.format(ClientExceptionLogTemplate.NOT_FOUND, ip, method, url, errorMessage));

        //2.返回异常信息
        return RestfulResult.ClientException.notFound(errorMessage);
    }
}
