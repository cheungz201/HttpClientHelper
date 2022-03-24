package xyz.cheungz.httphelper.entity;


import java.io.Serializable;

/**
 * Response包装类
 *
 * @Program: HttpClientHelper
 * @Author: Zhang Zhe
 * @Create: 2022-03-22 23:14
 * @Version: 1.0.0
 **/
public class ResponseBody implements Serializable {


    private static final long serialVersionUID = -3020854568490225248L;
    private String result;
    private Header header;
    private Integer responseCode;

    public ResponseBody(String result, Header header, Integer responseCode) {
        this.result = result;
        this.header = header;
        this.responseCode = responseCode;
    }

    public ResponseBody() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getResult() {
        return result;
    }

    public ResponseBody setResult(String result) {
        this.result = result;
        return this;
    }

    public Header getHeader() {
        return header;
    }

    public ResponseBody setHeader(Header header) {
        this.header = header;
        return this;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public ResponseBody setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
        return this;
    }
}
