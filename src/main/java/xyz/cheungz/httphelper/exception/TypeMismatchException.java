package xyz.cheungz.httphelper.exception;

/**
 * 类型不匹配异常
 *
 * @Program: HttpClientHelper
 * @Author: Zhang Zhe
 * @Create: 2022-03-13 13:28
 * @Version: 1.0.0
 **/
public class TypeMismatchException extends RuntimeException{

    public TypeMismatchException(String details){
        super(details);
    }

    public TypeMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public TypeMismatchException(Throwable cause) {
        super(cause);
    }

    public TypeMismatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public TypeMismatchException() {
        super("type mismatch !");
    }

}
