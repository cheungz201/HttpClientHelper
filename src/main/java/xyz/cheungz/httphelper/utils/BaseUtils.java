package xyz.cheungz.httphelper.utils;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;
import xyz.cheungz.httphelper.constant.HttpConstant;
import xyz.cheungz.httphelper.entity.Cookie;
import xyz.cheungz.httphelper.entity.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
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
    public static boolean setCookies(xyz.cheungz.httphelper.entity.Header header, Cookie cookies){
        if (cookies == null){
            throw new NullPointerException("cookies is null !");
        }
        header.setCookies(cookies);
        return true;
    }

    /**
     * 通过CloseableHttpResponse将所需信息包装成ResponseBody
     * @param response
     * @return
     */
    public static ResponseBody getResponse(CloseableHttpResponse response){
        if (response == null){
            throw new NullPointerException("response is null !");
        }
        try {
            ResponseBody responseBody = new ResponseBody();
            String result = entityToString(response.getEntity());
            Header[] allHeaders = response.getAllHeaders();
            // Headers
            Map<String,String> headers = new HashMap<>();

            // Cookies
            Map<String,String> cookies = new HashMap<>();

            for (Header header: allHeaders){
                if (HttpConstant.COOKIE.equalsIgnoreCase(header.getName())) {
                    cookies.put(header.getName(),header.getValue());
                }else {
                    headers.put(header.getName(),header.getValue());
                }
            }
            responseBody.setResult(result);
            responseBody.setHeader(new xyz.cheungz.httphelper.entity.Header(new Cookie(cookies),headers));
            return responseBody;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 通过HttpMethod将所需信息包装成ResponseBody
     * @param request
     * @return
     */
    public static ResponseBody getResponse(HttpMethod request){
        if (request == null){
            throw new NullPointerException("HttpMethod is null !");
        }
        try {
            ResponseBody responseBody = new ResponseBody();
            String result = inStreamToString(request.getResponseBodyAsStream());
            // Headers
            Map<String,String> header = new HashMap<>();

            // Cookies
            Map<String,String> cookies = new HashMap<>();

            org.apache.commons.httpclient.Header[] responseHeaders = request.getResponseHeaders();
            for (org.apache.commons.httpclient.Header responseHeader : responseHeaders){
                if (HttpConstant.COOKIE.equalsIgnoreCase(responseHeader.getValue())) {
                    cookies.put(responseHeader.getName(),responseHeader.getValue());
                }else {
                    header.put(responseHeader.getValue(),responseHeader.getValue());
                }
            }
            responseBody.setResult(result);
            responseBody.setHeader(new xyz.cheungz.httphelper.entity.Header(new Cookie(cookies),header));
            return responseBody;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 得到cookies的结构体
     *
     * @param cookies cookies
     * @return cookies to string."k=v;"
     */
    public static String getCookieBody(Map<String,String> cookies){
        StringBuffer buffer = new StringBuffer();
        for (String key : cookies.keySet()){
            buffer.append(key+"="+cookies.get(key)+";");
        }
        return buffer.toString();
    }
}
