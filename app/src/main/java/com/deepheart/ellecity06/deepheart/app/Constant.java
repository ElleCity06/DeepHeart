package com.deepheart.ellecity06.deepheart.app;

/**
 * @author ellecity06
 * @time 2018/5/8 15:17
 * @project NewTest
 * @packge name：com.example.ellecity06.newtest.app
 * @des 常量定义信息
 */

public class Constant {
    public static final String BASE_URL = "http://47.95.3.41:5050/";


    /* ================================Bomb  ===================================================*/
    public static final String BOMB_APPLICATION_ID = "db26a2a6efb1c75027eeb10f6c8e642b";
    public static final String BOMB_REST_API_KEY = "29eeef072e18f3a34073940c74053d43";
    public static final String BOMB_SECTET_KEY = "2c675f6b48ff474f";
    public static final String BOMB_MASTER_KEY = "bc3cace4f4db93ba752c84e45fee2f73";

    // ======================================= 异常处理常量 =========================================
    // 网络链接超时
    public static final String SOCKETTIMEOUTEXCEPTION = "java.net.SocketTimeoutException";
    // json解析异常
    public static final String JSONSYNTAXEXCEPTION = "com.google.gson.JsonSyntaxException";


    // =========================================== 网络 ============================================
    // 网络请求的超时时长
    public static final int REQUEST_TIMEOUT_TIME = 30;
    // 没有网络
    public static final String WITHOUT_NETWORK = "without network";
    // 网络访问错误，请稍后再试
    public static final String NET_ERROR = "net error";
    // 网络连接超时
    public static final String NETWORK_CONNECTION_TIMEOUT = "network connection timeout";
}
