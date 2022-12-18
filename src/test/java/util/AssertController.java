package util;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ftc.basecommon.enums.RestfulResultEnum;
import lombok.SneakyThrows;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * @author 冯铁城 [fengtiecheng@cyou-inc.com]
 * @date 2022-01-17 15:03:33
 * @describe: Controller层校验工具类
 */
public class AssertController {

    /**
     * 验证成功请求基础响应值
     *
     * @param mvcResult 请求响应
     * @param code      响应码
     * @return 验证非空的请求响应实体类
     */
    @SneakyThrows(UnsupportedEncodingException.class)
    public static JSONObject checkOkResponse(MvcResult mvcResult, Integer code) {

        //1.获取响应数据
        JSONObject response = new JSONObject(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8), true);

        //2.验证响应数据非空
        Assert.isTrue(ObjectUtil.isNotNull(response));

        //3.验证响应码
        Integer responseCode = response.getInt("code");
        Assert.isTrue(ObjectUtil.isNotNull(responseCode));
        Assert.isTrue(code.equals(responseCode));

        //4.验证响应信息
        String responseMsg = response.getStr("message");
        Assert.isTrue(StrUtil.isNotBlank(responseMsg));
        Assert.isTrue(RestfulResultEnum.SUCCESS_MESSAGE.getMessage().equals(responseMsg));

        //5.验证响应数据非空
        JSONObject data = response.getJSONObject("data");
        Assert.isTrue(ObjectUtil.isNotNull(data));

        //6.返回数据
        return data;
    }

    /**
     * 验证失败请求基础响应值
     *
     * @param mvcResult    请求响应
     * @param code         期望响应码
     * @param errorMessage 期望异常信息
     * @return 响应数据
     */
    @SneakyThrows(UnsupportedEncodingException.class)
    public static void checkErrorResponse(MvcResult mvcResult, Integer code, String errorMessage) {

        //1.获取响应数据
        JSONObject response = JSONUtil.parseObj(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));

        //2.验证响应数据非空
        Assert.isTrue(ObjectUtil.isNotNull(response));

        //3.验证响应码
        Integer responseCode = response.getInt("code");
        Assert.isTrue(ObjectUtil.isNotNull(responseCode));
        Assert.isTrue(code.equals(responseCode));

        //4.验证响应信息
        errorMessage = StrUtil.format(RestfulResultEnum.ERROR_MESSAGE.getMessage(), JSONUtil.toJsonStr(errorMessage));
        String responseMsg = response.getStr("message");
        Assert.isTrue(StrUtil.isNotBlank(responseMsg));
        Assert.isTrue(errorMessage.equals(responseMsg));

        //5.验证响应数据
        JSONObject data = response.getJSONObject("data");
        Assert.isTrue(ObjectUtil.isNotNull(data));
        Assert.isTrue(data.isEmpty());
    }
}