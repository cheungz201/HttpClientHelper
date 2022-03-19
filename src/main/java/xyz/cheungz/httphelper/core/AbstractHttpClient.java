package xyz.cheungz.httphelper.core;


import org.apache.commons.httpclient.HttpClient;
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
import org.apache.logging.log4j.Logger;
import xyz.cheungz.httphelper.constant.HttpConstant;
import xyz.cheungz.httphelper.exception.TypeMismatchException;
import xyz.cheungz.httphelper.utils.BaseUtils;
import xyz.cheungz.httphelper.utils.ClientFactory;
import xyz.cheungz.httphelper.utils.LogUtil;
import xyz.cheungz.httphelper.utils.SerializationUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 顶层的HttpClient
 *
 * @Program: HttpClientHelper
 * @Author: Zhang Zhe
 * @Create: 2022-03-13 13:06
 * @Version: 1.0.0
 **/
public abstract class AbstractHttpClient implements HttpAble {

    Logger logger = LogUtil.getLogger(this.getClass());

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
     * @param header 请求头
     * @return 响应数据
     */
    @Override
    public abstract String sendPost(String url, String json, Map<String, String> header);

    /**
     * 发送get请求
     * @param url 请求路径
     * @param header 请求头
     * @return
     */
    @Override
    public abstract String sendGet(String url, Map<String, String> header);


    /**
     * 发送请求
     * @param url 请求路径
     * @param json 请求数据
     * @param method 请求方法，get或post
     * @return 响应数据
     */
    protected String send(String url, String json,String method, Map<String,String> header){
        String result = null;
        CloseableHttpResponse response = null;
        try {
            if (HttpConstant.POST.equalsIgnoreCase(method)) {
                HttpPost post = new HttpPost(url);
                if (HttpConstant.FORM.equalsIgnoreCase(getContentType(header))){
                    Map<String,String> map = SerializationUtil.string2Obj(json, HashMap.class);
                    List<NameValuePair> pairs = new ArrayList<>();
                    for (String key : map.keySet()){
                        pairs.add(new BasicNameValuePair(key,map.get(key)));
                    }
                    post.setEntity(new UrlEncodedFormEntity(pairs,StandardCharsets.UTF_8));
                }else if (HttpConstant.BODY.equalsIgnoreCase(getContentType(header))){
                    StringEntity requestEntity = new StringEntity(json, StandardCharsets.UTF_8);
                    post.setEntity(requestEntity);
                }else {
                    throw new TypeMismatchException("content-type not found!");
                }
                setHeader(header,post);
                response = closeableHttpClient.execute(post);
                if (response != null && response.getStatusLine().getStatusCode() == HttpConstant.RESULT_CODE) {
                    HttpEntity entity = response.getEntity();
                    result = BaseUtils.entityToString(entity);
                }
            } else if (HttpConstant.GET.equals(method)) {
                HttpGet get = new HttpGet(url);
                setHeader(header,get);
                response = closeableHttpClient.execute(get);
                result = BaseUtils.entityToString(response.getEntity());
            } else {
                throw new TypeMismatchException("request method not found!");
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
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
     * multi发送器
     * @param url 请求地址
     * @param json 请求数据
     * @param method 请求方法
     * @param header 请求头
     * @return 响应数据
     */
    protected String multiSend(String url, String json,String method, Map<String,String> header){
        String result =  null;
        if (HttpConstant.POST.equals(method)){
            result = multiPostSend(url, json, header);
        }else if (HttpConstant.GET.equals(method)){
            result = multiGetSend(url,header);
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
     */
    private String multiPostSend(String url, String json, Map<String,String> header) {
        PostMethod request = new PostMethod(url);
        String result = null;
        try{
            setHeader(header,request);
            if (HttpConstant.FORM.equalsIgnoreCase(getContentType(header))){
                Map<String,String> map = SerializationUtil.string2Obj(json, HashMap.class);
                for (String key: map.keySet()){
                    request.setParameter(key,map.get(key));
                }
            }else if (HttpConstant.BODY.equalsIgnoreCase(getContentType(header))){
                request.setRequestBody(json);
            }else {
                throw new TypeMismatchException("content-type not found");
            }
            multiHttpClient.executeMethod(request);
            result = BaseUtils.inStreamToString(request.getResponseBodyAsStream());
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            request.releaseConnection();
        }
        return result;
    }

    /**
     * 发送multiGet
     * @param url
     * @return 响应数据
     */
    private String multiGetSend(String url,Map<String,String> header) {
        String result = null;
        GetMethod request = new GetMethod(url);
        setHeader(header,request);
        try {
            multiHttpClient.executeMethod(request);
            result = BaseUtils.inStreamToString(request.getResponseBodyAsStream());
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            request.releaseConnection();
        }
        return result;
    }

    /**
     * 获取header中的content-type信息
     * @param header 请求头
     * @return String
     */
    private String getContentType(Map<String,String> header){
        for (String key : header.keySet()){
            if (HttpConstant.CONTENT_TYPE.equalsIgnoreCase(key)){
                return header.get(key);
            }
        }
        return null;
    }

    /**
     * 设置请求头
     * @param header 请求头信息
     * @param t
     * @param <T>
     */
    private <T> void setHeader(Map<String,String> header,T t){
        if (t instanceof HttpPost){
            HttpPost post = (HttpPost) t;
            for (String key : header.keySet()){
                post.setHeader(key,header.get(key));
            }
        }else if(t instanceof HttpGet){
            HttpGet get = (HttpGet) t;
            for (String key : header.keySet()){
                get.setHeader(key,header.get(key));
            }
        }else if (t instanceof PostMethod){
            PostMethod postMethod = (PostMethod) t;
            HttpMethodParams params = postMethod.getParams();
            params.setParameter(HttpMethodParams.SO_TIMEOUT,60 * 1000);
            params.setHttpElementCharset("UTF-8");
            params.setContentCharset("UTF-8");
            params.setUriCharset("UTF-8");
            for (String key : header.keySet()){
                postMethod.setRequestHeader(key,header.get(key));
            }
        }else if (t instanceof GetMethod){
            GetMethod getMethod = (GetMethod) t;
            for (String key : header.keySet()){
                getMethod.setRequestHeader(key,header.get(key));
            }
        }else  {
            throw new TypeMismatchException("request parameter mismatch");
        }
    }
}
