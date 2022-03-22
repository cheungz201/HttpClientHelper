package xyz.cheungz.httphelper.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * http相关字段常量
 *
 * @Program: HttpClientHelper
 * @Author: Zhang Zhe
 * @Create: 2022-03-13 12:51
 * @Version: 1.0.0
 **/
public class HttpConstant {

    /**
     * 状态响应码
     */
    public final static Integer RESULT_CODE = 200;

    /**
     * Content-Type
     */
    public final static String CONTENT_TYPE = "Content-Type";

    /**
     * from表单提交
     */
    public final static String FORM = "application/x-www-form-urlencoded";

    /**
     * body提交
     */
    public final static String BODY = "application/json;charset=utf-8";

    /**
     * get请求
     */
    public final static String GET = "get";

    /**
     * post请求
     */
    public final static String POST = "post";

    /**
     * Cookie
     */
    public final static String COOKIE = "Cookie";

    /**
     *
     */
    public final static Map<String, String> DEFAULT_REQUEST_HEADER = new HashMap<>();

    static {
        DEFAULT_REQUEST_HEADER.put(CONTENT_TYPE, BODY);
        DEFAULT_REQUEST_HEADER.put("User-Agent","Mozilla/5.0 (Windows NT 6.1; rv:2.0.1) Gecko/20100101 Firefox/4.0.1");
        DEFAULT_REQUEST_HEADER.put("Accept","*/*");
    }



}
