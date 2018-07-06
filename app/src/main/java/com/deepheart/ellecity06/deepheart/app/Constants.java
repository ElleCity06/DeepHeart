package com.deepheart.ellecity06.deepheart.app;

/**
 * @author ellecity06
 * @time 2018/5/8 15:17
 * @project NewTest
 * @packge name：com.example.ellecity06.newtest.app
 * @des 常量定义信息
 */

public class Constants {
    public static final String BASE_URL = "http://47.95.3.41:5050/";


    /* ================================Bomb  ===================================================*/
    public static final String BOMB_APPLICATION_ID = "db26a2a6efb1c75027eeb10f6c8e642b";
    public static final String BOMB_REST_API_KEY = "29eeef072e18f3a34073940c74053d43";
    public static final String BOMB_SECTET_KEY = "2c675f6b48ff474f";
    public static final String BOMB_MASTER_KEY = "bc3cace4f4db93ba752c84e45fee2f73";

    // ======================================= Bugly =========================================
    public static final String BUGLY_APPID = "2ae60e20cb";
    // ======================================= 友盟+  =========================================
    public static final String UMENG_APPKEY = "5b0f51b2f29d9808f700005e";

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


    // =========================================== 底部tab ============================================


    // =========================================== 其他其他 ============================================
    public static final String SHOW_INTRODUCTION_PAGE = "show_introduction_page"; // 是否显示引导页


    // =========================================== 通知的Tag ============================================
    public static final String LOGIN_SUCCESS = "login_success"; // 登录成功通知

    // =========================================== Acticity跳转带 的Tag ============================================
    public static final String PHONE_NUMBER = "phone_number"; // 登录成功通知
    public static final String LOGIN_TYPE = "login_type"; // 登录成功通知
    public static final String LOGIN_DATA = "login_data"; // 登录成功通知
    public static final String BIND_PHONE_TYPE = "bind_phone_type"; // 绑定手机号的类型
    // =========================================== 第三方登录 ============================================
    public static final String TYPE_QQ = "QQ"; // qq登录
    public static final String TYPE_WEIXIN = "WEIXIN"; // 微信
    public static final String TYPE_WEIBO = "SINA"; // 微博 新浪
}
