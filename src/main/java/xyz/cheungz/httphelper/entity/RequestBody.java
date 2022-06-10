package xyz.cheungz.httphelper.entity;

import org.apache.commons.httpclient.Cookie;
import xyz.cheungz.httphelper.constant.HttpConstant;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Request包装类
 *
 * @Program: HttpClientHelper
 * @Author: Zhang Zhe
 * @Create: 2022-03-23 08:24
 * @Version: 1.0.0
 **/
public class RequestBody implements Serializable {

    /**
     * 默认的Header
     */
    private final Header DEFAULT_REQUEST_HEADER = new Header();


    {
        // 默认的请求头
        Map<String, String> HEADER = new HashMap<>();
        HEADER.put(HttpConstant.CONTENT_TYPE, HttpConstant.BODY);
        HEADER.put("User-Agent","Mozilla/5.0 (Windows NT 6.1; rv:2.0.1) Gecko/20100101 Firefox/4.0.1");
        HEADER.put("Accept","*/*");
        DEFAULT_REQUEST_HEADER.setHeaders(HEADER);
        DEFAULT_REQUEST_HEADER.setCookies(null);
    }


    private static final long serialVersionUID = 67160013866408466L;
    private String url;
    private String data;
    private String method;
    private Header header;

    public RequestBody(String url, String data, String method, Header header) {
        this.url = url;
        this.data = data;
        this.method = method;
        this.header = header;
    }

    public RequestBody(String url,String data,String contentType){
        this.url = url;
        this.data = data;
        this.header = DEFAULT_REQUEST_HEADER;
        header.getHeaders().put(HttpConstant.CONTENT_TYPE,contentType);
    }

    public RequestBody(String url, String data) {
        this.url = url;
        this.data = data;
        this.header = DEFAULT_REQUEST_HEADER;
    }

    public RequestBody(String url) {
        this.url = url;
        this.data = "";
        this.method = HttpConstant.GET;
        this.header = DEFAULT_REQUEST_HEADER;
    }

    public String getUrl() {
        return url;
    }

    public RequestBody setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getData() {
        return data;
    }

    public RequestBody setData(String data) {
        this.data = data;
        return this;
    }

    public String getMethod() {
        return method;
    }

    public RequestBody setMethod(String method) {
        this.method = method;
        return this;
    }

    public Header getHeader() {
        return header;
    }

    public RequestBody setHeader(Header header) {
        this.header = header;
        return this;
    }

    /**
     * 设置cookie
     * 首先检查cookies,如果未初始化则初始化长度为5,再设置cookie。
     * 如果数组已满，则扩容至旧数组的1.5倍(并非严格的1.5倍，若为偶数则为1.5倍，否则长度为旧数组长度1.5倍-1)
     * 然后再添加cookie
     *
     * @param key cookie键
     * @param value cookie值
     */
    public void setCookie(String key, String value){

        Cookie[] cookies = DEFAULT_REQUEST_HEADER.getCookies();
        if (cookies == null){
            cookies = new Cookie[5];
            DEFAULT_REQUEST_HEADER.setCookies(cookies);
        }
        if (setCookie(cookies,key,value)) {
            return;
        }
        // 如果cookies已满,则扩容并且复制旧数组到新数组
        Cookie[] newCookies = Arrays.copyOf(cookies, (cookies.length + cookies.length / 2));
        DEFAULT_REQUEST_HEADER.setCookies(newCookies);
        setCookie(newCookies,key,value);
    }

    /**
     * 设置cookie，成功返回true，否则返回false
     * @param cookies newCookies
     * @param key key
     * @param value value
     * @return boolean
     */
    private boolean setCookie(Cookie[] cookies, String key, String value){
        for (int i = 0; i < cookies.length; i++){
            Cookie cookie = cookies[i];
            if (cookie == null) {
                cookie = new Cookie();
                cookie.setName(key);
                cookie.setValue(value);
                cookies[i] = cookie;
                return true;
            }
        }
        return false;
    }
}
