package xyz.cheungz.httphelper;

import org.junit.Test;
import xyz.cheungz.httphelper.constant.HttpConstant;
import xyz.cheungz.httphelper.core.Client;

import java.util.HashMap;
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void sendTest() {
        Map<String,String> map = new HashMap();
        map.put("username","zhangzhe123");
        String json = "{\n" +
                "\t\"username\": \"zhangzhe\"\n" +
                "}";
        Client client = new Client();
        //client.setUrl("http://localhost/map").setJson(json);
        client.setUrl("http://localhost/json").setJson(json).setMode(HttpConstant.FORM);
        String s = client.sendPost();
        System.out.println(s);
    }
}
