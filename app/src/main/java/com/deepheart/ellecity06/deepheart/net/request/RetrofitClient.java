package com.deepheart.ellecity06.deepheart.net.request;

import com.deepheart.ellecity06.deepheart.net.factory.ExGsonConverterFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * @author ellecity06
 * @time 2018/5/9 9:55
 * @des retrofit 网络请求的封装
 */
public class RetrofitClient {

    private static OkHttpClient mOkHttpClient;
    //    初始化BaseUrl
    private static String baseUrl;

    private static Retrofit retrofit;

    /**
     * RetrofitClient 构造 函数
     * 获取OKhttpClient 实例
     *
     * @param mOkHttpClient
     */
    public RetrofitClient(String baseUrl, OkHttpClient mOkHttpClient) {
        this.baseUrl = baseUrl;
        this.mOkHttpClient = mOkHttpClient;
    }


    /**
     * 修改BaseUrl地址
     *
     * @param baseUrl
     */
    public RetrofitClient setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    /**
     * 获得对应的ApiServcie对象
     *
     * @param service
     * @param <T>
     * @return
     */
    public <T> T builder(Class<T> service) {
        if (baseUrl == null) {
            throw new RuntimeException("baseUrl is null!");
        }

        if (service == null) {
            throw new RuntimeException("api Service is null!");
        }

        retrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(ExGsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(service);
    }
}
