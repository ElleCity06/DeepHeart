package com.deepheart.ellecity06.deepheart.net.callback;

/**
 * @author ellecity06
 * @time 2018/5/9 9:36
 * @des 定义服务器请求异常
 */
public class ServerException extends Exception {

    public ServerException(String msg) {
        super(msg);
    }
}
