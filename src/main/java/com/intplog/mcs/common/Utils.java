package com.intplog.mcs.common;

import java.util.Date;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

public class Utils {

    /**
     * 获取当前时间 - 300毫秒 的差值
     *
     * @param startDate
     */
    public static long getTimeDifference(Date startDate) {
        Date endDate = new Date();
        long different = endDate.getTime() - startDate.getTime();
        long value = 500 - different;
        return value > 0 ? value : 10;
    }

    public static boolean isNumber(String str) {
        Pattern pattern = compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断字符串是不是double型
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        //?:0或1个, *:0或多个, +:1或多个
        Boolean strResult = str.matches("-?[0-9]+.?[0-9]*");
        return strResult;

    }
}
