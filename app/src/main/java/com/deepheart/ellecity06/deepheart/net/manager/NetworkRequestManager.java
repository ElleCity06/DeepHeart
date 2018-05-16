package com.deepheart.ellecity06.deepheart.net.manager;

/**
 * @author ellecity06
 * @time 2018/5/9 9:48
 * @des 统一管理网络请求类
 */

public class NetworkRequestManager {

    private NetworkRequestManager() {
    }

    /* =======================请求示例=================================*/
    //    public static Observable<UserLoginResBean> loginNetworkRequest(Context context, String phone, final String loginkey) {
    //        return HttpUtils.getInstance(context)
    //                .setLoadDiskCache(false)
    //                .getRetofitClinet()
    //                .builder(DeepHeartApiService.class)
    //                .userLogin(NetworkRequestParamsManager.getInstance(context).setLoginparams(phone, loginkey))
    //                .compose(new DefaultTransformer<UserLoginResBean>());
    //    }

}
