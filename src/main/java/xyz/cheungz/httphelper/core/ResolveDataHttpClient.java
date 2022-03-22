package xyz.cheungz.httphelper.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import xyz.cheungz.httphelper.constant.HttpConstant;
import xyz.cheungz.httphelper.utils.SerializationUtil;

import java.util.Map;

/**
 * 对HttpClient的包装，使得可以解析更多数据格式，例如map，list等等。
 *
 * @Program: HttpClientHelper
 * @Author: Zhang Zhe
 * @Create: 2022-03-15 15:51
 * @Version: 1.0.0
 **/
public class ResolveDataHttpClient extends HttpClientWrapper{

    public ResolveDataHttpClient(AbstractHttpClient client) {
        super(client);
    }

    /**
     * 解析map为json
     * @param map 请求数据
     * @return 解析结果
     * @throws JsonProcessingException
     */
    public String resolveMap(Map map) throws JsonProcessingException {
        return SerializationUtil.obj2String(map);
    }

    /**
     * 发送post请求
     * @param url 请求地址
     * @param data 请求数据
     * @param header 请求头
     * @throws JsonProcessingException
     * @return 响应数据
     */
    public String sendPost(String url,Map data,Map<String, String> header) throws JsonProcessingException {
        String json = resolveMap(data);
        return super.sendPost(url, json, header);
    }

    public String sendPost(String url,Map data) throws JsonProcessingException {
        String json = resolveMap(data);
        return super.sendPost(url, json, HttpConstant.DEFAULT_REQUEST_HEADER);
    }

}
