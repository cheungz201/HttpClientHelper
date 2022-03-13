package xyz.cheungz.httphelper.exception;

/**
 * @Program: HttpClientHelper
 * @Author: Zhang Zhe
 * @Create: 2022-03-13 13:28
 * @Version: 1.0.0
 * @Description:
 **/
public class TypeMismatchException extends RuntimeException{
    public TypeMismatchException(String details){

        super(details);
    }
}
