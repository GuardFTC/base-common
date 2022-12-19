package com.ftc.basecommon.exception.handler;

import cn.hutool.json.JSONUtil;
import com.ftc.basecommon.exception.handler.client.CustomClientExceptionHandler;
import com.ftc.basecommon.exception.handler.client.ParamExceptionHandler;
import com.ftc.basecommon.exception.handler.server.DataBaseExceptionHandler;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.HandlerExceptionResolver;
import test.local.controller.TableController;
import test.local.entity.TableEntity;
import test.local.service.TableService;
import util.AssertController;
import util.MockInitUtil;

import java.util.ArrayList;
import java.util.List;

class HandlerTest {

    /*----------------------------------测试预加载所需组件---------------------------------*/
    private MockMvc mockMvc;

    @InjectMocks
    private TableController testController;

    @Mock
    private TableService testService;

    /*----------------------------------每个单元测试执行之前执行---------------------------------*/
    @BeforeEach
    public void setUp() {

        //1.初始化mock
        MockitoAnnotations.openMocks(this);

        //2.增加异常处理器
        List<HandlerExceptionResolver> exceptionResolvers = new ArrayList<>();
        exceptionResolvers.add(MockInitUtil.getExceptionResolver(CustomClientExceptionHandler.class));
        exceptionResolvers.add(MockInitUtil.getExceptionResolver(ParamExceptionHandler.class));
        exceptionResolvers.add(MockInitUtil.getExceptionResolver(DataBaseExceptionHandler.class));

        //3.设置mvc组件
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(testController)
                .setHandlerExceptionResolvers(exceptionResolvers)
                .build();
    }

    /*----------------------------------测试方法----------------------------------------*/
    @Test
    @SneakyThrows(Exception.class)
    void BodyParamError() {

        //1.年龄字符串为空
        TableEntity testEntity = new TableEntity();
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("http://localhost:8080/api/v1/rest/test")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JSONUtil.toJsonStr(testEntity));

        //2.验证
        AssertController.checkErrorResponse(
                mockMvc.perform(request).andReturn(),
                HttpStatus.BAD_REQUEST.value(),
                "[\"年龄字符串不能为空\"]"
        );

        //3.年龄字符串小于3
        testEntity = new TableEntity();
        testEntity.setAgeStr("1");
        request = MockMvcRequestBuilders
                .post("http://localhost:8080/api/v1/rest/test")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JSONUtil.toJsonStr(testEntity));

        //4.验证
        AssertController.checkErrorResponse(
                mockMvc.perform(request).andReturn(),
                HttpStatus.BAD_REQUEST.value(),
                "[\"年龄字符串不能小于3\"]"
        );
    }

    @Test
    @SneakyThrows(Exception.class)
    void ClientException() {

        //1.NotFoundException
        Mockito.when(testService.getById(1)).thenReturn(null);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("http://localhost:8080/api/v1/rest/test")
                .param("age", String.valueOf(Integer.MAX_VALUE));
        AssertController.checkErrorResponse(
                mockMvc.perform(request).andReturn(),
                HttpStatus.NOT_FOUND.value(),
                "根据主键ID:[1]获取类型:[java.lang.Object]数据,未找到"
        );

        //2.NotAcceptException
        Mockito.when(testService.getById(1)).thenReturn(new TableEntity());
        Mockito.when(testService.getById(2)).thenReturn(new TableEntity());
        request = MockMvcRequestBuilders
                .get("http://localhost:8080/api/v1/rest/test")
                .param("age", String.valueOf(Integer.MAX_VALUE));
        AssertController.checkErrorResponse(
                mockMvc.perform(request).andReturn(),
                HttpStatus.NOT_ACCEPTABLE.value(),
                "接口流程因参数异常中断,异常原因:[根据主键ID:[2]获取类型:[test.local.entity.TableEntity]数据,已存在]"
        );
    }

    @Test
    @SneakyThrows(Exception.class)
    void ServerException() {

        //1.SaveException
        Mockito.when(testService.getById(1)).thenReturn(new TableEntity());
        Mockito.when(testService.getById(2)).thenReturn(null);
        Mockito.when(testService.saveBatch(Mockito.anyList())).thenReturn(false);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("http://localhost:8080/api/v1/rest/test")
                .param("age", String.valueOf(Integer.MAX_VALUE));
        AssertController.checkErrorResponse(
                mockMvc.perform(request).andReturn(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "数据:[[{}]]批量保存异常"
        );

        //2.UpdateException
        Mockito.when(testService.getById(1)).thenReturn(new TableEntity());
        Mockito.when(testService.getById(2)).thenReturn(null);
        Mockito.when(testService.saveBatch(Mockito.anyList())).thenReturn(true);
        request = MockMvcRequestBuilders
                .get("http://localhost:8080/api/v1/rest/test")
                .param("age", String.valueOf(Integer.MAX_VALUE));
        AssertController.checkErrorResponse(
                mockMvc.perform(request).andReturn(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "数据:[{}]更新异常"
        );
    }
}