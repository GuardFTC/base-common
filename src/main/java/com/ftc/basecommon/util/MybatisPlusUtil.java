package com.ftc.basecommon.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ftc.basecommon.exception.server.DataExistException;
import com.ftc.basecommon.exception.server.DataNotExistException;
import com.ftc.basecommon.exception.server.SaveException;

import java.util.List;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-12-15 21:47:17
 * @describe: MybatisPlus扩展工具类
 */
public class MybatisPlusUtil {

    /**
     * 分批次批量保存数据
     *
     * @param dataList       数据集合
     * @param iService       接口Service
     * @param countThreshold 触发批量保存数量阈值
     * @param <T>            数据泛型类
     */
    public static <T> void saveBatch(List<T> dataList, IService<T> iService, int countThreshold) {

        //1.初始化计数器
        int count = 0;

        //2.定义暂存结果集
        List<T> saveList = CollUtil.newArrayList();

        //3.循环保存
        for (T data : dataList) {

            //4.存入暂存结果集,计数器++
            saveList.add(data);
            count++;

            //5.到达存储数量阈值开始储存
            if (countThreshold == saveList.size()) {
                if (!(iService.saveBatch(saveList))) {
                    throw new SaveException(data.getClass());
                }
                saveList.clear();
            } else if (count == dataList.size()) {
                if (!(iService.saveBatch(saveList))) {
                    throw new SaveException(data.getClass());
                }
                saveList.clear();
            }
        }
    }

    /**
     * 根据主键ID查询并校验对象是否不存在,如果不存在抛出异常
     *
     * @param id      主键ID
     * @param service IService接口
     * @param <T>     泛型
     * @return 根据主键ID获取的对象
     */
    public static <T> T getAndCheckNotExist(Integer id, IService<T> service) {

        //1.查询并校验样本
        T data = service.getById(id);

        //2.校验是否不存在
        if (ObjectUtil.isNull(data)) {
            throw new DataNotExistException(id, service.getClass());
        }

        //3.返回
        return data;
    }

    /**
     * 根据主键ID查询并校验对象是否存在,如果存在抛出异常
     *
     * @param id      主键ID
     * @param service IService接口
     * @param <T>     泛型
     */
    public static <T> void getAndCheckExist(Integer id, IService<T> service) {

        //1.查询
        T data = service.getById(id);

        //2.校验是否存在
        if (ObjectUtil.isNotNull(data)) {
            throw new DataExistException(id, data.getClass());
        }
    }
}
