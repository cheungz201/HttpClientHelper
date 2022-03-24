package xyz.cheungz.httphelper.core;


import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
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
import xyz.cheungz.httphelper.entity.Header;
import xyz.cheungz.httphelper.entity.RequestBody;
import xyz.cheungz.httphelper.entity.ResponseBody;
import xyz.cheungz.httphelper.exception.HttpException;
import xyz.cheungz.httphelper.exception.TypeMismatchException;
import xyz.cheungz.httphelper.utils.BaseUtils;
import xyz.cheungz.httphelper.utils.ClientFactory;
import xyz.cheungz.httphelper.utils.LogUtil;
import xyz.cheungz.httphelper.utils.SerializationUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;


/**
 * 顶层的HttpClient
 *
 * @Program: HttpClientHelper
 * @Author: Zhang Zhe
 * @Create: 2022-03-13 13:06
 * @Version: 1.0.0
 **/
public abstract class AbstractHttpClient implements HttpAble {

    protected Header headers;

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
     * @param requestBody 请求体
     * @return 响应数据
     */
    @Override
    public abstract ResponseBody sendPost(RequestBody requestBody) throws HttpException;

    /**
     * 发送get请求
     * @param requestBody 请求体
     * @return 响应数据
     */
    @Override
    public abstract ResponseBody sendGet(RequestBody requestBody) throws HttpException;


