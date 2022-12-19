package test.local.service.impl;

import test.local.entity.TableEntity;
import test.local.mapper.TableMapper;
import test.local.service.TableService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 冯铁城 [fengtiecheng@cyou-inc.com]
 * @since 2022-12-19
 */
@Service
public class TableServiceImpl extends ServiceImpl<TableMapper, TableEntity> implements TableService {

}
