package xyz.cheungz.httphelper.utils;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;
import xyz.cheungz.httphelper.constant.HttpConstant;
import xyz.cheungz.httphelper.entity.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
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
     * @param entity 响应实体
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
    public static boolean setCookies(xyz.cheungz.httphelper.entity.Header header, Cookie[] cookies){
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
        try {
            ResponseBody responseBody = new ResponseBody();
            ArrayList<Cookie> cookieList = new ArrayList();
            String result = entityToString(response.getEntity());
            Header[] allHeaders = response.getAllHeaders();
            // get Headers
            Map<String,String> headers = new HashMap<>();
            // 包装header和cookie
            for (Header header: allHeaders){
                // 包装cookie
                if ("Set-Cookie".equalsIgnoreCase(header.getName())){
                    HeaderElement element = header.getElements()[0];
                    Cookie cookie = new Cookie();
                    NameValuePair[] parameters = element.getParameters();
                    for (NameValuePair pair : parameters){
                        if ("Max-Age".equalsIgnoreCase(pair.getName())){
                            cookie.setExpiryDate(new Date(System.currentTimeMillis()+Integer.parseInt(pair.getValue())));
                        }
                    }
                    cookie.setName(element.getName());
                    cookie.setValue(element.getValue());
                    cookieList.add(cookie);
                }
                // 包装header
                headers.put(header.getName(),header.getValue());
            }
            responseBody.setResult(result);
            responseBody.setHeader(new xyz.cheungz.httphelper.entity.Header(headers));
            responseBody.setResponseCode(response.getStatusLine().getStatusCode());
            responseBody.getHeader().setCookies(cookieList.toArray(new Cookie[cookieList.size()]));
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
    public static ResponseBody getResponse(HttpClient client, HttpMethod request){
        try {
            ResponseBody responseBody = new ResponseBody();
            String result = inStreamToString(request.getResponseBodyAsStream());

            // add cookies
            Cookie[] cookies = client.getState().getCookies();
            // add headers
            Map<String,String> headers = new HashMap<>();
            org.apache.commons.httpclient.Header[] responseHeaders = request.getResponseHeaders();
            for (org.apache.commons.httpclient.Header responseHeader : responseHeaders){
                    headers.put(responseHeader.getName(),responseHeader.getValue());
            }

            responseBody.setResult(result);
            responseBody.setHeader(new xyz.cheungz.httphelper.entity.Header(cookies,headers));
            responseBody.setResponseCode(request.getStatusCode());
            return responseBody;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 得到cookies的结构体
     *
     * @param header 请求头
     * @return cookies to string."k=v;"
     */
    public static String getCookieBody(xyz.cheungz.httphelper.entity.Header header){
        if (header.getCookies() == null){
            return null;
        }
        Cookie[] cookies = header.getCookies();
        StringBuffer buffer = new StringBuffer();
        for (Cookie cookie : cookies){
            if (cookie == null){
                return buffer.toString();
            }
            buffer.append(cookie.getName()+"="+cookie.getValue()+";");
        }
        return buffer.toString();
    }

}
