package com.deepheart.ellecity06.deepheart.net.callback;

import android.content.Context;

import com.deepheart.ellecity06.deepheart.R;
import com.deepheart.ellecity06.deepheart.app.Constants;
import com.deepheart.ellecity06.deepheart.app.DeepHeartApplication;
import com.deepheart.ellecity06.deepheart.utils.LogUtil;
import com.deepheart.ellecity06.deepheart.utils.NetWorkUtils;
import com.deepheart.ellecity06.deepheart.widget.CustomProgressDialog;

import rx.Subscriber;

/**
 * @author ellecity06
 * @time 2018/5/9 9:35
 * @des 订阅封装
 */
public abstract class RxSubscriber<T> extends Subscriber<T> {

    private CustomProgressDialog mCustomProgressDialog;
    private Context mContext;
    private boolean isShowDialog = true;

    /**
     * 是否显示浮动dialog
     */
    public void showDialog() {
        this.isShowDialog = true;
    }

    public void hideDialog() {
        this.isShowDialog = true;
    }

    public RxSubscriber(Context context) {
        this(context, DeepHeartApplication.getAppContext().getString(R.string.loading), true);
    }

    public RxSubscriber(Context context, boolean showDialog) {
        this(context, DeepHeartApplication.getAppContext().getString(R.string.loading), showDialog);
    }

    public RxSubscriber(Context context, String msg, boolean showDialog) {
        this.mContext = context;
        this.isShowDialog = showDialog;

        mCustomProgressDialog = CustomProgressDialog.creatDialog(context);
        mCustomProgressDialog.setMessage(msg);
    }

    @Override
    public void onCompleted() {
        if (mCustomProgressDialog != null)
            mCustomProgressDialog.dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isShowDialog) {
            if (mCustomProgressDialog != null)
                mCustomProgressDialog.show();
        }
    }

    @Override
    public void onNext(T t) {
        if (mCustomProgressDialog != null)
            mCustomProgressDialog.dismiss();

        _onNext(t);
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        String exception = e.toString();
        if (isShowDialog) {
            mCustomProgressDialog.dismiss();
        }

        // 网络
        if (!NetWorkUtils.isNetConnected(DeepHeartApplication.getAppContext())) {
            _onError(Constants.WITHOUT_NETWORK);
        } else { // 其它
            if (exception.contains(Constants.SOCKETTIMEOUTEXCEPTION)) {
                _onError(Constants.NETWORK_CONNECTION_TIMEOUT);
            } else {
                _onError(Constants.NET_ERROR);
            }
        }

        LogUtil.e("http", "HttpException = " + e.toString());
    }

    protected abstract void _onNext(T t);

    protected abstract void _onError(String message);

}
