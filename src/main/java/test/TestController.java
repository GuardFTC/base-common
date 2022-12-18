package test;

import cn.hutool.core.collection.CollUtil;
import com.ftc.basecommon.exception.exception.server.UpdateException;
import com.ftc.basecommon.util.MybatisPlusUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-12-15 10:16:32
 * @describe: 测试Controller
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/rest/test")
@Api("测试Controller")
public class TestController {

    private final TestService testService;

    @PostMapping
    @ApiOperation("测试POST接口")
    public void post(@Validated @RequestBody TestEntity testEntity) {
        testService.save(testEntity);
    }

    @GetMapping
    @ApiOperation("测试GET接口")
    public void get(@Min(value = 10, message = "最小值为10") @RequestParam Integer age) {
        MybatisPlusUtil.getAndCheckNotExist(1, testService);
        MybatisPlusUtil.getAndCheckExist(2, testService);
        MybatisPlusUtil.saveBatch(CollUtil.newArrayList(new TestEntity()), testService, 1);
        throw new UpdateException(age);
    }
}
