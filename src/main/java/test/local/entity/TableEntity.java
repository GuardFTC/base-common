package test.local.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author 冯铁城 [fengtiecheng@cyou-inc.com]
 * @since 2022-12-19
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("test_table")
@ApiModel(value = "TableEntity对象", description = "TableEntity对象")
public class TableEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("年龄")
    @NotBlank(message = "年龄字符串不能为空")
    @Min(value = 3, message = "年龄字符串不能小于3")
    private String ageStr;
}
