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

    public ResponseBody(String result, Header header) {
        this.result = result;
        this.header = header;
    }

    public ResponseBody() {
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
}
