package xyz.cheungz.httphelper.exception;

/**
 * 参数不匹配异常
 *
 * @Program: HttpClientHelper
 * @Author: Zhang Zhe
 * @Create: 2022-03-19 23:21
 * @Version: 1.0.0
 **/
public class ParameterException extends RuntimeException{
    public ParameterException() {
        super("parameter mismatch !");
    }

    public ParameterException(String message) {
        super(message);
    }
}
