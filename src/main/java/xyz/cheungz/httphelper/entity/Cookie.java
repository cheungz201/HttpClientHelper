package xyz.cheungz.httphelper.entity;

import java.util.Map;

/**
 * Cookie实体类
 *
 * @Program: HttpClientHelper
 * @Author: Zhang Zhe
 * @Create: 2022-03-23 08:32
 * @Version: 1.0.0
 **/
public class Cookie {

    private Map<String,String> cookies;

    public Cookie() { }

    public Cookie(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public Cookie setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
        return this;
    }

    @Override
    public String toString() {
        return "Cookie{" +
                "cookies=" + cookies +
                '}';
    }
}
