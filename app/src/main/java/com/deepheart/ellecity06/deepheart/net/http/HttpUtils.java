package com.deepheart.ellecity06.deepheart.net.http;

import android.content.Context;

import com.deepheart.ellecity06.deepheart.net.config.NetWorkConfiguration;
import com.deepheart.ellecity06.deepheart.net.cookie.SimpleCookieJar;
import com.deepheart.ellecity06.deepheart.net.request.RetrofitClient;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;


/**
 * @author ellecity06
 * @time 2018/5/9 9:41
 * @des Http请求的工具类
 */

public class HttpUtils {
    public static final String TAG = "HttpUtils";

    // 获得HttpUtils实例
    private static HttpUtils mInstance;
    // OkHttpClient对象
    private OkHttpClient mOkHttpClient;
    private static NetWorkConfiguration configuration;
    private Context context;
    /**
     * 是否加载本地缓存数据
     * 默认为false
     */
    private boolean isLoadDiskCache = false;

    /**
     * ---> 针对无网络情况
     * 是否加载本地缓存数据
     *
     * @param isCache true为加载 false不进行加载
     * @return
     */
    public HttpUtils setLoadDiskCache(boolean isCache) {
        this.isLoadDiskCache = isCache;
        return this;
    }

    /**
     * 针对有网络情况
     * 是否加载内存缓存数据
     * 默认为False
     */
    private boolean isLoadMemoryCache = false;

    /**
     * 是否加载内存缓存数据
     *
     * @param isCache true为加载 false不进行加载
     * @return
     */
    public HttpUtils setLoadMemoryCache(boolean isCache) {
        this.isLoadMemoryCache = isCache;
        return this;
    }

    private HttpUtils(Context context) {
        //创建默认 okHttpClient对象
        this.context = context;

        /**
         * 进行默认配置
         * 未配置configuration ,
         *
         */
        if (configuration == null) {
            configuration = new NetWorkConfiguration(context);
        }

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (configuration.getIsCache()) {
            mOkHttpClient = new OkHttpClient.Builder()
                    // 网络缓存拦截器
                    .addInterceptor(interceptor)
                    .cache(configuration.getDiskCache())
                    .connectTimeout(configuration.getConnectTimeOut(), TimeUnit.SECONDS)
                    .connectionPool(configuration.getConnectionPool())
                    .retryOnConnectionFailure(true)
                    .build();
        } else {
            mOkHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .connectTimeout(configuration.getConnectTimeOut(), TimeUnit.SECONDS)
                    .connectionPool(configuration.getConnectionPool())
                    .retryOnConnectionFailure(true)
                    .build();
        }

        /**
         *
         *  判断是否在AppLication中配置Https证书
         *
         */
        if (configuration.getCertificates() != null) {
            mOkHttpClient = getOkHttpClient().newBuilder()
                    .sslSocketFactory(HttpsUtils.getSslSocketFactory(configuration.getCertificates(), null, null))
                    .build();
        }
    }

    /**
     * 设置网络配置参数
     *
     * @param configuration
     */
    public static void setConFiguration(NetWorkConfiguration configuration) {

        if (configuration == null) {
            throw new IllegalArgumentException("ImageLoader configuration can not be initialized with null");
        } else {
            if (HttpUtils.configuration == null) {
                HttpUtils.configuration = configuration;
            }
        }

        if (configuration != null) {

        }
    }

    public RetrofitClient getRetofitClinet() {
        return new RetrofitClient(configuration.getBaseUrl(), mOkHttpClient);
    }

    /**
     * 设置HTTPS客户端带证书访问
     *
     * @param certificates 本地证书
     */
    public HttpUtils setCertificates(InputStream... certificates) {
        mOkHttpClient = getOkHttpClient().newBuilder()
                .sslSocketFactory(HttpsUtils.getSslSocketFactory(certificates, null, null))
                .build();
        return this;
    }

    /**
     * 设置是否打印网络日志
     *
     * @param falg
     */
    public HttpUtils setDBugLog(boolean falg) {
        if (falg) {
            mOkHttpClient = getOkHttpClient().newBuilder()
                    .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build();
        }
        return this;
    }

    /**
     * 设置Coolie
     *
     * @return
     */
    public HttpUtils addCookie() {
        mOkHttpClient = getOkHttpClient().newBuilder()
                .cookieJar(new SimpleCookieJar())
                .build();
        return this;
    }

    /**
     * 获得OkHttpClient实例
     *
     * @return
     */
    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    /**
     * 获取请求网络实例
     *
     * @return
     */
    public static HttpUtils getInstance(Context context) {
        if (mInstance == null) {
            synchronized (HttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new HttpUtils(context);
                }
            }
        }
        return mInstance;
    }
}
