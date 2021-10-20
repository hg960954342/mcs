package com.intplog.mcs;

import com.intplog.mcs.common.PlcGetByte;

import java.util.Arrays;

public class Test {

//    public static String begin = "2";
//    public static String end = "3";
    public static char begin = (char) 02;
    public static char end = (char) 03;
    public static int serialNum = 0;

    public static void main(String[] args) {
//        String str="m1";
//        String s = PlcGetByte.convertStringToHex(str);
//        System.out.println(s);
//
//        byte[] bytes = PlcGetByte.convertStringToByte(str);
//        System.out.println(Arrays.toString(bytes));
        dpsCode();
    }

    private static void dpsCode(){
        String msg = "PP5060000m133$1001999999";
        int length = msg.length();
        /*
        (02)000024 P P 5 0 6 0 0 0 0 m 1 3 3 $ 2 4 0 1 9 9 9 9 9 9 (03)
        PP5060000m133$1001999999
         */
        String data = begin + String.format("%04d", serialNum) + String.format("%03d", length) + msg + end;
        System.out.println(data);

        byte[] bytes = PlcGetByte.convertStringToByte(data);
        //[ 2 48 48 48 48 48 50 52 80 80 53 48 54 48 48 48 48 109 49 51 51 36 49 48 48 49 57 57 57 57 57 57  3]
        //[2 30 30 30 30 30 32 34 50 50 35 30 36 30 30 30 30 6d 31 33 33 24 31 30 30 31 39 39 39 39 39 39 3]
        System.out.println(bytes.length+Arrays.toString(bytes));
        String s = PlcGetByte.convertStringToHex(data);
        System.out.println(s);
    }

}
