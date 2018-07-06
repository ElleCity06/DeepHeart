package com.deepheart.ellecity06.deepheart.base;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.deepheart.ellecity06.deepheart.BuildConfig;
import com.deepheart.ellecity06.deepheart.R;
import com.deepheart.ellecity06.deepheart.app.Constants;
import com.deepheart.ellecity06.deepheart.app.DeepHeartApplication;
import com.deepheart.ellecity06.deepheart.utils.ContractProxy;
import com.deepheart.ellecity06.deepheart.utils.LogUtils;
import com.deepheart.ellecity06.deepheart.utils.ScreenUtil;
import com.deepheart.ellecity06.deepheart.utils.ToastUtils;
import com.deepheart.ellecity06.deepheart.widget.ShapeLoadingDialog;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.Bmob;

/**
 * @author ellecity06
 * @time 2018/5/8 14:48
 * @des Activity 的基类
 */

public abstract class BaseActivity2<M extends BaseModel, P extends BasePresenter> extends RxAppCompatActivity {

    // RxBus管理类
    public RxManager mRxManager;
    // 定义Presenter
    protected P mPresenter;
    //顶部导航栏
    @BindView(R.id.navbar_title)
    public ViewGroup navBarView;
    @BindView(R.id.tv_navbar_left)
    TextView txtTitleLeft;
    @BindView(R.id.navbar_left)
    FrameLayout navBarLeft;
    @BindView(R.id.tv_navbar_title)
    TextView txtTitle;
    @BindView(R.id.tv_navbar_right)
    TextView txtTitleRight;
    @BindView(R.id.navbar_right)
    FrameLayout navBarRight;
    @BindView(R.id.tv_navbar_middle)
    TextView tvNavBarMiddle;
    @BindView(R.id.dh_container)
    FrameLayout mDhContainer;
    private ShapeLoadingDialog shapeLoadingDialog;
    private Unbinder mBind;

    // 获取布局资源文件
    protected abstract int getLayoutId();

    // 初始化数据
    protected abstract void onInitView(Bundle bundle);


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
        //        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        ViewGroup rootView = (ViewGroup) getLayoutInflater().inflate(R.layout.base_activity, null);
        FrameLayout contentView = rootView.findViewById(R.id.dh_container);

        View childView = this.getLayoutInflater().inflate(getLayoutId(), null);
        ViewGroup parent = (ViewGroup) childView.getParent();
        if (parent != null) {
            parent.removeAllViews();
        }
        contentView.addView(childView);
        setContentView(rootView);
        mBind = ButterKnife.bind(this);

