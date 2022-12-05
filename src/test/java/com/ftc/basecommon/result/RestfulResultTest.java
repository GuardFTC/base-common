package com.ftc.basecommon.result;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ftc.basecommon.enums.RestfulResultEnum;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class RestfulResultTest {

    /**
     * 模拟异常信息
     */
    private final static String MOCK_EXCEPTION_MESSAGE = "mock-error";

    @Test
    void testSuccess() {

        //1.测试success
        RestfulResult<JSONObject> success = RestfulResult.success(HttpStatus.OK.value(), new JSONObject());
        Assert.isTrue(HttpStatus.OK.value() == success.getCode());
        Assert.isTrue(RestfulResultEnum.SUCCESS_MESSAGE.getMessage().equals(success.getMessage()));
        Assert.isTrue(new JSONObject().toString().equals(JSONUtil.toJsonStr(success.getData())));

        //2.测试addData
        success = RestfulResult.Success.addData(new JSONObject());
        Assert.isTrue(HttpStatus.CREATED.value() == success.getCode());
        Assert.isTrue(RestfulResultEnum.SUCCESS_MESSAGE.getMessage().equals(success.getMessage()));
        Assert.isTrue(new JSONObject().toString().equals(JSONUtil.toJsonStr(success.getData())));

        //3.测试getOrUpdateData
        success = RestfulResult.Success.getOrUpdateData(new JSONObject());
        Assert.isTrue(HttpStatus.OK.value() == success.getCode());
        Assert.isTrue(RestfulResultEnum.SUCCESS_MESSAGE.getMessage().equals(success.getMessage()));
        Assert.isTrue(new JSONObject().toString().equals(JSONUtil.toJsonStr(success.getData())));

        //4.测试removeData
        RestfulResult<Void> removeSuccess = RestfulResult.Success.removeData();
        Assert.isTrue(HttpStatus.NO_CONTENT.value() == removeSuccess.getCode());
        Assert.isTrue(RestfulResultEnum.SUCCESS_MESSAGE.getMessage().equals(removeSuccess.getMessage()));
        Assert.isNull(removeSuccess.getData());
    }

    @Test
    void testFail() {

        //1.测试fail
        RestfulResult<Object> fail = RestfulResult.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), MOCK_EXCEPTION_MESSAGE);
        Assert.isTrue(HttpStatus.INTERNAL_SERVER_ERROR.value() == fail.getCode());
        Assert.isTrue(StrUtil.format(RestfulResultEnum.ERROR_MESSAGE.getMessage(), MOCK_EXCEPTION_MESSAGE).equals(fail.getMessage()));
        Assert.isTrue(new JSONObject().toString().equals(JSONUtil.toJsonStr(fail.getData())));

        //2.测试badRequest
        fail = RestfulResult.ClientException.badRequest(MOCK_EXCEPTION_MESSAGE);
        Assert.isTrue(HttpStatus.BAD_REQUEST.value() == fail.getCode());
        Assert.isTrue(StrUtil.format(RestfulResultEnum.ERROR_MESSAGE.getMessage(), MOCK_EXCEPTION_MESSAGE).equals(fail.getMessage()));
        Assert.isTrue(new JSONObject().toString().equals(JSONUtil.toJsonStr(fail.getData())));

        //3.测试unauthorized
        fail = RestfulResult.ClientException.unauthorized(MOCK_EXCEPTION_MESSAGE);
        Assert.isTrue(HttpStatus.UNAUTHORIZED.value() == fail.getCode());
        Assert.isTrue(StrUtil.format(RestfulResultEnum.ERROR_MESSAGE.getMessage(), MOCK_EXCEPTION_MESSAGE).equals(fail.getMessage()));
        Assert.isTrue(new JSONObject().toString().equals(JSONUtil.toJsonStr(fail.getData())));

        //4.测试forbidden
        fail = RestfulResult.ClientException.forbidden(MOCK_EXCEPTION_MESSAGE);
        Assert.isTrue(HttpStatus.FORBIDDEN.value() == fail.getCode());
        Assert.isTrue(StrUtil.format(RestfulResultEnum.ERROR_MESSAGE.getMessage(), MOCK_EXCEPTION_MESSAGE).equals(fail.getMessage()));
        Assert.isTrue(new JSONObject().toString().equals(JSONUtil.toJsonStr(fail.getData())));

        //5.测试notFound
        fail = RestfulResult.ClientException.notFound(MOCK_EXCEPTION_MESSAGE);
        Assert.isTrue(HttpStatus.NOT_FOUND.value() == fail.getCode());
        Assert.isTrue(StrUtil.format(RestfulResultEnum.ERROR_MESSAGE.getMessage(), MOCK_EXCEPTION_MESSAGE).equals(fail.getMessage()));
        Assert.isTrue(new JSONObject().toString().equals(JSONUtil.toJsonStr(fail.getData())));

        //6.测试notAccept
        fail = RestfulResult.ClientException.notAccept(MOCK_EXCEPTION_MESSAGE);
        Assert.isTrue(HttpStatus.NOT_ACCEPTABLE.value() == fail.getCode());
        Assert.isTrue(StrUtil.format(RestfulResultEnum.ERROR_MESSAGE.getMessage(), MOCK_EXCEPTION_MESSAGE).equals(fail.getMessage()));
        Assert.isTrue(new JSONObject().toString().equals(JSONUtil.toJsonStr(fail.getData())));

        //7.测试internalError
        fail = RestfulResult.ServerException.internalError(MOCK_EXCEPTION_MESSAGE);
        Assert.isTrue(HttpStatus.INTERNAL_SERVER_ERROR.value() == fail.getCode());
        Assert.isTrue(StrUtil.format(RestfulResultEnum.ERROR_MESSAGE.getMessage(), MOCK_EXCEPTION_MESSAGE).equals(fail.getMessage()));
        Assert.isTrue(new JSONObject().toString().equals(JSONUtil.toJsonStr(fail.getData())));
    }
}