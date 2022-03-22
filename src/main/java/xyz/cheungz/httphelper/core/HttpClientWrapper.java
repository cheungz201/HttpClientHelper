package xyz.cheungz.httphelper.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import xyz.cheungz.httphelper.constant.HttpConstant;

import java.util.Map;

/**
 * HTTPClient装饰器
 *
 * @Program: HttpClientHelper
 * @Author: Zhang Zhe
 * @Create: 2022-03-15 15:55
 * @Version: 1.0.0
 **/
public abstract class HttpClientWrapper extends AbstractHttpClient {

    private AbstractHttpClient client = null;

    public HttpClientWrapper(AbstractHttpClient client){
        this.client = client;
    }

    @Override
    public String sendPost(String url, String json, Map<String, String> header) {
        return client.sendPost(url,json,header);
    }

    @Override
    public String sendGet(String url,Map<String, String> header) {
        return client.sendGet(url,header);
    }

    public String sendPost(String url,String json){
        return sendPost(url,json, HttpConstant.DEFAULT_REQUEST_HEADER);
    }

    public String sendGet(String url){
        return sendGet(url,HttpConstant.DEFAULT_REQUEST_HEADER);
    }

}
