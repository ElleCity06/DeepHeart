package com.deepheart.ellecity06.deepheart.net.manager;

import android.content.Context;

import com.deepheart.ellecity06.deepheart.net.http.HttpUtils;

/**
 * @author ellecity06
 * @time 2018/5/9 9:51
 * @des 网络请求参数的工具类
 */
public class NetworkRequestParamsManager {

    private static NetworkRequestParamsManager mInstance;
    public String mEquipmentId;
    private Context mContext;

    private NetworkRequestParamsManager(Context context) {
        //        mEquipmentId = EquipmentIdentificationUtils.getEquipmentID(context);
        this.mContext = context;
    }

    /**
     * 获取请求参数工具类
     *
     * @return
     */
    public static NetworkRequestParamsManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (HttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new NetworkRequestParamsManager(context);
                }
            }
        }
        return mInstance;
    }


    /**
     * 登陆的操作
     *
     * @param phone
     * @param loginkey
     * @return
     */
//    public RequestBody setLoginparams(String phone, String loginkey) {
//        RequestBody requestBody = new UserLoginRequest(phone, loginkey).buildEncryptString();
//        LogUtil.e("http", requestBody.toString());
//
//        return requestBody;
//    }

}
