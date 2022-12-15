package com.ftc.basecommon.util;

import java.awt.geom.Point2D;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-12-15 17:33:15
 * @describe: JavaGeo相关工具类
 */
public class GeoUtil {

    /**
     * 地球半径
     **/
    private static final double EARTH_RADIUS = 6371e3;

    /**
     * 格式化模板,小数点后6位
     **/
    private static final DecimalFormat DF = new DecimalFormat("0.000000");

    /**
     * 根据指定经纬度坐标、指定距离、偏移角度，计算另外一点的经纬度
     *
     * @param startLat 起始点纬度
     * @param startLng 起始点经度
     * @param distance 距离/m
     * @param angle    偏移角度，从正北顺时针方向开始计算
     * @return 目标经纬度[经度, 经纬]
     */
    public static String[] calLocationByDistanceAndLocationAndDirection(double startLat, double startLng, double distance, double angle) {

        //1.定义结果集
        String[] result = new String[2];

        //2.将距离转换成经度的计算公式
        double formulaValue = distance / EARTH_RADIUS;

        //3.转换为radian，否则结果会不正确
        angle = Math.toRadians(angle);
        startLng = Math.toRadians(startLng);
        startLat = Math.toRadians(startLat);
        double lat = Math.asin(Math.sin(startLat) * Math.cos(formulaValue) + Math.cos(startLat) * Math.sin(formulaValue) * Math.cos(angle));
        double lon = startLng + Math.atan2(Math.sin(angle) * Math.sin(formulaValue) * Math.cos(startLat), Math.cos(formulaValue) - Math.sin(startLat) * Math.sin(lat));

        //4.转为正常的10进制经纬度
        lon = Math.toDegrees(lon);
        lat = Math.toDegrees(lat);
        result[0] = DF.format(lon);
        result[1] = DF.format(lat);

        //5.返回
        return result;
    }

    /**
     * 根据指定经纬度坐标、半径，计算出能包含住圆的最小矩形经纬度
     *
     * @param lat    纬度
     * @param lon    经度
     * @param radius 半径/m
     * @return 范围内最大经纬度
     */
    public static double[] getAround(double lat, double lon, double radius) {

        //1.获取半径纬度
        double degree = (24901 * 1609) / 360.0;
        double dpmLat = 1 / degree;
        double radiusLat = dpmLat * radius;

        //2.获取最小纬度、最大纬度
        double minLat = lat - radiusLat;
        double maxLat = lat + radiusLat;

        //3.获取半径经度
        double mpdLng = degree * Math.cos(lat * (Math.PI / 180));
        double dpmLng = 1 / mpdLng;
        double radiusLng = dpmLng * radius;

        //4.获取最小经度、最大经度
        double minLng = lon - radiusLng;
        double maxLng = lon + radiusLng;

        //5.返回
        return new double[]{minLat, maxLat, minLng, maxLng};
    }

    /**
     * 根据最大最小经纬度-获取随机经纬度
     *
     * @param minLat 最小纬度
     * @param maxLat 最大纬度
     * @param minLng 最小经度
     * @param maxLng 最大经度
     * @return 随机经纬度
     */
    public static double[] randomLonLat(double minLat, double maxLat, double minLng, double maxLng) {

        //1.获取经度
        BigDecimal db = new BigDecimal(Math.random() * (maxLng - minLng) + minLng);
        double lng = db.setScale(6, RoundingMode.HALF_UP).doubleValue();

        //2.获取纬度
        db = new BigDecimal(Math.random() * (maxLat - minLat) + minLat);
        double lat = db.setScale(6, RoundingMode.HALF_UP).doubleValue();

        //3.返回
        return new double[]{lat, lng};
    }

