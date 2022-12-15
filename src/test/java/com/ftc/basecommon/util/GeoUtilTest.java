package com.ftc.basecommon.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import org.junit.jupiter.api.Test;

import java.awt.geom.Point2D;
import java.util.List;

class GeoUtilTest {

    @Test
    void getDistanceByNm() {

        //1.指定原点经纬度(北京经纬度)
        double sourceLat = 39.26;
        double sourceLng = 115.25;

        //2.指定目标点经纬度(大同经纬度)
        double targetLat = 39.03;
        double targetLng = 112.34;

        //3.计算距离
        double distance = GeoUtil.getDistanceByNm(sourceLat, sourceLng, targetLat, targetLng);

        //4.校验
        Assert.isTrue(136.361772 == distance);
    }

    @Test
    void getDistance() {

        //1.指定原点经纬度(北京经纬度)
        double sourceLat = 39.26;
        double sourceLng = 115.25;

        //2.指定目标点经纬度(大同经纬度)
        double targetLat = 39.03;
        double targetLng = 112.34;

        //3.计算距离
        double distance = GeoUtil.getDistance(sourceLat, sourceLng, targetLat, targetLng);

        //4.校验
        Assert.isTrue(252521.80000000002 == distance);
    }

    @Test
    void inShape() {

        //1.创建原点
        Point2D.Double point = new Point2D.Double(116.20, 39.56);

        //2.创建包含原点三角形
        Point2D.Double point1 = new Point2D.Double(92.13, 32.31);
        Point2D.Double point2 = new Point2D.Double(125.03, 50.49);
        Point2D.Double point3 = new Point2D.Double(119.18, 20.45);

        //3.封装点集合
        List<Point2D.Double> points = CollUtil.newArrayList(point1, point2, point3, point1);

        //4.判定
        boolean inShape = GeoUtil.inShape(point, points);
        Assert.isTrue(inShape);

        //5.创建不包含原点三角形
        point1 = new Point2D.Double(105.49, 38.08);
        point2 = new Point2D.Double(96.37, 42.45);
        point3 = new Point2D.Double(80.24, 31.29);

        //6.封装点集合
        points = CollUtil.newArrayList(point1, point2, point3, point1);

        //7.判定
        inShape = GeoUtil.inShape(point, points);
        Assert.isFalse(inShape);
    }
}