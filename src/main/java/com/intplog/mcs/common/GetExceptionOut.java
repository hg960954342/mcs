package com.intplog.mcs.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class GetExceptionOut {

    /**
     * 获取异常具体信息
     * @param ex
     * @return
     */
    public static String getExceptionAllinformation(Exception ex) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(out);
        ex.printStackTrace(printStream);
        String ret = new String(out.toByteArray());
        printStream.close();
        try {
           out.close();
        } catch (Exception e) {
        }
        return ret;
    }
}
