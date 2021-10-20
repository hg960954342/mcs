package com.intplog.mcs.utils;

import com.google.common.base.Splitter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class StringUtil {

    private static int taskSN = 1;

    public static List<Integer> splitToListInt(String str) {
        List<String> strList = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(str);
        return strList.stream().map(strItem -> Integer.parseInt(strItem)).collect(Collectors.toList());
    }

    /**
     * 生成UUID
     *
     * @return
     */
    public static String getUUID32() {
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        return uuid;
    }

    /**
     * 时间转换为字符串
     *
     * @param date
     * @return
     */
    public static String getDate(Date date) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * 生成随机不重复的9位数字：日时分秒+3位流水号
     *
     * @return
     */
    public static synchronized String getTaskCode() {
        SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
        String dateStr = sdf.format(new Date());
        taskSN = taskSN > 998 ? 1 : ++taskSN;
        //注意,去掉头部0,防止转int出错
        return (dateStr + String.format("%03d", taskSN)).replaceAll("^(0+)", "");
    }

    public static boolean differDate(Date dateTime, int differ) {
        return System.currentTimeMillis() - dateTime.getTime() > differ;
    }
}
