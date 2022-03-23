package xyz.cheungz.httphelper.entity;

import xyz.cheungz.httphelper.constant.HttpConstant;

import java.io.Serializable;

/**
 * Request包装类
 *
 * @Program: HttpClientHelper
 * @Author: Zhang Zhe
 * @Create: 2022-03-23 08:24
 * @Version: 1.0.0
 **/
public class RequestBody implements Serializable {


    private static final long serialVersionUID = 67160013866408466L;
    private String url;
    private String data;
    private String method;
    private Header header;

    public RequestBody(String url, String data, String method, Header header) {
        this.url = url;
        this.data = data;
        this.method = method;
        this.header = header;
    }

    public RequestBody(String url, String data) {
        this.url = url;
        this.data = data;
        this.header = HttpConstant.DEFAULT_REQUEST_HEADER;
    }

    public RequestBody(String url, String method, Header header) {
        this.url = url;
        this.data="";
        this.method = method;
        this.header = header;
    }

    public RequestBody(String url, String data, String method) {
        this.url = url;
        this.data = data;
        this.method = method;
        this.header = HttpConstant.DEFAULT_REQUEST_HEADER;
    }

    public RequestBody(String url) {
        this.url = url;
        this.data = "";
        this.method = HttpConstant.GET;
        this.header = HttpConstant.DEFAULT_REQUEST_HEADER;
    }

    public String getUrl() {
        return url;
    }

    public RequestBody setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getData() {
        return data;
    }

    public RequestBody setData(String data) {
        this.data = data;
        return this;
    }

    public String getMethod() {
        return method;
    }

    public RequestBody setMethod(String method) {
        this.method = method;
        return this;
    }

    public Header getHeader() {
        return header;
    }

    public RequestBody setHeader(Header header) {
        this.header = header;
        return this;
    }
}
