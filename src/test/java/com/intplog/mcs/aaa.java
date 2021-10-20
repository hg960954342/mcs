package com.intplog.mcs;

/**
 * @author liaoliming
 * @Date 2020/2/19 12:42 下午
 */
public class aaa {
    public static void main(String[] args) {
        String data = "-   123.5+     0.0";
        String value=analyData(data);
        double v = Double.parseDouble(value);
        System.out.println(v);
    }

    private static String analyData(String data){
        String value=null;
        if (data.length()==7){
            value=data;
        }
        else if (data.startsWith("+")||data.startsWith("-")){
            value = data.substring(0, 1) + data.substring(1, 9).replace(" ", "");
        }
        return value;
    }
}


