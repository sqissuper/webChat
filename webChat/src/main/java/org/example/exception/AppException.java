package org.example.exception;

/**
 * ClassName:AppException
 * Package:org.example.exception
 * Description:
 *
 * @Author:HP
 * @date:2021/5/17 14:34
 */
public class AppException extends RuntimeException {
    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }
}
