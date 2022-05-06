package xyz.cheungz.httphelper.entity;

import org.apache.commons.httpclient.Cookie;

import java.util.Map;

/**
 * 请求头
 *
 * @Program: HttpClientHelper
 * @Author: Zhang Zhe
 * @Create: 2022-03-23 08:36
 * @Version: 1.0.0
 **/
public class Header {

    private Cookie[] cookies;
    private Map<String, String> headers;


    public Cookie[] getCookies() {
        return cookies;
    }

    public Header setCookies(Cookie ...cookies) {
        this.cookies = cookies;
        return this;
    }

    public Header(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Header setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public Header(Cookie[] cookies, Map<String, String> headers) {
        this.cookies = cookies;
        this.headers = headers;
    }

    public Header() {
    }
}
