package xyz.cheungz.httphelper;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import xyz.cheungz.httphelper.constant.HttpConstant;
import xyz.cheungz.httphelper.core.PoolHttpClient;
import xyz.cheungz.httphelper.core.ResolveDataHttpClient;
import xyz.cheungz.httphelper.core.multithreading.MultiHttpClient;

import java.util.HashMap;
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class AppTest {

    static String url_json = "http://localhost:8080/json";
    static String url_form = "http://localhost:8080/str";
    static Map<String,String> map = new HashMap();
    static String json = "{\n" +
            "\t\"username\": \"zhangzhe\"\n" +
            "}";

    static{
        map.put("username","zhangzhe123");
    }


    /**
     * Rigorous Test :-)
     */
    @Test
    public void poolSendPostJson() {
        PoolHttpClient poolHttpClient = new PoolHttpClient();
        poolHttpClient.setUrl("http://localhost:8080/json").setJson(json).setMode(HttpConstant.BODY);
        System.out.println(poolHttpClient.sendPost()+"1");
    }

    @Test
    public void poolSendGet(){
        PoolHttpClient poolHttpClient = new PoolHttpClient();
        poolHttpClient.setUrl("http://localhost:8080/get?username=zhangsan");
        System.out.println(poolHttpClient.sendGet()+"2");
    }

    @Test
    public void poolSenPostForm(){
        PoolHttpClient poolHttpClient =  new PoolHttpClient();
        poolHttpClient.setUrl(url_form).setJson(json).setMode(HttpConstant.FORM);
        System.out.println(poolHttpClient.sendPost()+"3");
    }

    //@Test
    public void multiSendPostJson(){
        MultiHttpClient client = new MultiHttpClient();
        System.out.println(client.sendPost(url_json, json, HttpConstant.BODY)+"4");
    }

    @Test
    public void multiSendPostForm(){
        MultiHttpClient client = new MultiHttpClient();
        System.out.println(client.sendPost(url_form, json,HttpConstant.FORM)+"5");
    }

    @Test
    public void multiSendGet(){
        MultiHttpClient client = new MultiHttpClient();
        System.out.println(client.sendGet("http://localhost:8080/get?username=zhangsan")+"6");
    }

    @Test
    public void resolveClientTest(){
        ResolveDataHttpClient client = new ResolveDataHttpClient(new MultiHttpClient());

        /*try {
            String s = client.sendPost(url_json,HttpConstant.BODY,map);
            System.out.println(s);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }*/

        System.out.println(client.sendGet("http://www.cheungz.xyz/article/26"));


    }



}
