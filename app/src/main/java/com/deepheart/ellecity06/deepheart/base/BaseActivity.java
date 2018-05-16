package com.deepheart.ellecity06.deepheart.base;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.ellecity06.newtest.app.Constant;
import com.example.ellecity06.newtest.utils.ContractProxy;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import cn.bmob.v3.Bmob;

/**
 * @author ellecity06
 * @time 2018/5/8 14:48
 * @des Activity 的基类
 */

public abstract class BaseActivity<M extends BaseModel, P extends BasePresenter> extends RxAppCompatActivity {

    // RxBus管理类
    public RxManager mRxManager;
    // 定义Presenter
    protected P mPresenter;

    // 获取布局资源文件
    protected abstract int getLayoutId();

    // 初始化数据
    protected abstract void onInitView(Bundle bundle);

    // 初始化事件Event
    protected abstract void onEvent();

    // 获取抽取View对象
    protected abstract BaseView getView();

    // 获得抽取接口Model对象
    protected Class getModelClazz() {
        return (Class<M>) ContractProxy.getModelClazz(getClass(), 0);
    }

    // 获得抽取接口Presenter对象
    protected Class getPresenterClazz() {
        return (Class<P>) ContractProxy.getPresnterClazz(getClass(), 1);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        if (getLayoutId() != 0) {
            // 设置布局资源文件
            setContentView(getLayoutId());
            Bmob.initialize(this, Constant.BOMB_APPLICATION_ID);
            bindMVP();
            onInitView(savedInstanceState);
            onEvent();

        }

        //        mNFCShowUtils = new NFCShowUtils(this);
        //        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        //        mNFCShowUtils.resolveIntent(getIntent());
        // 获取默认的NFC控制器
        //        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        //        //拦截系统级的NFC扫描，例如扫描蓝牙
        //        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
        //                getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        //
        //        mNdefPushMessage = new NdefMessage(new NdefRecord[]{mNFCShowUtils.newTextRecord("",
        //                Locale.ENGLISH, true)});
    }

    /**
     * 获取presenter 实例
     */
    private void bindMVP() {
        if (getPresenterClazz() != null) {
            mPresenter = getPresenterImpl();
            mPresenter.mContext = this;
            bindVM();
        }
    }

    private <T> T getPresenterImpl() {
        return ContractProxy.getInstance().presenter(getPresenterClazz());
    }

    @Override
    protected void onStart() {
        if (mPresenter == null) {
            bindMVP();
        }
        super.onStart();
    }

    private void bindVM() {
        if (mPresenter != null && !mPresenter.isViewBind() && getModelClazz() != null && getView() != null) {
            ContractProxy.getInstance().bindModel(getModelClazz(), mPresenter);
            ContractProxy.getInstance().bindView(getView(), mPresenter);
            mPresenter.mContext = this;
        }
    }

    /**
     * activity摧毁
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mPresenter != null) {
            ContractProxy.getInstance().unbindView(getView(), mPresenter);
            ContractProxy.getInstance().unbindModel(getModelClazz(), mPresenter);
            mPresenter = null;
        }

        if (mRxManager != null) {
            mRxManager.clear();
        }
    }

    //    @Override
    //    protected void onResume() {
    //        super.onResume();
    //        mRxManager = new RxManager();
    //
    //        if (mNfcAdapter == null) {
    //            return;
    //        }
    //        if (!mNfcAdapter.isEnabled()) {
    //            return;
    //        }
    //
    //        if (mNfcAdapter != null) {
    //            //隐式启动
    //            mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, null, null);
    //            mNfcAdapter.enableForegroundNdefPush(this, mNdefPushMessage);
    //        }
    //    }
    //
    //    @Override
    //    protected void onPause() {
    //        super.onPause();
    //        if (mNfcAdapter != null) {
    //            //隐式启动
    //            mNfcAdapter.disableForegroundDispatch(this);
    //            mNfcAdapter.disableForegroundNdefPush(this);
    //        }
    //    }

    /**
     * 点击空白处关闭软键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
