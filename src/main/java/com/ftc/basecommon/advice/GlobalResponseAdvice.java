package com.ftc.basecommon.advice;

import cn.hutool.core.util.ObjectUtil;
import com.ftc.basecommon.result.RestfulResult;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-12-05 19:56:35
 * @describe: 全局响应处理器
 */
@RestControllerAdvice(basePackages = {"com.ftc.basecommon"})
public class GlobalResponseAdvice implements ResponseBodyAdvice<RestfulResult> {

    /**
     * 判断是否要执行beforeBodyWrite方法，true为执行，false不执行
     *
     * @param returnType 返回的类型
     * @param aClass     我也不知道这个参数咋用
     * @return true-跳转到beforeBodyWrite/false-直接返回
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    /**
     * 会拦截controller层的返回，在响应写入响应体之前执行的方法
     *
     * @param data               响应数据
     * @param returnType         返回的类型
     * @param mediaType          Content/Type
     * @param aClass             我也不知道这个参数咋用
     * @param serverHttpRequest  HTTP请求
     * @param serverHttpResponse HTTP响应
     * @return 处理过后的响应数据
     */
    @Override
    public RestfulResult beforeBodyWrite(RestfulResult data, MethodParameter returnType, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

        //1.判定
        if (ObjectUtil.isNull(data)) {
            return data;
        }

        //2.设置Http响应码
        serverHttpResponse.setStatusCode(HttpStatus.valueOf(data.getCode()));

        //3.返回
        return data;
    }
}