        // 设置布局资源文件
        //            setContentView(getLayoutId());
        Bmob.initialize(this, Constants.BOMB_APPLICATION_ID);
        bindMVP();
        try {
            onInitView(savedInstanceState);
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                LogUtils.logStackTrace(e);
                ToastUtils.showBigToast(DeepHeartApplication.getAppContext(), "非常抱歉，发生软件运行错误，我们会尽快解决该问题，请关注版本更新。");
            }

        }


    }


    @Override
    public void onResume() {
        super.onResume();
        // 友盟统计
        MobclickAgent.onResume(this);

    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
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
        hideLoadingDialog(true);
        super.onDestroy();
        if (mBind != null) {
            mBind.unbind();
        }
        if (mPresenter != null) {
            ContractProxy.getInstance().unbindView(getView(), mPresenter);
            ContractProxy.getInstance().unbindModel(getModelClazz(), mPresenter);
            mPresenter = null;
        }

        if (mRxManager != null) {
            mRxManager.clear();
        }
    }


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

    // ==========utils============
    public void startActivity(Class<? extends Activity> clazz) {
        startActivity(new Intent(this, clazz));
    }

    @SuppressWarnings("unchecked")
    public <F extends BaseDHFragment> F findFragment(int id) {
        return (F) getSupportFragmentManager().findFragmentById(id);
    }

    @SuppressWarnings("unchecked")
    public <F extends BaseDHFragment> F findFragment(String tag) {
        return (F) getSupportFragmentManager().findFragmentByTag(tag);
    }

    @SuppressWarnings("unchecked")
    protected <V extends View> V findView(int id) {
        return (V) findViewById(id);
    }

    @SuppressWarnings("unchecked")
    protected <V extends View> V findView(View parent, int id) {
        return (V) parent.findViewById(id);
    }

    public void setTitle(int titleId) {
        if (txtTitle != null) {
            txtTitle.setText(getText(titleId));
        }
    }

    public void setTitle(String title) {
        if (txtTitle != null) {
            txtTitle.setText(title);
        }
    }

    protected void setNavBarViewStatusHeightPadding(boolean isSetPadding) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (isSetPadding) {
                navBarView.setPadding(0, ScreenUtil.getStatusHeight(getApplicationContext()), 0, 0);
            } else {
                navBarView.setPadding(0, 0, 0, 0);
            }
        }
    }

    public void setTvLeftAction(int actionId) {
        setTvLeftAction(getText(actionId), 0, 0, 0, 0);
    }

    public void setTvLeftAction(CharSequence actionId, int leftImg, int rightImg, int topImg, int bottomImg) {
        setNavBarLeft(View.VISIBLE);
        if (txtTitleLeft != null) {
            txtTitleLeft.setText(actionId);
            txtTitleLeft.setVisibility(View.VISIBLE);
            txtTitleLeft.setCompoundDrawablesWithIntrinsicBounds(leftImg, topImg, rightImg, bottomImg);
            txtTitleLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (actionCallBack != null) {
                        actionCallBack.getLeftActionCallBack(v);
                    }
                }
            });
        }
    }

    public void setTvRightAction(int actionId) {
        setTvRightAction(getText(actionId), 0, 0, 0, 0);
    }

    public void setTvRightAction(CharSequence actionId, int leftImg, int rightImg, int topImg, int bottomImg) {
        setNavBarRight(View.VISIBLE);
        if (txtTitleRight != null) {
            txtTitleRight.setText(actionId);
            txtTitleRight.setVisibility(View.VISIBLE);
            txtTitleRight.setCompoundDrawablesWithIntrinsicBounds(leftImg, topImg, rightImg, bottomImg);
            txtTitleRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (actionCallBack != null) {
                        actionCallBack.getRightActionCallBack(v);
                    }
                }
            });
        }
    }

    public void setTvMiddleAction(int actionId) {
        setTvMiddleAction(getText(actionId), 0, 0, 0, 0);
    }

    public void setTvMiddleAction(CharSequence actionId, int leftImg, int rightImg, int topImg, int bottomImg) {
        if (tvNavBarMiddle != null) {
            tvNavBarMiddle.setText(actionId);
            tvNavBarMiddle.setVisibility(View.VISIBLE);
            tvNavBarMiddle.setCompoundDrawablesWithIntrinsicBounds(leftImg, topImg, rightImg, bottomImg);
            tvNavBarMiddle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (actionCallBack != null) {
                        actionCallBack.getMiddleActionCallBack(v);
                    }
                }
            });
        }
    }

    public void hideActionBar() {
        navBarView.setVisibility(View.GONE);
    }

    public void showActionBar() {
        navBarView.setVisibility(View.VISIBLE);
    }


    public TextView getTvLeftAction() {
        return txtTitleLeft;
    }

    public TextView getTvRightAction() {
        return txtTitleRight;
    }

    public ViewGroup getNavBarLeft() {
        return navBarLeft;
    }

    public void setNavBarLeft(int visible) {
        this.navBarLeft.setVisibility(visible);
    }

    public ViewGroup getNavBarRight() {
        return navBarRight;
    }

    public void setNavBarRight(int visible) {
        this.navBarRight.setVisibility(visible);
    }

    public void setTvNavBarMiddle(int visible) {
        this.tvNavBarMiddle.setVisibility(visible);
    }

    private ActionCallBack actionCallBack;

    public void setOnActionCallBackListener(ActionCallBack actionCallBack) {
        this.actionCallBack = actionCallBack;
    }

    /**
     * 左边按钮点击事件(如回退箭头) 中间偏右按钮点击事件(如搜索) 右边按钮点击事件(如添加)
     */
    public interface ActionCallBack {
        void getLeftActionCallBack(View view);

        void getMiddleActionCallBack(View view);

        void getRightActionCallBack(View view);
    }

    /**
     * 适配器
     */
    public class ActionCallBackAdapter implements ActionCallBack {
        @Override
        public void getLeftActionCallBack(View view) {

        }

        @Override
        public void getRightActionCallBack(View view) {

        }

        @Override
        public void getMiddleActionCallBack(View view) {

        }
    }

    private int loadingSemaphore;

    /**
     * 如果出现未处理的请求失败，将强制关闭loading dialog
     */
    public void showLoadingDialog(boolean cancelable) {
        showLoadWordDialog("请稍候...", cancelable);
    }

    /**
     * 显示loading dialog，计数器+1
     * <p>
     * 如果出现未处理的请求失败，将强制关闭loading dialog
     */
    public void showLoadingDialog() {
        showLoadingDialog(true);
    }

    /**
     * 隐藏loading dialog，计数器-1
     * <p>
     * 计数器归零则关闭loading dialog
     */
    public void hideLoadingDialog() {
        hideLoadingDialog(false);
    }

    /**
     * 强制关闭loading dialog
     * <p>
     * 并将计数器归零
     */
    public void hideLoadingDialog(boolean forceClose) {
        loadingSemaphore--;
        if (forceClose || loadingSemaphore < 0) {
            loadingSemaphore = 0;
        }
        if (loadingSemaphore == 0 && shapeLoadingDialog != null && shapeLoadingDialog.isShowing()) {
            shapeLoadingDialog.dismiss();
        }
    }

    public boolean dialogIsShow() {
        if (shapeLoadingDialog != null && shapeLoadingDialog.isShowing()) {
            return true;
        }
        return false;
    }

    protected Dialog getProgressDialog(String showWord) {
        if (shapeLoadingDialog == null || !shapeLoadingDialog.isShowing()) {
            shapeLoadingDialog = new ShapeLoadingDialog.Builder(this)
                    .loadText(showWord).canceledOnTouchOutside(false)
                    .build();
            ;
            return shapeLoadingDialog;
        } else {
            return shapeLoadingDialog;
        }
    }

    /**
     * 显示带文字的loading dialog
     */
    public void showLoadWordDialog(String showWord) {
        showLoadWordDialog(showWord, true);
    }

    /**
     * 显示带文字的loading dialog
     */
    public void showLoadWordDialog(String showWord, boolean cancelable) {
        Dialog dialog = getProgressDialog(showWord);
        dialog.setCancelable(cancelable);
        if (!this.isFinishing() && !dialog.isShowing()) {
            dialog.show();
        }
        loadingSemaphore++;
    }

    /**
     * 隐藏返回按钮
     */
    public void hideBackButton() {
        getTvLeftAction().setVisibility(View.GONE);
    }
}