    /**
     * 发送请求
     * @param requestBody 请求体
     * @return 响应数据
     */
    protected ResponseBody poolSend(RequestBody requestBody) throws HttpException {
        String url = requestBody.getUrl();
        String data = requestBody.getData();
        String method = requestBody.getMethod();
        Header header = requestBody.getHeader();

        ResponseBody result = null;
        CloseableHttpResponse response = null;
        try {
            if (HttpConstant.POST.equalsIgnoreCase(method)) {
                HttpPost post = new HttpPost(url);
                if (HttpConstant.FORM.equalsIgnoreCase(getContentType(header.getHeaders()))){
                    Map<String,String> map = SerializationUtil.string2Obj(data, HashMap.class);
                    List<NameValuePair> pairs = new ArrayList<>();
                    for (String key : map.keySet()){
                        pairs.add(new BasicNameValuePair(key,map.get(key)));
                    }
                    post.setEntity(new UrlEncodedFormEntity(pairs,StandardCharsets.UTF_8));
                }else if (HttpConstant.BODY.equalsIgnoreCase(getContentType(header.getHeaders()))){
                    StringEntity requestEntity = new StringEntity(data, StandardCharsets.UTF_8);
                    post.setEntity(requestEntity);
                }else {
                    throw new TypeMismatchException("content-type not found !");
                }
                setHeader(requestBody.getHeader(), post);
                response = closeableHttpClient.execute(post);
            } else if (HttpConstant.GET.equalsIgnoreCase(method)) {
                HttpGet get = new HttpGet(url);
                setHeader(requestBody.getHeader(), get);
                response = closeableHttpClient.execute(get);
            } else {
                throw new TypeMismatchException("request method not found !");
            }
            if (response != null && response.getStatusLine().getStatusCode() == HttpConstant.RESULT_CODE) {
                result = BaseUtils.getResponse(response);
            }else {
                throw new HttpException("error code:"+response.getStatusLine().getStatusCode());
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
     * @param requestBody 请求体
     * @return 响应数据
     */
    protected ResponseBody multiSend(RequestBody requestBody){
        ResponseBody result =  null;
        if (HttpConstant.POST.equalsIgnoreCase(requestBody.getMethod())){
            result = multiPostSend(requestBody);
        }else if (HttpConstant.GET.equalsIgnoreCase(requestBody.getMethod())){
            result = multiGetSend(requestBody);
        }else {
            throw new TypeMismatchException("request method not found!");
        }
        return result;
    }


    /**
     * 发送multiPost
     * @param requestBody 请求体
     * @return 响应数据
     */
    private ResponseBody multiPostSend(RequestBody requestBody) {
        PostMethod request = new PostMethod(requestBody.getUrl());
        ResponseBody result = null;
        try{
            setHeader(requestBody.getHeader(), request);
            if (HttpConstant.FORM.equalsIgnoreCase(getContentType(requestBody.getHeader().getHeaders()))){
                Map<String,String> map = SerializationUtil.string2Obj(requestBody.getData(), HashMap.class);
                for (String key: map.keySet()){
                    request.setParameter(key,map.get(key));
                }
            }else if (HttpConstant.BODY.equalsIgnoreCase(getContentType(requestBody.getHeader().getHeaders()))){
                request.setRequestBody(requestBody.getData());
            }else {
                throw new TypeMismatchException("content-type not found");
            }
            multiHttpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
            multiHttpClient.executeMethod(request);
            result = BaseUtils.getResponse(multiHttpClient,request);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            request.releaseConnection();
        }
        return result;
    }

    /**
     * 发送multiGet
     * @param requestBody 请求体
     * @return 响应数据
     */
    private ResponseBody multiGetSend(RequestBody requestBody) {
        ResponseBody result = null;
        GetMethod request = new GetMethod(requestBody.getUrl());
        setHeader(requestBody.getHeader(), request);
        try {
            multiHttpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
            multiHttpClient.executeMethod(request);
            result = BaseUtils.getResponse(multiHttpClient,request);
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
    private <T> void setHeader(Header header,T t){
        Map<String, String> headers = header.getHeaders();
        if (t instanceof HttpPost){
            HttpPost post = (HttpPost) t;
            for (String key : headers.keySet()){
                post.setHeader(key,headers.get(key));
            }
            if (header.getCookies() == null){ return; }
            post.setHeader(HttpConstant.COOKIE,BaseUtils.getCookieBody(header));
        }else if(t instanceof HttpGet){
            HttpGet get = (HttpGet) t;
            for (String key : headers.keySet()){
                get.setHeader(key,headers.get(key));
            }
            if (header.getCookies() == null){ return; }
            get.setHeader(HttpConstant.COOKIE,BaseUtils.getCookieBody(header));
        }else if (t instanceof PostMethod){
            PostMethod postMethod = (PostMethod) t;
            HttpMethodParams params = postMethod.getParams();
            params.setParameter(HttpMethodParams.SO_TIMEOUT,60 * 1000);
            params.setHttpElementCharset("UTF-8");
            params.setContentCharset("UTF-8");
            params.setUriCharset("UTF-8");
            for (String key : headers.keySet()){
                postMethod.setRequestHeader(key,headers.get(key));
            }
            if (header.getCookies() == null){ return; }
            postMethod.setRequestHeader(HttpConstant.COOKIE,BaseUtils.getCookieBody(header));
        }else if (t instanceof GetMethod){
            GetMethod getMethod = (GetMethod) t;
            for (String key : headers.keySet()){
                getMethod.setRequestHeader(key,headers.get(key));
            }
            if (header.getCookies() == null){ return; }
            getMethod.setRequestHeader(HttpConstant.COOKIE,BaseUtils.getCookieBody(header));
        }else  {
            throw new TypeMismatchException("request parameter mismatch");
        }
    }

    /**
     * 设置cookie
     * 首先检查cookies,如果未初始化则初始化长度为5,再设置cookie。
     * 如果数组已满，则扩容至旧数组的1.5倍(并非严格的1.5倍，若为偶数则为1.5倍，否则长度为旧数组长度1.5倍-1)
     * 然后再添加cookie
     *
     * @param key cookie键
     * @param value cookie值
     */
    public void setCookie(String key, String value){

        Cookie[] cookies = this.headers.getCookies();
        if (cookies == null){
            cookies = new Cookie[5];
            this.headers.setCookies(cookies);
        }
        if (setCookie(cookies,key,value)) {
            return;
        }
        // 如果cookies已满,则扩容并且复制旧数组到新数组
        Cookie[] newCookies = Arrays.copyOf(cookies, (cookies.length + cookies.length / 2));
        this.headers.setCookies(newCookies);
        setCookie(newCookies,key,value);
    }

    /**
     * 设置cookie，成功返回true，否则返回false
     * @param cookies newCookies
     * @param key key
     * @param value value
     * @return boolean
     */
    private boolean setCookie(Cookie[] cookies, String key, String value){
        for (int i = 0; i < cookies.length; i++){
            Cookie cookie = cookies[i];
            if (cookie == null) {
                cookie = new Cookie();
                cookie.setName(key);
                cookie.setValue(value);
                cookies[i] = cookie;
                return true;
            }
        }
        return false;
    }
}
