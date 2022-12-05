package com.ftc.basecommon.advice;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Servlet;
import java.io.IOException;
import java.util.TimeZone;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-12-05 20:02:57
 * @describe: 全局请求参数trim操作处理器
 */
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass({Servlet.class, DispatcherServlet.class, WebMvcConfigurer.class})
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
public class GlobalParamTrimAdvice {

    /**
     * 对URL参数进行trim操作
     */
    @ControllerAdvice
    public static class ControllerStringParamTrimConfig {
        @InitBinder
        public void initBinder(WebDataBinder binder) {
            binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        }
    }

    /**
     * 对Body参数进行trim操作
     *
     * @return 参数->对象映射生成器
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return jacksonObjectMapperBuilder -> {
            jacksonObjectMapperBuilder.timeZone(TimeZone.getDefault());
            jacksonObjectMapperBuilder.deserializerByType(String.class, new StdScalarDeserializer<String>(String.class) {
                @Override
                public String deserialize(JsonParser jsonParser, DeserializationContext ctx) throws IOException {
                    return StrUtil.trim(jsonParser.getValueAsString());
                }
            });
        };
    }
}
