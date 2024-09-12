package com.subinuer.weblog.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;  // SpringBoot 内置JSON工具Jackson
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtil {

    private static final ObjectMapper INSTANCE = new ObjectMapper();

    //  toJsonString 方法，用于将传入的对象打印成 JSON 字符串
    public static String toJsonString(Object obj){
        try{
            return INSTANCE.writeValueAsString(obj);
        }catch (JsonProcessingException e){
            return obj.toString();
        }
    }
}
