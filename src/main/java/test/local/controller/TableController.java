package test.local.controller;

import cn.hutool.core.collection.CollUtil;
import com.ftc.basecommon.exception.exception.server.UpdateException;
import com.ftc.basecommon.util.MybatisPlusUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import test.local.entity.TableEntity;
import test.local.service.TableService;

import javax.validation.constraints.Min;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 冯铁城 [fengtiecheng@cyou-inc.com]
 * @since 2022-12-19
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/rest/test")
@Api("测试Controller")
public class TableController {

    private final TableService testService;

    @PostMapping
    @ApiOperation("测试POST接口")
    public void post(@Validated @RequestBody TableEntity testEntity) {
        testService.save(testEntity);
    }

    @GetMapping
    @ApiOperation("测试GET接口")
    public void get(@Min(value = 10, message = "最小值为10") @RequestParam Integer age) {
        MybatisPlusUtil.getAndCheckNotExist(1, testService);
        MybatisPlusUtil.getAndCheckExist(2, testService);
        MybatisPlusUtil.saveBatch(CollUtil.newArrayList(new TableEntity()), testService, 1);
        throw new UpdateException(age);
    }
}
