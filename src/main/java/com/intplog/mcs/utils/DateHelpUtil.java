package com.intplog.mcs.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * 返回比当前系统日期早day天的日期
 * @author liaoliming
 * @Date 2019-06-19 20:13
 */
public class DateHelpUtil {

    public static Date getDate(int day){
        Date date = new Date();
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.DAY_OF_YEAR, day * -1);
        Date dt = rightNow.getTime();
        return dt;
    }
}
