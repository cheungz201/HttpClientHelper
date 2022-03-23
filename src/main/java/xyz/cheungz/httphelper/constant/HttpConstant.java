package xyz.cheungz.httphelper.constant;

import sun.util.resources.cldr.zh.CalendarData_zh_Hans_HK;
import xyz.cheungz.httphelper.entity.Cookie;
import xyz.cheungz.httphelper.entity.Header;

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
     * 响应结果
     */
    public final static String RESPONSE_RESULT = "result";

    /**
     * 默认的Header
     */
    public final static Header DEFAULT_REQUEST_HEADER = new Header();

    /**
     * 默认的请求头
     */
    public final static Map<String, String> HEADER = new HashMap<>();

    static {
        HEADER.put(CONTENT_TYPE, BODY);
        HEADER.put("User-Agent","Mozilla/5.0 (Windows NT 6.1; rv:2.0.1) Gecko/20100101 Firefox/4.0.1");
        HEADER.put("Accept","*/*");
        DEFAULT_REQUEST_HEADER.setHeaders(HEADER);
        DEFAULT_REQUEST_HEADER.setCookies(new Cookie().setCookies(new HashMap<>()));
    }



}