    /**
     * 通过经纬度获取距离(单位:海里)
     *
     * @param sourceLat 原点纬度
     * @param sourceLng 原点经度
     * @param targetLat 目标点纬度
     * @param targetLng 目标点经度
     * @return 两点之间距离 单位海里
     */
    public static double getDistanceByNm(double sourceLat, double sourceLng, double targetLat, double targetLng) {

        //1.获取两点间距离（单位米）
        double distance = getDistance(sourceLat, sourceLng, targetLat, targetLng);

        //2.转换为海里
        double var = 0.00054;
        distance = distance * var;

        //3.返回
        return distance;
    }

    /**
     * 通过经纬度获取距离(单位:米)
     *
     * @param sourceLat 原点纬度
     * @param sourceLng 原点经度
     * @param targetLat 目标点纬度
     * @param targetLng 目标点经度
     * @return 两点之间距离 单位米
     */
    public static double getDistance(double sourceLat, double sourceLng, double targetLat, double targetLng) {

        //1.平面纬度转球面纬度
        double radLat1 = sourceLat * Math.PI / 180.0;
        double radLat2 = targetLat * Math.PI / 180.0;

        //2.计算纬度以及经度差值
        double latDifference = radLat1 - radLat2;
        double lngDifference = sourceLng * Math.PI / 180.0 - targetLng * Math.PI / 180.0;

        //3.计算距离
        double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(latDifference / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(lngDifference / 2), 2)));
        distance = distance * 6378.137;
        distance = Math.round(distance * 10000d) / 10000d;
        distance = distance * 1000;

        //4.返回
        return distance;
    }

    /**
     * 通过射线法，判断目标点是否在多边形内
     * <p>
     * 射线法介绍:
     * 以目标点为原点，水平向两侧射出直线
     * 如果任意一侧的直线，与多边形的交点为奇数，代表目标点在多边形内
     * 如果任意一侧的直线，与多边形的交点为偶数，代表目标点在多边形外
     *
     * @param point  目标点
     * @param points 多边形的顶点,必须满足闭合条件,顺时针或逆时针皆可.例如说如果为三角形,points内应为4个点
     * @return 点在多边形内返回true, 否则返回false
     */
    public static boolean inShape(Point2D.Double point, List<Point2D.Double> points) {

        //1.获取多边形顶点数量
        int pointCount = points.size();

        //2.初始化交点数量、是否在多边形内、精确度
        boolean boundOrVertex = true;
        int intersectCount = 0;
        double precision = 2e-10;

        //3.定义起止点
        Point2D.Double p1, p2;

        //4.开始循环判定
        p1 = points.get(0);
        for (int i = 1; i <= pointCount; ++i) {

            if (point.equals(p1)) {
                return boundOrVertex;
            }

            p2 = points.get(i % pointCount);
            if (point.x < Math.min(p1.x, p2.x) || point.x > Math.max(p1.x, p2.x)) {
                p1 = p2;
                continue;
            }

            if (point.x > Math.min(p1.x, p2.x) && point.x < Math.max(p1.x, p2.x)) {
                if (point.y <= Math.max(p1.y, p2.y)) {
                    if (p1.x == p2.x && point.y >= Math.min(p1.y, p2.y)) {
                        return boundOrVertex;
                    }

                    if (p1.y == p2.y) {
                        if (p1.y == point.y) {
                            return boundOrVertex;
                        } else {
                            ++intersectCount;
                        }
                    } else {
                        double xinters = (point.x - p1.x) * (p2.y - p1.y) / (p2.x - p1.x) + p1.y;
                        if (Math.abs(point.y - xinters) < precision) {
                            return boundOrVertex;
                        }

                        if (point.y < xinters) {
                            ++intersectCount;
                        }
                    }
                }
            } else {
                if (point.x == p2.x && point.y <= p2.y) {
                    Point2D.Double p3 = points.get((i + 1) % pointCount);
                    if (point.x >= Math.min(p1.x, p3.x) && point.x <= Math.max(p1.x, p3.x)) {
                        ++intersectCount;
                    } else {
                        intersectCount += 2;
                    }
                }
            }
            p1 = p2;
        }

        //5.返回
        return intersectCount % 2 != 0;
    }
}
