package xyz.cheungz.httphelper.core;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import xyz.cheungz.httphelper.constant.HttpConstant;
import xyz.cheungz.httphelper.exception.TypeMismatchException;
import xyz.cheungz.httphelper.utils.BaseUtils;
import xyz.cheungz.httphelper.utils.ClientFactory;
import xyz.cheungz.httphelper.utils.SerializationUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Program: HttpClientHelper
 * @Author: Zhang Zhe
 * @Create: 2022-03-13 13:06
 * @Version: 1.0.0
 * @Description:
 **/
public abstract class AbstractHttpClient {


    /**
     * 唯一的CloseableHttpClient
     */
    private final static CloseableHttpClient closeableHttpClient = ClientFactory.getPoolHttpClient();

    /**
     * 唯一的HttpClient
     */
    private final static HttpClient multiHttpClient = ClientFactory.getMultiHttpClient();

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
    protected String send(String url, String json,String method, String mode){
        String result = null;
        CloseableHttpResponse response = null;
        try {
            if (HttpConstant.POST.equals(method)) {
                HttpPost post = new HttpPost(url);
                if (HttpConstant.FORM.equals(mode)){
                    Map<String,String> map = SerializationUtil.string2Obj(json, HashMap.class);
                    List<NameValuePair> pairs = new ArrayList<>();
                    for (String key : map.keySet()){
                        pairs.add(new BasicNameValuePair(key,map.get(key)));
                    }
                        post.setEntity(new UrlEncodedFormEntity(pairs,StandardCharsets.UTF_8));
                }else if (HttpConstant.BODY.equals(mode)){
                    StringEntity requestEntity = new StringEntity(json, StandardCharsets.UTF_8);
                    requestEntity.setContentType(mode);
                    post.setEntity(requestEntity);
                }else {
                    throw new TypeMismatchException("content-type not found!");
                }
                response = closeableHttpClient.execute(post);
                if (response != null && response.getStatusLine().getStatusCode() == HttpConstant.RESULT_CODE) {
                    HttpEntity entity = response.getEntity();
                    result = BaseUtils.entityToString(entity);
                }
            } else if (HttpConstant.GET.equals(method)) {
                HttpGet get = new HttpGet(url);
                response = closeableHttpClient.execute(get);
                result = BaseUtils.entityToString(response.getEntity());
            } else {
                throw new TypeMismatchException("request method not found!");
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

        return result;
    }

    /**
     *
     * @param url 请求地址
     * @param json 请求数据
     * @param method 请求方法
     * @param mode content-type
     * @return
     */
    protected String multiSend(String url, String json,String method, String mode){
        String result =  null;
        if (HttpConstant.POST.equals(method)){
            result = postSend(url, json, mode);
        }else if (HttpConstant.GET.equals(method)){
            result = getSend(url);
        }else {
            throw new TypeMismatchException("request method not found!");
        }
        return result;
    }


    /**
     * 发送multiPost
     * @param url  请求地址
     * @param json 请求数据
     * @param mode content-type
     * @return 响应数据
     * @throws IOException
     */
    private String postSend(String url, String json, String mode) {
        PostMethod request = new PostMethod(url);
        String result = null;
        try{
            HttpMethodParams params = request.getParams();
            params.setParameter(HttpMethodParams.SO_TIMEOUT,60 * 1000);
            params.setHttpElementCharset("UTF-8");
            params.setContentCharset("UTF-8");
            params.setUriCharset("UTF-8");
            request.addRequestHeader(HttpConstant.CONTENT_TYPE,mode);
            request.setRequestBody(json);
            multiHttpClient.executeMethod(request);
            result = BaseUtils.inStreamToString(request.getResponseBodyAsStream());
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            request.releaseConnection();
        }
        return result;
    }

    /**
     * 发送multiGet
     * @param url
     * @return 响应数据
     * @throws IOException
     */
    private String getSend(String url) {
        String result = null;
        GetMethod request = new GetMethod(url);
        try {
            multiHttpClient.executeMethod(request);
            result = BaseUtils.inStreamToString(request.getResponseBodyAsStream());
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            request.releaseConnection();
        }
        return result;
    }

}
