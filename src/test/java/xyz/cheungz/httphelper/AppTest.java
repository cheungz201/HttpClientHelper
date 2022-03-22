package xyz.cheungz.httphelper;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import xyz.cheungz.httphelper.constant.HttpConstant;
import xyz.cheungz.httphelper.core.PoolHttpClient;
import xyz.cheungz.httphelper.core.ResolveDataHttpClient;
import xyz.cheungz.httphelper.core.multithreading.MultiHttpClient;

import java.util.Date;
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
        poolHttpClient.setUrl("http://localhost:8080/json").setJson(json).setHeader(HttpConstant.DEFAULT_REQUEST_HEADER);
        System.out.println(poolHttpClient.sendPost()+"1");
    }

    @Test
    public void poolSendPostJsonNoHeader() {
        PoolHttpClient poolHttpClient = new PoolHttpClient();
        poolHttpClient.setUrl("http://localhost:8080/json").setJson(json);
        System.out.println(poolHttpClient.sendPost()+"1");
    }

    @Test
    public void poolSendGet(){
        PoolHttpClient poolHttpClient = new PoolHttpClient();
        poolHttpClient.setUrl("http://localhost:8080/get?username=zhangsan");
        Map<String,String> cookies = new HashMap<>();
        cookies.put("zhansgan","18");
        cookies.put("lisi","19");
        poolHttpClient.setCookies(cookies);
        System.out.println(poolHttpClient.sendGet()+"2");
    }

    @Test
    public void poolSenPostForm(){
        PoolHttpClient poolHttpClient =  new PoolHttpClient();
        Map map = new HashMap();
        map.put(HttpConstant.CONTENT_TYPE,HttpConstant.FORM);
        poolHttpClient.setUrl(url_form).setJson(json).setHeader(map);
        System.out.println(poolHttpClient.sendPost()+"3");
    }

    @Test
    public void multiSendPostJson(){
        MultiHttpClient client = new MultiHttpClient();
        System.out.println(client.sendPost(url_json, json,HttpConstant.DEFAULT_REQUEST_HEADER)+"4");
    }

    @Test
    public void multiSendPostForm(){
        MultiHttpClient client = new MultiHttpClient();
        Map map = new HashMap();
        map.put(HttpConstant.CONTENT_TYPE,HttpConstant.FORM);
        System.out.println(client.sendPost(url_form, json,map)+"5");
    }

    @Test
    public void multiSendGet(){
        MultiHttpClient client = new MultiHttpClient();
        System.out.println(client.sendGet("http://localhost:8080/get?username=zhangsan")+"6");
    }

    @Test
    public void resolveClientTest() throws JsonProcessingException {
        ResolveDataHttpClient client = new ResolveDataHttpClient(new MultiHttpClient());

        String s = client.sendPost(url_json, map);
        System.out.println(s);

        //System.out.println(client.sendGet("http://www.cheungz.xyz/article/26"));


    }

    @Test
    public void Date(){
        Date date = new Date();
        date.setTime(1647878756135l);
        System.out.println(date);
    }


    @Test
    public void Web(){
        MultiHttpClient client = new MultiHttpClient();
        client.setUrl("http://localhost/admin/index");
        client.setCookie("JSESSIONID","668BA512BDD8098A3A654B1DCCA63143");
        String s = client.sendGet();
        System.out.println(s);
    }

}
