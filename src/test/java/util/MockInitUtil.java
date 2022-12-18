package util;

import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

/**
 * @author 冯铁城 [fengtiecheng@cyou-inc.com]
 * @date 2022-01-11 18:06:32
 * @describe: Mock组件初始化相关工具类
 */
public class MockInitUtil {

    /**
     * 获取异常处理器注入格式
     *
     * @param exceptionHandlerClass 异常处理器类
     * @return 异常处理器注入格式
     */
    public static ExceptionHandlerExceptionResolver getExceptionResolver(Class exceptionHandlerClass) {

        //1.获取类名
        String className = exceptionHandlerClass.getSimpleName();

        //2.设置appContext
        StaticApplicationContext appContext = new StaticApplicationContext();
        appContext.registerBeanDefinition(className, new RootBeanDefinition(exceptionHandlerClass, (String) null, null));

        //3.创建异常处理器注入格式
        ExceptionHandlerExceptionResolver resolver = new ExceptionHandlerExceptionResolver();

        //4.设置常处理器注入格式并返回
        resolver.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        resolver.setApplicationContext(appContext);
        resolver.afterPropertiesSet();
        return resolver;
    }
}