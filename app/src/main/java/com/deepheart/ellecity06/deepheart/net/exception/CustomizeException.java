package com.deepheart.ellecity06.deepheart.net.exception;

/**
 * @author ellecity06
 * @time 2018/5/9 9:37
 * @des 自定义的异常
 */

public class CustomizeException extends RuntimeException {

    public CustomizeException(String message, Throwable cause) {
        super(message, cause);
    }
}
