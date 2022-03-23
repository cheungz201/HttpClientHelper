package xyz.cheungz.httphelper.entity;

import java.util.Map;

/**
 * detail
 *
 * @Program: HttpClientHelper
 * @Author: Zhang Zhe
 * @Create: 2022-03-23 08:36
 * @Version: 1.0.0
 **/
public class Header {

    private Cookie cookies;
    private Map<String, String> headers;

    public Header(Cookie cookies, Map<String, String> headers) {
        this.cookies = cookies;
        this.headers = headers;
    }

    public Header() {
    }

    public Cookie getCookies() {
        return cookies;
    }

    public Header setCookies(Cookie cookies) {
        this.cookies = cookies;
        return this;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Header setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    @Override
    public String toString() {
        return "Header{" +
                "cookies=" + cookies +
                ", headers=" + headers +
                '}';
    }
}
