package xyz.cheungz.httphelper.core.multithreading;

import xyz.cheungz.httphelper.constant.HttpConstant;
import xyz.cheungz.httphelper.core.AbstractHttpClient;

/**
 * @Program: HttpClientHelper
 * @Author: Zhang Zhe
 * @Create: 2022-03-13 20:50
 * @Version: 1.0.0
 * @Description:
 **/
public class MultiHttpClient extends AbstractHttpClient {

    private String url;
    private String json;
    private String mode;

    public MultiHttpClient(String url, String json) {
        this.url = url;
        this.json = json;
        this.mode = HttpConstant.BODY;
    }

    public MultiHttpClient(String url, String json, String mode) {
        this.url = url;
        this.json = json;
        this.mode = mode;
    }

    public MultiHttpClient() { }

    @Override
    public String sendPost(String url, String json, String mode) {
        return multiSend(url, json, HttpConstant.POST, mode);
    }

    public String sendPost(String url, String json){
        if (this.mode != null){
            multiSend(url, json, HttpConstant.POST, mode);
        }
        return multiSend(url,json, HttpConstant.POST,HttpConstant.BODY);
    }

    @Override
    public String sendGet(String url) {
        return multiSend(url,"",HttpConstant.GET,"");
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

    public String getMode() {
        return mode;
    }

    public MultiHttpClient setMode(String mode) {
        this.mode = mode;
        return this;
    }
}
