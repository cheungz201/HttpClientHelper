package xyz.cheungz.httphelper.core;

import java.util.Map;

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
     * @param url 请求路径
     * @param json 请求数据
     * @param header 请求头
     * @return 响应数据
     */
    String sendPost(String url, String json, Map<String,String> header);

    /**
     * 发送get请求
     * @param url
     * @param header 请求头
     * @return 响应数据
     */
    String sendGet(String url,Map<String,String> header);

}
