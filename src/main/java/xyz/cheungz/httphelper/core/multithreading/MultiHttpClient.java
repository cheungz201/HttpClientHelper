package xyz.cheungz.httphelper.core.multithreading;

import org.apache.commons.lang3.StringUtils;
import xyz.cheungz.httphelper.constant.HttpConstant;
import xyz.cheungz.httphelper.core.AbstractHttpClient;
import xyz.cheungz.httphelper.exception.ParameterException;

import java.util.Map;

/**
 * 线程安全的Http请求
 *
 * @Program: HttpClientHelper
 * @Author: Zhang Zhe
 * @Create: 2022-03-13 20:50
 * @Version: 1.0.0
 **/
public class MultiHttpClient extends AbstractHttpClient {

    private String url;
    private String json;
    private Map<String,String> header;

    public MultiHttpClient(String url, String json,Map<String, String> header) {
        this.url = url;
        this.json = json;
        this.header = header;
    }

    public MultiHttpClient(String url, String json) {
        this.url = url;
        this.json = json;
        this.header = HttpConstant.DEFAULT_REQUEST_HEADER;
    }

    public MultiHttpClient() {
        this.header = HttpConstant.DEFAULT_REQUEST_HEADER;
    }

    public String getUrl() {
        return url;
    }

    public MultiHttpClient setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getJson() {
        return json;
    }

    public MultiHttpClient setJson(String json) {
        this.json = json;
        return this;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public MultiHttpClient setHeader(Map<String, String> header) {
        this.header = header;
        return this;
    }

    @Override
    public String sendPost(String url, String json, Map<String, String> header) {
        return multiSend(url,json,HttpConstant.POST,header);
    }

    public String senPost(Map<String, String> header){
        if (StringUtils.isNotBlank(this.url) && StringUtils.isNotBlank(this.json)){
            return sendPost(this.url,this.json,header);
        }
        throw new ParameterException();
    }

    public String sendPost(){
        if (StringUtils.isNotBlank(this.url) && StringUtils.isNotBlank(this.json)){
            return sendPost(this.url,this.json,this.header);
        }
        throw new ParameterException();
    }

    @Override
    public String sendGet(String url, Map<String, String> header) {
        return multiSend(url,"",HttpConstant.GET,header);
    }

    public String sendGet(Map<String, String> header){
        if (StringUtils.isNotBlank(this.url) && StringUtils.isNotBlank(this.json)){
            return sendPost(this.url,this.json,header);
        }
        throw new ParameterException();
    }

    public String sendGet(String url){
        return sendGet(url,this.header);
    }

    public String sendGet(){
        if (StringUtils.isNotBlank(this.url) && StringUtils.isNotBlank(this.json)){
            return sendPost(this.url,this.json,this.header);
        }
        throw new ParameterException();
    }

}
