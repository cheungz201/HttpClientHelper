package xyz.cheungz.httphelper.core;

import xyz.cheungz.httphelper.constant.HttpConstant;
import xyz.cheungz.httphelper.entity.Header;
import xyz.cheungz.httphelper.entity.RequestBody;
import xyz.cheungz.httphelper.entity.ResponseBody;
import xyz.cheungz.httphelper.exception.HttpException;

/**
 * 请求发送器
 *
 * @Program: HttpClientHelper
 * @Author: Zhang Zhe
 * @Create: 2022-03-13 14:36
 * @Version: 1.0.0
 **/
public class PoolHttpClient extends AbstractHttpClient {

    public PoolHttpClient() {
        this.headers = HttpConstant.DEFAULT_REQUEST_HEADER;
    }

    public PoolHttpClient(Header header){
        this.headers = header;
    }

    @Override
    public ResponseBody sendPost(RequestBody requestBody) throws HttpException {
        requestBody.setMethod(HttpConstant.POST);
        return poolSend(requestBody);
    }

    @Override
    public ResponseBody sendGet(RequestBody requestBody) throws HttpException {
        requestBody.setMethod(HttpConstant.GET);
        return poolSend(requestBody);
    }
}
