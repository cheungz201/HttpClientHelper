package xyz.cheungz.httphelper.core;

import xyz.cheungz.httphelper.entity.RequestBody;
import xyz.cheungz.httphelper.entity.ResponseBody;

/**
 * 请求发送抽象接口
 *
 * @Program: HttpClientHelper
 * @Author: Zhang Zhe
 * @Create: 2022-03-14 13:53
 * @Version: 1.0.0
 **/
public interface HttpAble {

    /**
     * 发送post请求
     * @param requestBody 请求体
     * @return 响应数据
     */
    ResponseBody sendPost(RequestBody requestBody);

    /**
     * 发送get请求
     * @param requestBody 请求体
     * @return 响应数据
     */
    ResponseBody sendGet(RequestBody requestBody);

}
