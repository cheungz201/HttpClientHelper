package xyz.cheungz.httphelper;

import org.junit.Test;
import xyz.cheungz.httphelper.constant.HttpConstant;
import xyz.cheungz.httphelper.core.Client;
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
    //@Test
    public void sendTest() {
        Client client = new Client();
        client.setUrl("http://localhost:8080/json").setJson(json).setMode(HttpConstant.BODY);
        String s = client.sendPost();
        System.out.println(s);
    }

    @Test
    public void multiSend(){
        MultiHttpClient client = new MultiHttpClient();
        System.out.println(client.sendPost(url_json, json, HttpConstant.BODY));
    }
}
