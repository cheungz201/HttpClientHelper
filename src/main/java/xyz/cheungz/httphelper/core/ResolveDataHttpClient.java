package xyz.cheungz.httphelper.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import xyz.cheungz.httphelper.entity.RequestBody;
import xyz.cheungz.httphelper.entity.ResponseBody;
import xyz.cheungz.httphelper.exception.HttpException;
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

    public ResponseBody sendPost(RequestBody requestBody,Map<String,String> data) throws HttpException {
        try {
            String json = resolveMap(data);
            requestBody.setData(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return super.sendPost(requestBody);
    }
}
