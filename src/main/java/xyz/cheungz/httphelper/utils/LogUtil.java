package xyz.cheungz.httphelper.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 日志记录工具类
 *
 * @Program: HttpClientHelper
 * @Author: Zhang Zhe
 * @Create: 2022-03-16 15:53
 * @Version: 1.0.0
 **/
public class LogUtil {

    /**
     * 生成一个Logger
     * @param T 需要记录的Class
     * @return Logger
     */
    public static Logger getLogger(Class T){
        return LogManager.getLogger(T.getName());
    }

}
