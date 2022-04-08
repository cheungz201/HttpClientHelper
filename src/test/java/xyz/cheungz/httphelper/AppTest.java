package xyz.cheungz.httphelper;

import org.apache.commons.lang3.builder.ToStringExclude;
import org.apache.logging.log4j.core.util.JsonUtils;
import org.junit.Test;
import xyz.cheungz.httphelper.constant.HttpConstant;
import xyz.cheungz.httphelper.core.PoolHttpClient;
import xyz.cheungz.httphelper.core.multithreading.MultiHttpClient;
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
    static Map<String,String> map = new HashMap();
    static String json = "{\n" +
            "\t\"username\": \"zhangzhe\"\n" +
            "}";

    static{
        map.put("username","zhangsan");
    }

    static PoolHttpClient poolHttpClient = new PoolHttpClient();
    static MultiHttpClient multiHttpClient = new MultiHttpClient();

    /**
     * poolClient 发送get请求
     * @throws HttpException
     */
    @Test
    public void poolGetTest() throws HttpException {
        ResponseBody responseBody = poolHttpClient.sendGet(new RequestBody(url_get));
        System.out.println(responseBody.getResult());
    }

    /**
     * poolClient发送post请求，content-type = application/json;charset=utf-8
     * @throws HttpException
     */
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
    public void multiGetTest(){
        ResponseBody responseBody = multiHttpClient.sendGet(new RequestBody(url_get));
        System.out.println(responseBody.getResult());
    }

    @Test
    public void multiPostTest(){
        ResponseBody responseBody = multiHttpClient.sendPost(new RequestBody(url_json, json));
        System.out.println(responseBody.getResult());
    }

    @Test
    public void multiPostTestByXXX(){
        ResponseBody responseBody = multiHttpClient.sendPost(new RequestBody(url_form, json, HttpConstant.FORM));
        System.out.println(responseBody.getResult());
    }
}
