package com.deepheart.ellecity06.deepheart.app;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.deepheart.ellecity06.deepheart.net.config.NetWorkConfiguration;
import com.deepheart.ellecity06.deepheart.net.http.HttpUtils;


public class DeepHeartApplication extends Application {

    private static DeepHeartApplication tbtpplication;

    @Override
    public void onCreate() {
        super.onCreate();
        tbtpplication = this;

        // 初始化网络
        initOkHttpUtils();
        // 初始化Bugly
        //        initBugly();
        //        // 初始化LeakCanary测试工具
        //        initLeakCanary();
    }

    //    private void initLeakCanary() {
    //        if (LeakCanary.isInAnalyzerProcess(this)) {
    //            // This process is dedicated to LeakCanary for heap analysis.
    //            // You should not init your app in this process.
    //            return;
    //        }
    //
    //        LeakCanary.install(this);
    //        // Normal app init code...
    //    }
    //
    //    private void initBugly() {
    //        CrashReport.initCrashReport(getApplicationContext(), "3d7dc46580", false);
    //    }

    public static Context getAppContext() {
        return tbtpplication;
    }

    public static Resources getAppResources() {
        return tbtpplication.getResources();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    private void initOkHttpUtils() {
        // 网络配置
        NetWorkConfiguration configuration = new NetWorkConfiguration(this)
                .baseUrl(URLConstant.urlbase)
                .isCache(false)
                .isDiskCache(false)
                .isMemoryCache(false);
        HttpUtils.setConFiguration(configuration);

    }
}