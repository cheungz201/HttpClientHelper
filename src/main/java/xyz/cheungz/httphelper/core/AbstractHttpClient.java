package xyz.cheungz.httphelper.core;


import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;
import xyz.cheungz.httphelper.constant.HttpConstant;
import xyz.cheungz.httphelper.exception.RequestMethodNotFoundException;
import xyz.cheungz.httphelper.utils.ClientFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @Program: HttpClientHelper
 * @Author: Zhang Zhe
 * @Create: 2022-03-13 13:06
 * @Version: 1.0.0
 * @Description:
 **/
public abstract class AbstractHttpClient {


    /**
     * 唯一的HttpClient
     */
    private final static CloseableHttpClient closeableHttpClient = ClientFactory.getPoolHttpClient();

    /**
     * 发送post请求
     * @param url 请求路径
     * @param json 请求数据
     * @param mode 编码格式,x-www-form-urlencoded或body
     * @return 响应数据
     */
    public abstract String sendPost(String url, String json, String mode);

    /**
     * 发送get请求
     * @param url 请求路径
     * @return
     */
    public abstract String sendGet(String url);


    /**
     * 发送请求
     * @param url 请求路径
     * @param json 请求数据
     * @param method 请求方法，get或post
     * @param mode 编码格式,x-www-form-urlencoded或body
     * @return 响应数据
     */
    public String send(String url, String json,String method, String mode){
        String result = null;
        CloseableHttpResponse response = null;
        try {
            if (HttpConstant.POST.equals(method)) {
                HttpPost post = new HttpPost(url);
                StringEntity requestEntity = new StringEntity(json, StandardCharsets.UTF_8);
                requestEntity.setContentType(mode);
                post.setEntity(requestEntity);
                response = closeableHttpClient.execute(post);
                if (response != null && response.getStatusLine().getStatusCode() == HttpConstant.RESULT_CODE) {
                    HttpEntity entity = response.getEntity();
                    result = entityToString(entity);
                    return result;
                }
            } else if (HttpConstant.GET.equals(method)) {
                HttpGet get = new HttpGet(url);
                response = closeableHttpClient.execute(get);
                result = entityToString(response.getEntity());
                return result;
            } else {
                throw new RequestMethodNotFoundException("request method not found!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    /**
     * 处理HttpEntity实体
     * @param entity
     * @return
     * @throws IOException
     * @return 处理结果
     */
    private  String entityToString(HttpEntity entity) throws IOException {
        String result = null;
        if (entity != null) {
            long length = entity.getContentLength();
            if (length != -1 && length < 2048) {
                result = EntityUtils.toString(entity, "UTF-8");
            } else {
                InputStreamReader reader1 = new InputStreamReader(entity.getContent(), "UTF-8");
                CharArrayBuffer buffer = new CharArrayBuffer(2048);
                char[] tmp = new char[1024];
                int l;
                while ((l = reader1.read(tmp)) != -1) {
                    buffer.append(tmp, 0, l);
                }
                result = buffer.toString();
            }
        }
        return result;
    }


}
