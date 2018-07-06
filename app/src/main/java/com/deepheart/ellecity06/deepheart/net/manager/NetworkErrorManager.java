package com.deepheart.ellecity06.deepheart.net.manager;

import android.content.Context;

import com.deepheart.ellecity06.deepheart.R;
import com.deepheart.ellecity06.deepheart.app.Constants;
import com.deepheart.ellecity06.deepheart.utils.ToastUtils;

/**
 * @author ellecity06
 * @time 2018/5/9 9:47
 * @des 网络错误管理
 */

public class NetworkErrorManager {
    public static void showErrorInfo(Context context, String errorInfo) {
        switch (errorInfo) {
            case Constants.WITHOUT_NETWORK:
                ToastUtils.showToast(context, context.getResources().getString(R.string.no_net));
                break;

            case Constants.NET_ERROR:
                ToastUtils.showToast(context, context.getResources().getString(R.string.system_is_busy));
                break;

            case Constants.NETWORK_CONNECTION_TIMEOUT:
                ToastUtils.showToast(context, context.getResources().getString(R.string.network_connection_timeout));
                break;
        }
    }
}
