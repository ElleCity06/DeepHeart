package com.deepheart.ellecity06.deepheart.net.exception;

/**
 * @author ellecity06
 * @time 2018/5/9 9:39
 * @des 服务器异常信息处理
 */

public class ServerException extends RuntimeException {
    public int code;
    public String message;

    public ServerException(String message, int code) {
        this.message = message;
        this.code = code;
    }
}
