package com.intplog.mcs.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Json转换工具类
 *
 * @author JiangZhongXing
 * @create 2019-01-12 10:59 AM
 */
public class JsonConvertUtils {
    /**
     * mapper对象
     */
    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * Json转换成对象
     *
     * @param jsonStr
     * @param classType
     * @param <T>
     * @return
     */
    public static <T> T strToObj(String jsonStr, Class<T> classType) {
        try {
            return mapper.readValue(jsonStr, classType);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 对象转换成Json
     *
     * @param obj
     * @return
     */
    public static String objToStr(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
