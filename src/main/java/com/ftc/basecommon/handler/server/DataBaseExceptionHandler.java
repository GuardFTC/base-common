package com.ftc.basecommon.handler.server;

import cn.hutool.log.StaticLog;
import com.ftc.basecommon.exception.server.DataExistException;
import com.ftc.basecommon.exception.server.DataNotExistException;
import com.ftc.basecommon.exception.server.SaveException;
import com.ftc.basecommon.exception.server.UpdateException;
import com.ftc.basecommon.result.RestfulResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-12-14 17:44:02
 * @describe: 数据库异常捕捉器
 */
@RestControllerAdvice
public class DataBaseExceptionHandler {

    /**
     * 数据已存在异常处理类
     *
     * @param exception 数据已存在异常
     * @return 异常信息
     */
    @ResponseBody
    @ExceptionHandler(DataExistException.class)
    public RestfulResult<Object> handle(DataExistException exception) {

        //1.打印日志
        StaticLog.error(exception.getMessage());

        //2.返回异常信息
        return RestfulResult.ClientException.notAccept(exception.getMessage());
    }

    /**
     * 数据不存在异常处理类
     *
     * @param exception 数据不存在异常
     * @return 异常信息
     */
    @ResponseBody
    @ExceptionHandler(DataNotExistException.class)
    public RestfulResult<Object> handle(DataNotExistException exception) {

        //1.打印日志
        StaticLog.error(exception.getMessage());

        //2.返回异常信息
        return RestfulResult.ClientException.notFound(exception.getMessage());
    }

    /**
     * 数据保存异常处理类
     *
     * @param exception 数据保存异常
     * @return 异常信息
     */
    @ResponseBody
    @ExceptionHandler(SaveException.class)
    public RestfulResult<Object> handle(SaveException exception) {

        //1.打印日志
        StaticLog.error(exception.getMessage());

        //2.返回异常信息
        return RestfulResult.ServerException.internalError(exception.getMessage());
    }

    /**
     * 数据更新异常处理类
     *
     * @param exception 数据更新异常
     * @return 异常信息
     */
    @ResponseBody
    @ExceptionHandler(UpdateException.class)
    public RestfulResult<Object> handle(UpdateException exception) {

        //1.打印日志
        StaticLog.error(exception.getMessage());

        //2.返回异常信息
        return RestfulResult.ServerException.internalError(exception.getMessage());
    }
}
