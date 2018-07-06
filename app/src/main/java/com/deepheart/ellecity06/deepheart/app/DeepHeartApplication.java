package com.deepheart.ellecity06.deepheart.app;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.deepheart.ellecity06.deepheart.BuildConfig;
import com.deepheart.ellecity06.deepheart.net.config.NetWorkConfiguration;
import com.deepheart.ellecity06.deepheart.net.http.HttpUtils;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;


public class DeepHeartApplication extends Application {

    private static DeepHeartApplication tbtpplication;
    //    将此深心奉尘刹，则为报佛恩；

    @Override
    public void onCreate() {
        super.onCreate();
        tbtpplication = this;

        // 初始化网络
        initOkHttpUtils();
        // 初始化Bugly
        initBugly();
        // 初始化LeakCanary测试工具
        initLeakCanary();
        initUMeng();
    }

    /**
     * 初始化友盟
     */
    private void initUMeng() {
        UMConfigure.setLogEnabled(true);
        UMConfigure.init(this, "59892f08310c9307b60023d0", "Umeng", UMConfigure.DEVICE_TYPE_PHONE,
                "669c30a9584623e70e8cd01b0381dcb4");

        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        //豆瓣RENREN平台目前只能在服务器端配置
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
        //        PlatformConfig.setYixin("yxc0614e80c9304c11b0391514d09f13bf");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
    }

    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }

        LeakCanary.install(this);
        // Normal app init code...
    }

    private void initBugly() {
        // release版本得时候才开启统计
        if (!BuildConfig.DEBUG) {
            CrashReport.initCrashReport(getApplicationContext(), Constants.BUGLY_APPID, false);
        }
    }

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