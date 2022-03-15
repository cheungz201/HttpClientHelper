package xyz.cheungz.httphelper.core;

import com.fasterxml.jackson.core.JsonProcessingException;

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
    public String sendPost(String url, String json, String mode) {
        return client.sendPost(url,json,mode);
    }

    @Override
    public String sendGet(String url) {
        return client.sendGet(url);
    }

    /**
     * 发送post请求
     * @param url 请求地址
     * @param mode content-type
     * @param map 请求数据
     * @throws JsonProcessingException
     * @return 响应数据
     */
    public String sendPost(String url,String mode,Map map) throws JsonProcessingException {
        String json = resolveMap(map);
        return client.sendPost(url, json, mode);
    }

    /**
     * 解析map为json
     * @param map 请求数据
     * @return 解析结果
     * @throws JsonProcessingException
     */
    public abstract String resolveMap(Map map) throws JsonProcessingException;

}
