package com.ftc.basecommon.result;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: 冯铁城
 * @date: 2022-12-15 20:51:43
 * @describe: 简单分页对象
 */
@Data
public class SimplePageInfo<T> {

    @ApiModelProperty("页数")
    private long pageNum;

    @ApiModelProperty("页码")
    private long pageSize;

    @ApiModelProperty("总数")
    private long total;

    @ApiModelProperty("分页数据")
    private List<T> data;

    /**
     * 初始化简单分页实体类,用于数据库查询分页,响应数据为非PO数据
     *
     * @param pageInfo pageInfo实体类
     * @param data     响应数据集合
     * @param <V>      pageInfo对象类型
     * @param <T>      响应数据对象类型
     * @return 简单分页实体类
     */
    public static <V, T> SimplePageInfo<T> init(PageInfo<V> pageInfo, List<T> data) {

        //1.创建对象
        SimplePageInfo<T> simplePageInfo = new SimplePageInfo<>();

        //2.设置基础参数
        simplePageInfo.setPageNum(pageInfo.getPageNum());
        simplePageInfo.setPageSize(pageInfo.getPageSize());
        simplePageInfo.setTotal(pageInfo.getTotal());

        //3.设置data
        simplePageInfo.setData(data);

        //4.返回
        return simplePageInfo;
    }

    /**
     * 初始化简单分页实体类,用于数据库查询分页
     *
     * @param pageInfo pageInfo实体类
     * @param <T>      分页数据泛型
     * @return 简单分页实体类
     */
    public static <T> SimplePageInfo<T> init(PageInfo<T> pageInfo) {

        //1.创建对象
        SimplePageInfo<T> simplePageInfo = new SimplePageInfo<>();

        //2.设置基础参数
        simplePageInfo.setPageNum(pageInfo.getPageNum());
        simplePageInfo.setPageSize(pageInfo.getPageSize());
        simplePageInfo.setTotal(pageInfo.getTotal());

        //3.设置data
        simplePageInfo.setData(pageInfo.getList());

        //4.返回
        return simplePageInfo;
    }

    /**
     * 初始化简单分页实体类,用于数据库查询分页,响应数据为非PO数据
     *
     * @param pageInfo pageInfo实体类
     * @param data     响应数据集合
     * @param <V>      pageInfo对象类型
     * @param <T>      响应数据对象类型
     * @return 简单分页实体类
     */
    public static <V, T> SimplePageInfo<T> init(Page<V> pageInfo, List<T> data) {

        //1.创建对象
        SimplePageInfo<T> simplePageInfo = new SimplePageInfo<>();

        //2.设置基础参数
        simplePageInfo.setPageNum(pageInfo.getCurrent());
        simplePageInfo.setPageSize(pageInfo.getSize());
        simplePageInfo.setTotal(pageInfo.getTotal());

        //3.设置data
        simplePageInfo.setData(data);

        //4.返回
        return simplePageInfo;
    }

    /**
     * 初始化简单分页实体类,用于数据库查询分页
     *
     * @param pageInfo pageInfo实体类
     * @param <T>      分页数据泛型
     * @return 简单分页实体类
     */
    public static <T> SimplePageInfo<T> init(Page<T> pageInfo) {

        //1.创建对象
        SimplePageInfo<T> simplePageInfo = new SimplePageInfo<>();

        //2.设置基础参数
        simplePageInfo.setPageNum(pageInfo.getCurrent());
        simplePageInfo.setPageSize(pageInfo.getSize());
        simplePageInfo.setTotal(pageInfo.getTotal());

        //3.设置data
        simplePageInfo.setData(pageInfo.getRecords());

        //4.返回
        return simplePageInfo;
    }

    /**
     * 初始化简单分页实体类,用于非数据库查询分页
     *
     * @param data     分页数据
     * @param pageNum  页数
     * @param pageSize 页码
     * @param <T>      分页数据泛型
     * @return 简单分页实体类
     */
    public static <T> SimplePageInfo<T> init(List<T> data, int pageNum, int pageSize) {

        //1.创建对象
        SimplePageInfo<T> simplePageInfo = new SimplePageInfo<>();

        //2.设置基础参数
        simplePageInfo.setPageNum(pageNum);
        simplePageInfo.setPageSize(pageSize);
        simplePageInfo.setTotal((long) data.size());

        //3.设置data
        int start = (pageNum - 1) * pageSize;
        int end = pageNum * pageSize;
        simplePageInfo.setData(CollUtil.sub(data, start, end));

        //4.返回
        return simplePageInfo;
    }
}