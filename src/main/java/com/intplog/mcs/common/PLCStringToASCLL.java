package com.intplog.mcs.common;

public class PLCStringToASCLL {
    /**
     * 字符串转ASCLL
     * @param string
     */
    public static String ConvertToASCII(String string) {
        StringBuilder sb = new StringBuilder();
        char[] ch = string.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            sb.append(Integer.valueOf(ch[i]).intValue()).append("  ");
        }
        return sb.toString();
    }
}
