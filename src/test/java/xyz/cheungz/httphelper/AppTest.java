package xyz.cheungz.httphelper;

import org.apache.commons.httpclient.Cookie;
import org.junit.Test;
import xyz.cheungz.httphelper.constant.HttpConstant;
import xyz.cheungz.httphelper.core.PoolHttpClient;
import xyz.cheungz.httphelper.core.multithreading.MultiHttpClient;
import xyz.cheungz.httphelper.entity.Header;
import xyz.cheungz.httphelper.entity.RequestBody;
import xyz.cheungz.httphelper.entity.ResponseBody;
import xyz.cheungz.httphelper.exception.HttpException;

import java.util.HashMap;
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class AppTest {

    static String url_json = "http://localhost:8080/json";
    static String url_form = "http://localhost:8080/str";
    static String url_get  = "http://localhost:8080/get?username=zhangsan";
    static String url_get_cookie  = "http://localhost:8080/setcookie";
    static String url_post_cookie  = "http://localhost:8080/setcookiepost";
    static Map<String,String> map = new HashMap();
    static String json = "{\n" +
            "\t\"username\": \"zhangzhe\"\n" +
            "}";

    static{
        map.put("username","zhangsan");
    }

    static PoolHttpClient poolHttpClient = new PoolHttpClient();
    static MultiHttpClient multiHttpClient = new MultiHttpClient();


    @Test
    public void poolGetTest() throws HttpException {
        ResponseBody responseBody = poolHttpClient.sendGet(new RequestBody(url_get));
        System.out.println(responseBody.getResult());
    }

    @Test
    public void poolPostTest() throws HttpException {
        ResponseBody responseBody = poolHttpClient.sendPost(new RequestBody(url_json,json));
        System.out.println(responseBody.getResult());
    }

    @Test
    public void poolPostTestByXXX() throws HttpException {
        ResponseBody responseBody = poolHttpClient.sendPost(new RequestBody(url_form, json, HttpConstant.FORM));
        System.out.println(responseBody.getResult());
    }

    @Test
    public void multiGetTest() throws HttpException {
        ResponseBody responseBody = multiHttpClient.sendGet(new RequestBody(url_get));
        System.out.println(responseBody.getResult());
    }

    @Test
    public void multiPostTest() throws HttpException {
        ResponseBody responseBody = multiHttpClient.sendPost(new RequestBody(url_json, json));
        System.out.println(responseBody.getResult());
    }

    @Test
    public void multiPostTestByXXX() throws HttpException {
        ResponseBody responseBody = multiHttpClient.sendPost(new RequestBody(url_form, json, HttpConstant.FORM));
        System.out.println(responseBody.getResult());
    }

    @Test
    public void multiCookieTest() throws HttpException {
        RequestBody requestBody = new RequestBody(url_get);
        requestBody.setCookie("multiget","test!!");
        multiHttpClient.sendGet(requestBody);
    }

    @Test
    public void poolCookieTest() throws HttpException {
        RequestBody requestBody = new RequestBody(url_get);
        requestBody.setCookie("poolget","test!!");
        poolHttpClient.sendGet(requestBody);
    }

    @Test
    public void postPoolHttpClient() throws HttpException {
        RequestBody requestBody = new RequestBody(url_json,json);
        requestBody.setCookie("poolpost","test!!");
        multiHttpClient.sendPost(requestBody);
    }

    @Test
    public void postMultiHttpClient() throws HttpException {
        RequestBody requestBody = new RequestBody(url_json,json);
        requestBody.setCookie("multipost","test!!");
        multiHttpClient.sendPost(requestBody);
    }

    @Test
    public void multiGetCookieTest() throws HttpException {
        RequestBody requestBody = new RequestBody(url_get_cookie);
        ResponseBody responseBody = multiHttpClient.sendGet(requestBody);
        Cookie[] cookies = responseBody.getHeader().getCookies();
        if (cookies != null){
            for (Cookie cookie : cookies){
                System.out.println(cookie.getName() + "   " + cookie.getValue());
            }
        }
    }

    @Test
    public void multiPostCookieTest() throws HttpException {
        RequestBody requestBody = new RequestBody(url_post_cookie);
        ResponseBody responseBody = multiHttpClient.sendPost(requestBody);
        Cookie[] cookies = responseBody.getHeader().getCookies();
        if (cookies != null){
            for (Cookie cookie : cookies){
                System.out.println(cookie.getName() + "   " + cookie.getValue());
            }
        }
    }

    @Test
    public void poolGetCookieTest() throws HttpException {
        RequestBody requestBody = new RequestBody(url_get_cookie);
        ResponseBody responseBody = poolHttpClient.sendGet(requestBody);
        Header header = responseBody.getHeader();
        Cookie[] cookies = header.getCookies();
        for (Cookie cookie : cookies){
            System.out.println(cookie.getName() + "  " + cookie.getValue());
            System.out.println(cookie.getExpiryDate());
        }
    }

    @Test
    public void poolPostCookieTest() throws HttpException {
        RequestBody requestBody = new RequestBody(url_post_cookie);
        ResponseBody responseBody = poolHttpClient.sendPost(requestBody);
        Header header = responseBody.getHeader();
        Cookie[] cookies = header.getCookies();
        for (Cookie cookie : cookies){
            System.out.println(cookie.getName() + "  " + cookie.getValue());
            System.out.println(cookie.getExpiryDate());
        }
    }
}
