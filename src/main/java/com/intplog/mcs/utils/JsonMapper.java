package com.intplog.mcs.utils;

import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * json转换工具类
 * @author liaoliming
 * @Date 2019-06-22 12:49
 */

@Slf4j
public class JsonMapper {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        //config
        objectMapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS,false);
        objectMapper.setFilters(new SimpleFilterProvider().setFailOnUnknownId(false));
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY);
    }

    public static  <T> String obj2String(T src){
        if(src == null){

            return null;
        }
        try{
            return src instanceof String ? (String)src : objectMapper.writeValueAsString(src);
        }catch (Exception e){
            log.warn("parse object to string exception,error:{}",e);
            return null;
        }

    }

    public static <T> T string2obj(String src, TypeReference<T> typeReference){

        if(src==null && typeReference==null){
            return null;
        }
        try {
            return (T) (typeReference.getType().equals(String.class) ? src : objectMapper.readValue(src,typeReference));
        }catch (Exception e){
            log.warn("parse String to Object exception, String:{}, TypeReference<T>:{},error:{}",src,typeReference.getType());
            return null;
        }
    }

    public static String writeValueAsString(Object o) {
        if (StringUtils.isEmpty(o)) {
            return null;
        } else {
            String str = null;

            try {
                str = objectMapper.writeValueAsString(o);
            } catch (JsonGenerationException var3) {
                var3.printStackTrace();
            } catch (JsonMappingException var4) {
                var4.printStackTrace();
            } catch (IOException var5) {
                var5.printStackTrace();
            }

            return str;
        }
    }
}
