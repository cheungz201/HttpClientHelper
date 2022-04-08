package xyz.cheungz.httphelper.core.multithreading;

import xyz.cheungz.httphelper.constant.HttpConstant;
import xyz.cheungz.httphelper.core.AbstractHttpClient;
import xyz.cheungz.httphelper.entity.Header;
import xyz.cheungz.httphelper.entity.RequestBody;
import xyz.cheungz.httphelper.entity.ResponseBody;

/**
 * 线程安全的Http请求
 *
 * @Program: HttpClientHelper
 * @Author: Zhang Zhe
 * @Create: 2022-03-13 20:50
 * @Version: 1.0.0
 **/
public class MultiHttpClient extends AbstractHttpClient {

    public MultiHttpClient() {
    }

    public MultiHttpClient(Header header) {
        this.headers = header;
    }



    public Header getHeader() {
        return headers;
    }

    public MultiHttpClient setHeader(Header header) {
        this.headers = header;
        return this;
    }


    @Override
    public ResponseBody sendPost(RequestBody requestBody) {
        requestBody.setMethod(HttpConstant.POST);
        return multiSend(requestBody);
    }

    @Override
    public ResponseBody sendGet(RequestBody requestBody) {
        requestBody.setMethod(HttpConstant.GET);
        return multiSend(requestBody);
    }
}
