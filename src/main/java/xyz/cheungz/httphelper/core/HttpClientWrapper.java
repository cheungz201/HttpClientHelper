package xyz.cheungz.httphelper.core;

import xyz.cheungz.httphelper.entity.RequestBody;
import xyz.cheungz.httphelper.entity.ResponseBody;
import xyz.cheungz.httphelper.exception.HttpException;

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
    public ResponseBody sendPost(RequestBody requestBody) throws HttpException {
        return client.sendPost(requestBody);
    }

    @Override
    public ResponseBody sendGet(RequestBody requestBody) throws HttpException {
        return client.sendGet(requestBody);
    }

}
