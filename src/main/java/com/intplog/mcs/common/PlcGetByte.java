package com.intplog.mcs.common;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @program: mcs_j
 * @description
 * @author: tianlei
 * @create: 2020-03-10 13:26
 **/
public class PlcGetByte {

    //region int转byte[]

    /**
     * 将int数值转换为占四个字节的byte数组，本方法适用于(低位在前，高位在后)的顺序。 和bytesToInt（）配套使用
     *
     * @param value 要转换的int值
     * @return byte数组
     */
    public static byte[] intToBytes4LowEndian(int value) {
        byte[] src = new byte[4];
        src[3] = (byte) ((value >> 24) & 0xFF);
        src[2] = (byte) ((value >> 16) & 0xFF);
        src[1] = (byte) ((value >> 8) & 0xFF);
        src[0] = (byte) (value & 0xFF);
        return src;
    }

    /**
     * 将int数值转换为占四个字节的byte数组，本方法适用于(低位在后，高位在前)的顺序。 和bytesToInt（）配套使用
     *
     * @param value 要转换的int值
     * @return byte数组
     */
    public static byte[] intToBytes4HighEndian(int value) {
        byte[] src = new byte[4];
        src[0] = (byte) ((value >> 24) & 0xFF);
        src[1] = (byte) ((value >> 16) & 0xFF);
        src[2] = (byte) ((value >> 8) & 0xFF);
        src[3] = (byte) (value & 0xFF);
        return src;
    }

    /**
     * 将int数值转换为占四个字节的byte数组，本方法适用于(高位在前，低位在后)的顺序。  和bytesToInt2（）配套使用
     */
    public static byte[] intToBytes2HighEndian(int value) {
        byte[] src = new byte[2];
        src[0] = (byte) ((value >> 8) & 0xFF);
        src[1] = (byte) (value & 0xFF);
        return src;
    }
    //endregion

    //region byte[]转int

    /**
     * byte数组中取 一个byte int数值，本方法适用于(低位在前，高位在后)的顺序，和和intToBytes（）配套使用
     *
     * @param src    byte数组
     * @param offset 从数组的第offset位开始
     * @return int数值
     */
    public static int bytes1ToIntLowEndian(byte[] src, int offset) {
        int value;
        value = (int) ((src[offset] & 0xFF));
        return value;
    }

    /**
     * byte数组中取2个byte转int数值，本方法适用于(低位在前，高位在后)的顺序
     *
     * @param src    byte数组
     * @param offset 从数组的第offset位开始
     * @return
     */
    public static int bytes2ToIntLowEndian(byte[] src, int offset) {
        int value;
        value = (int) ((src[offset] & 0xFF)
                | ((src[offset + 1] & 0xFF) << 8));
        return value;
    }

    /**
     * byte数组中取4个byte转int数值，本方法适用于(低位在前，高位在后)的顺序
     *
     * @param src
     * @param offset
     * @return
     */
    public static int bytes4ToIntLowEndian(byte[] src, int offset) {
        int value;
        value = (int) (((src[offset] & 0xFF))
                | ((src[offset + 1] & 0xFF) << 8)
                | ((src[offset + 2] & 0xFF) << 16)
                | (src[offset + 3] & 0xFF) << 24);
        return value;
    }

    /**
     * byte数组中取int数值，本方法适用于(低位在后，高位在前)的顺序。和intToBytes2（）配套使用
     */
    public static int bytes1ToIntHighEndian(byte[] src, int offset) {
        int value;
        value = (int) (((src[offset] & 0xFF) << 24));
        return value;
    }


    /**
     * byte数组中取Dint数值，本方法适用于(低位在后，高位在前)的顺序。和intToBytes2（）配套使用
     */
    public static int bytesToInt32Hight(byte[] src, int offset) {
        int value;
        value = (int) (((src[offset] & 0xFF) << 24)
                | ((src[offset + 1] & 0xFF) << 16)
                | ((src[offset + 2] & 0xFF) << 8)
                | (src[offset + 3] & 0xFF));
        return value;
    }

    /**
     * byte数组中取Double数值，本方法适用于(低位在后，高位在前)的顺序。和intToBytes2（）配套使用
     */
    public static Double bytesToDouble32Hight(byte[] src, int offset) {
        Double value;
        value = Double.valueOf(((src[offset] & 0xFF) << 24)
                | ((src[offset + 1] & 0xFF) << 16)
                | ((src[offset + 2] & 0xFF) << 8)
                | (src[offset + 3] & 0xFF));
        return value;
    }

    /**
     * byte数组中取int数值，本方法适用于(低位在后，高位在前)的顺序。和intToBytes2（）配套使用
     */
    public static int bytes2ToIntHighEndian(byte[] src, int offset) {
        int value;
        if (src.length < src.length + 2) {
            value = (int) (((src[offset] & 0xFF) << 8)
                    | ((src[offset + 1] & 0xFF)));
        } else {
            value = 0;
        }
        return value;
    }

    /**
     * byte数组中取int数值，本方法适用于(低位在后，高位在前)的顺序。和intToBytes2（）配套使用
     */
    public static int bytes4ToIntHighEndian(byte[] src, int offset) {
        int value;
        value = (int) (((src[offset] & 0xFF) << 24)
                | ((src[offset + 1] & 0xFF) << 16)
                | ((src[offset + 2] & 0xFF) << 8)
                | (src[offset + 3] & 0xFF));
        return value;
    }
    //endregion

