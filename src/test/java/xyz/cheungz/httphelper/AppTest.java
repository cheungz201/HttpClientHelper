package xyz.cheungz.httphelper;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import xyz.cheungz.httphelper.constant.HttpConstant;
import xyz.cheungz.httphelper.core.PoolHttpClient;
import xyz.cheungz.httphelper.core.ResolveDataHttpClient;
import xyz.cheungz.httphelper.core.multithreading.MultiHttpClient;
import xyz.cheungz.httphelper.entity.RequestBody;
import xyz.cheungz.httphelper.entity.ResponseBody;

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


    @Test
    public void poolSendPostJson() {
        PoolHttpClient poolHttpClient = new PoolHttpClient();
        ResponseBody responseBody = poolHttpClient.sendPost(new RequestBody(url_json, json));
        System.out.println(responseBody.getResult());
        System.out.println(responseBody.getHeader().toString());
    }

    @Test
    public void poolSendPostJsonNoHeader() {
        PoolHttpClient poolHttpClient = new PoolHttpClient();
        poolSendGet();
    }

    @Test
    public void poolSendGet(){
        PoolHttpClient poolHttpClient = new PoolHttpClient();
    }

    @Test
    public void poolSenPostForm(){
        PoolHttpClient poolHttpClient =  new PoolHttpClient();
    }

    @Test
    public void multiSendPostJson(){
        MultiHttpClient client = new MultiHttpClient();
    }

    @Test
    public void multiSendPostForm(){
        MultiHttpClient client = new MultiHttpClient();
    }

    @Test
    public void multiSendGet(){
        MultiHttpClient client = new MultiHttpClient();
    }

    @Test
    public void resolveClientTest() throws JsonProcessingException {
        ResolveDataHttpClient client = new ResolveDataHttpClient(new MultiHttpClient());


    }

    @Test
    public void Web(){
        MultiHttpClient client = new MultiHttpClient();
    }

}
