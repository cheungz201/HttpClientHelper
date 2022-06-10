package xyz.cheungz.httphelper.core;


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


    @Override
    public abstract ResponseBody sendPost(RequestBody requestBody) throws HttpException;


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
    protected ResponseBody multiSend(RequestBody requestBody) throws HttpException {
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
    private ResponseBody multiPostSend(RequestBody requestBody) throws HttpException {
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
            int code = multiHttpClient.executeMethod(request);
            if (code != 200){
                throw new HttpException("error code:" + code);
            }
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
    private ResponseBody multiGetSend(RequestBody requestBody) throws HttpException {
        ResponseBody result = null;
        GetMethod request = new GetMethod(requestBody.getUrl());
        setHeader(requestBody.getHeader(), request);
        try {
            multiHttpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
            int code = multiHttpClient.executeMethod(request);
            if (code != 200){
                throw new HttpException("error code:" + code);
            }
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
}
