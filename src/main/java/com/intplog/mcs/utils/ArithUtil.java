package com.intplog.mcs.utils;

import java.math.BigDecimal;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2020/9/8 14:42
 */
public class ArithUtil {
    private static final int DEF_DIV_SCALE=10;

    private ArithUtil(){}
    //精确的加法算法
    public static double add(double d1,double d2){
        BigDecimal b1=new BigDecimal(Double.toString(d1));
        BigDecimal b2=new BigDecimal(Double.toString(d2));
        return b1.add(b2).doubleValue();

    }
    //精确的减法算法
    public static double sub(double d1,double d2){
        BigDecimal b1=new BigDecimal(Double.toString(d1));
        BigDecimal b2=new BigDecimal(Double.toString(d2));
        return b1.subtract(b2).doubleValue();

    }
    //精确的乘法算法
    public static double mul(double d1,double d2){
        BigDecimal b1=new BigDecimal(Double.toString(d1));
        BigDecimal b2=new BigDecimal(Double.toString(d2));
        return b1.multiply(b2).doubleValue();

    }
    //相对精确的除法运算，当发生除不尽的情况时，精确到小数点以后10位
    public static double div(double d1,double d2){

        return div(d1,d2,DEF_DIV_SCALE);

    }
    //相对精确的除法运算，当发生除不尽的情况时，精确到小数点以后指定精度(scale)，再往后的数字四舍五入
    public static double div(double d1,double d2,int scale){
        if(scale<0){
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1=new BigDecimal(Double.toString(d1));
        BigDecimal b2=new BigDecimal(Double.toString(d2));
        return b1.divide(b2,scale, BigDecimal.ROUND_HALF_UP).doubleValue();

    }
}
