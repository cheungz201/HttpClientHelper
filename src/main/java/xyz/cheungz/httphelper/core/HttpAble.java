package xyz.cheungz.httphelper.core;

/**
 * @Program: HttpClientHelper
 * @Author: Zhang Zhe
 * @Create: 2022-03-14 13:53
 * @Version: 1.0.0
 * @Description:
 **/
public interface HttpAble {

    /**
     * 发送post请求
     * @param url 请求路径
     * @param json 请求数据
     * @param mode content-type
     * @return 响应数据
     */
    String sendPost(String url, String json, String mode);

    /**
     * 发送get请求
     * @param url
     * @return 响应数据
     */
    String sendGet(String url);

}
