package xyz.cheungz.httphelper.core;

import org.apache.commons.lang3.StringUtils;
import xyz.cheungz.httphelper.constant.HttpConstant;
import xyz.cheungz.httphelper.exception.ParameterException;

import java.util.Map;

/**
 * 请求发送器
 *
 * @Program: HttpClientHelper
 * @Author: Zhang Zhe
 * @Create: 2022-03-13 14:36
 * @Version: 1.0.0
 **/
public class PoolHttpClient extends AbstractHttpClient {

    private String url;
    private String json;
    private Map<String, String> header;

    public PoolHttpClient(String url, String json) {
        this.url = url;
        this.json = json;
        this.header = HttpConstant.DEFAULT_REQUEST_HEADER;
    }

    public PoolHttpClient(String url, String json, Map<String, String> header) {
        this.url = url;
        this.json = json;
        this.header = header;
    }

    public PoolHttpClient() {
        this.header = HttpConstant.DEFAULT_REQUEST_HEADER;
    }

    public String getUrl() {
        return url;
    }

    public PoolHttpClient setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getJson() {
        return json;
    }

    public PoolHttpClient setJson(String json) {
        this.json = json;
        return this;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public PoolHttpClient setHeader(Map<String, String> header) {
        this.header = header;
        return this;
    }

    @Override
    public String sendPost(String url, String json, Map<String, String> header) {
        return send(url,json,HttpConstant.POST,header);
    }

    /**
     * post的默认调用
     * @return 响应数据
     */
    public String sendPost() {
        if (StringUtils.isNotBlank(this.url) && StringUtils.isNotBlank(this.json)){
            return sendPost(this.url,this.json,header);
        }
        throw new ParameterException();
    }

    public String sendPost(String url,String json){
        return sendPost(url,json,HttpConstant.DEFAULT_REQUEST_HEADER);
    }

    /**
     * 指定header的post请求
     * @param header
     * @return 响应数据
     */
    public String sendPost(Map<String, String> header){
        if (StringUtils.isNotBlank(this.url) && StringUtils.isNotBlank(this.json)){
            return sendPost(this.url,this.json,header);
        }
        throw new ParameterException();
    }

    @Override
    public String sendGet(String url,Map<String, String> header) {
        return send(url,"",HttpConstant.GET,header);
    }

    /**
     * get默认调用
     * @return 响应数据
     */
    public String sendGet() {
        return sendGet(this.url,this.header);
    }

    /**
     * 指定header的get请求
     * @param header
     * @return 响应请求
     */
    public String sendGet(Map<String, String> header){
        return sendGet(this.url,header);
    }
}
