package test.local.mapper;

import test.local.entity.TableEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 冯铁城 [fengtiecheng@cyou-inc.com]
 * @since 2022-12-19
 */
@Mapper
public interface TableMapper extends BaseMapper<TableEntity> {

}
