package xyz.cheungz.httphelper.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Program: HttpClientHelper
 * @Author: Zhang Zhe
 * @Create: 2022-03-13 12:41
 * @Version: 1.0.0
 * @Description: 序列化工具，完成对象和string之间的转换
 **/
public class SerializationUtil {


    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 序列化,将obj转成符合json格式的string
     * @param t：需要序列化的对象
     * @throws JsonProcessingException
     * @return 返回一个符合json格式的string
     */
    public static <T> String obj2String(T t) throws JsonProcessingException {
        return t instanceof String ? (String) t:objectMapper.writeValueAsString(t);
    }

    /**
     * 反序列化,将符合json格式的string转成obj
     * @param str：需要反序列化的字符串
     * @param tClass:指定返回对象类型
     * @throws JsonProcessingException
     * @return 返回越一个对象
     */
    public static <T> T string2Obj(String str,Class<T> tClass) throws JsonProcessingException {
        return objectMapper.readValue(str,tClass);
    }

}
