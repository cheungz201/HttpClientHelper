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

    /**
     * 发送post请求
     * @param url 请求地址
     * @param data 请求数据
     * @param header 请求头
     * @throws JsonProcessingException
     * @return 响应数据
     */
    public String sendPost(String url,Map data,Map<String, String> header) throws JsonProcessingException {
        String json = resolveMap(data);
        return client.sendPost(url, json, header);
    }

    public String sendPost(String url,Map data) throws JsonProcessingException {
        String json = resolveMap(data);
        return client.sendPost(url, json, HttpConstant.DEFAULT_REQUEST_HEADER);
    }

    /**
     * 解析map为json
     * @param map 请求数据
     * @return 解析结果
     * @throws JsonProcessingException
     */
    public abstract String resolveMap(Map map) throws JsonProcessingException;

}
