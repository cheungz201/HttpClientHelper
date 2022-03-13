package xyz.cheungz.httphelper.core;

import xyz.cheungz.httphelper.constant.HttpConstant;

/**
 * @Program: HttpClientHelper
 * @Author: Zhang Zhe
 * @Create: 2022-03-13 14:36
 * @Version: 1.0.0
 * @Description: 请求发送器
 **/
public class Client extends AbstractHttpClient {

    private String url;
    private String json;
    private String mode;

    public Client(String url, String json) {
        this.url = url;
        this.json = json;
        this.mode = HttpConstant.BODY;
    }

    public Client(String url, String json, String mode) {
        this.url = url;
        this.json = json;
        this.mode = mode;
    }

    public Client() {
    }

    /**
     * post的默认调用
     * @return 响应数据
     */
    public String sendPost() {
        if (this.mode != null){
            return send(this.url,this.json,HttpConstant.POST,this.mode);
        }
        return send(this.url,this.json,HttpConstant.POST,HttpConstant.BODY);
    }

    /**
     * 指定content-type的post请求
     * @param mode
     * @return 响应数据
     */
    public String sendPost(String mode){
        return send(this.url,this.json,HttpConstant.POST,mode);
    }


    /**
     * post自定义调用
     * @param url 请求路径
     * @param json 请求数据
     * @param mode 编码格式,x-www-form-urlencoded或body
     * @return 响应数据
     */
    @Override
    public String sendPost(String url, String json, String mode) {
        return send(url,json,HttpConstant.POST,mode);
    }

    /**
     * get调用
     * @param url 请求路径
     * @return 响应数据
     */
    @Override
    public String sendGet(String url) {
        return send(url,"",HttpConstant.GET,"");
    }

    /**
     * get调用
     * @return 响应数据
     */
    public String sendGet() {
        return send(this.url,"",HttpConstant.GET,"");
    }

    public String getUrl() {
        return url;
    }

    public Client setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getJson() {
        return json;
    }

    public Client setJson(String json) {
        this.json = json;
        return this;
    }

    public String getMode() {
        return mode;
    }

    public Client setMode(String mode) {
        this.mode = mode;
        return this;
    }
}
