package com.deepheart.ellecity06.deepheart.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.deepheart.ellecity06.deepheart.BuildConfig;
import com.deepheart.ellecity06.deepheart.R;
import com.deepheart.ellecity06.deepheart.app.DeepHeartApplication;
import com.deepheart.ellecity06.deepheart.utils.LogUtils;
import com.deepheart.ellecity06.deepheart.utils.ScreenUtil;
import com.deepheart.ellecity06.deepheart.utils.ToastUtils;

/**
 * @author ellecity06
 * @time 2018/5/16 13:43
 * @project DeepHeart
 * @packge name：com.deepheart.ellecity06.deepheart.base
 * @des TODO
 */

public abstract class BaseTitleBarActivity2<M extends BaseModel, P extends BasePresenter> extends BaseActivity2<M, P> {

    public ViewGroup navBarView;
    public ViewGroup navBarLeft;
    public ViewGroup navBarRight;
    public TextView txtTitle;
    public TextView txtTitleLeft;
    public TextView tvNavBarMiddle;
    public TextView txtTitleRight;

    protected abstract void onInit();

    protected abstract int getContentViewId();

    @Override
    protected int getLayoutId() {
        return R.layout.base_activity;
    }

    @Override
    protected void onInitView(Bundle bundle) {
        setupViews();
        try {
            onInit();
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                LogUtils.logStackTrace(e);
                ToastUtils.showBigToast(DeepHeartApplication.getAppContext(), "非常抱歉，发生软件运行错误，我们会尽快解决该问题，请关注版本更新。");
            }
        }

    }


    private void setupViews() {
        ViewGroup   rootView    = (ViewGroup) getLayoutInflater().inflate(R.layout.base_activity, null);
        FrameLayout contentView = (FrameLayout) rootView.findViewById(R.id.container);
        View childView = this.getLayoutInflater().inflate(getContentViewId(), null);
        contentView.addView(childView);

        setContentView(rootView);
        navBarView = (ViewGroup) findViewById(R.id.navbar_title);
        navBarLeft=(ViewGroup) findViewById(R.id.navbar_left);
        navBarRight = (ViewGroup) findViewById(R.id.navbar_right);
        txtTitle = (TextView) findViewById(R.id.tv_navbar_title);
        txtTitleLeft = (TextView) findViewById(R.id.tv_navbar_left);
        tvNavBarMiddle = (TextView) findViewById(R.id.tv_navbar_middle);
        txtTitleRight =(TextView) findViewById(R.id.tv_navbar_right);


    }

    // ==========utils============
    public void startActivity(Class<? extends Activity> clazz) {
        startActivity(new Intent(this, clazz));
    }

//    @SuppressWarnings("unchecked")
//    public <F extends BaseFragment2> F findFragment(int id) {
//        return (F) getSupportFragmentManager().findFragmentById(id);
//    }
//
//    @SuppressWarnings("unchecked")
//    public <F extends BaseFragment2> F findFragment(String tag) {
//        return (F) getSupportFragmentManager().findFragmentByTag(tag);
//    }

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
}
