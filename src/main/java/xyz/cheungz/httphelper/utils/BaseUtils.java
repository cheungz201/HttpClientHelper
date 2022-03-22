package xyz.cheungz.httphelper.utils;

import org.apache.http.HttpEntity;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;
import xyz.cheungz.httphelper.constant.HttpConstant;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * 基础工具类
 *
 * @Program: HttpClientHelper
 * @Author: Zhang Zhe
 * @Create: 2022-03-13 20:33
 * @Version: 1.0.0
 **/
public class BaseUtils {

    /**
     * 处理HttpEntity实体
     * @param entity
     * @return 处理结果
     * @throws IOException
     * @return 处理结果
     */
    public static String entityToString(HttpEntity entity) throws IOException {
        String result = null;
        if (entity != null) {
            long length = entity.getContentLength();
            if (length != -1 && length < 2048) {
                result = EntityUtils.toString(entity, "UTF-8");
            } else {
                InputStreamReader reader1 = new InputStreamReader(entity.getContent(), "UTF-8");
                CharArrayBuffer buffer = new CharArrayBuffer(2048);
                char[] tmp = new char[1024];
                int l;
                while ((l = reader1.read(tmp)) != -1) {
                    buffer.append(tmp, 0, l);
                }
                result = buffer.toString();
            }
        }
        return result;
    }

    /**
     * 处理输入流转为string
     * @param stream
     * @return 处理结果
     * @throws IOException
     */
    public static String inStreamToString(InputStream stream) throws IOException {
        StringBuffer buffer = new StringBuffer();
        byte[] bytes = new byte[1024];
        while (stream.read(bytes) != -1){
            buffer.append(new String(bytes,"UTF-8"));
        }
        return buffer.toString();
    }

    /**
     * 判断请求头中是否包含content-type信息
     * @param header
     * @return content-type
     */
    public static boolean includeContentType(Map<String,String> header){
        for (String key : header.keySet()){
            if (HttpConstant.CONTENT_TYPE.equalsIgnoreCase(key)){
                return true;
            }
        }
        return false;
    }

    /**
     * 设置cookie，成功返回true，失败返回false
     * @param cookies
     * @return boolean
     */
    public static boolean setCookies(Map<String, String> headers,Map<String, String> cookies){
        if (cookies == null){
            throw new NullPointerException("cookies is null !");
        }
        StringBuffer cookiesString = new StringBuffer();
        for (String key : cookies.keySet()){
            cookiesString.append(key+"="+cookies.get(key)+";");
        }
        headers.put(HttpConstant.COOKIE,cookiesString.toString());
        return true;
    }
}