    /**
     * 从一个byte[]数组中截取一部分
     *
     * @param src
     * @param begin
     * @param count
     * @return
     */
    public static byte[] subBytes(byte[] src, int begin, int count) {
        byte[] bs = new byte[count];
        for (int i = begin; i < begin + count; i++) bs[i - begin] = src[i];
        return bs;
    }

    /**
     * 数组反转
     *
     * @param scr
     * @return
     */
    public static byte[] arryByte(byte[] scr) {
        int count = scr.length;
        byte[] bs = new byte[count];
        for (int i = count - 1; i >= 0; i--) {
            bs[count - 1 - i] = scr[i];
        }
        return bs;

    }


    /**
     * 数组转float
     *
     * @param bytes
     * @return
     */
    public static float byte2float(byte[] bytes) {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer();
        byteBuf.writeBytes(bytes);
        float result = byteBuf.readFloat();
        return result;
    }

    /**
     * byte数组转String
     *
     * @param scr   byte数组
     * @param begin 截取起始位置
     * @param count 截取数组长度
     * @return
     */
    public static String byteToString(byte[] scr, int begin, int count) {
        byte[] bytes = subBytes(scr, begin, count);
        try {

            int length = 0;
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] == 0) {
                    length = i;
                    break;
                }
            }

            return new String(bytes, 0, length, "UTF-8");
        } catch (Exception ex) {
            return "";
        }
    }

    /**
     * 16进制的字符串表示转成字节数组
     *
     * @param hexString 16进制格式的字符串
     * @return 转换后的字节数组
     **/
    public static byte[] toByteArray(String hexString) {
        if (StringUtils.isEmpty(hexString))
            throw new IllegalArgumentException("this hexString must not be empty");
        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {//因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }

    /**
     * 字节数组转成16进制表示格式的字符串
     *
     * @param byteArray 需要转换的字节数组
     * @return 16进制表示格式的字符串
     **/
    public static String toHexString(byte[] byteArray) {
        if (byteArray == null || byteArray.length < 1)
            throw new IllegalArgumentException("this byteArray must not be null or empty");

        final StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < byteArray.length; i++) {
            if ((byteArray[i] & 0xff) < 0x10)//0~F前面不零
                hexString.append("0");
            hexString.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        return hexString.toString().toLowerCase();
    }

    /**
     * byte数组转String去除后面的0
     *
     * @param buffer
     * @return
     */
    public static String byteToStr(byte[] buffer) {
        try {
            int length = 0;
            for (int i = 0; i < buffer.length; ++i) {
                if (buffer[i] == 0) {
                    length = i;
                    break;
                }
            }
            return new String(buffer, 0, length, "UTF-8");
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * byte数组转成字符串
     *
     * @param bytes  ascii
     * @param offset 起始位置
     * @param length 长度
     * @return ?和空默认为NoRead
     */
    public static String asciiToString(byte[] bytes, int offset, int length) {

        String data = "";
        //ASCII转字符串
        try {
            data = new String(bytes, offset, length, "ascii");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //?为NoRead
        if (data.contains("?")) {
            data = "NoRead";
        }
        //去空
        else {
            data = data.trim();
            if (org.apache.commons.lang3.StringUtils.isBlank(data)) {
                data = "NoRead";
            }
        }
        return data;
    }

    /**
     * ASCII码字符串转换为16进制字符串
     *
     * @param str
     * @return java.lang.String
     * @date 2020/9/16 10:37
     * @author szh
     */
    public static String convertStringToHex(String str) {

        char[] chars = str.toCharArray();

        StringBuffer hex = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            hex.append(Integer.toHexString((int) chars[i]));
        }

        return hex.toString();
    }

    /**
     * ASCII码字符串转换为byte数组
     *
     * @param str
     * @return byte[]
     * @date 2020/9/16 11:30
     * @author szh
     */
    public static byte[] convertStringToByte(String str) {

        char[] chars = str.toCharArray();
        byte[] bytes = new byte[str.length()];
        for (int i = 0; i < chars.length; i++) {
            bytes[i] = (byte) chars[i];
        }

        return bytes;
    }

    /**
     * 16进制转换为ASCII
     *
     * @param hex
     * @return java.lang.String
     * @date 2020/9/16 10:37
     * @author szh
     */
    public static String convertHexToString(String hex) {

        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();

        //49204c6f7665204a617661 split into two characters 49, 20, 4c...
        for (int i = 0; i < hex.length() - 1; i += 2) {

            //grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            //convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            //convert the decimal to character
            sb.append((char) decimal);

            temp.append(decimal);
        }

        return sb.toString();
    }

    /**
     * int数组转成byte数组
     *
     * @param list
     * @return
     */
    public static byte[] listIntToBytes2HighEndian(List<Integer> list) {
        ByteBuf byteBuf = Unpooled.buffer(list.size() * 2);
        for (int signal : list) {
            byteBuf.writeShort(signal);
        }
        return byteBuf.array();
    }
}
