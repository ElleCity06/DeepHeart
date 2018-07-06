package com.deepheart.ellecity06.deepheart.ui.activity.user;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.deepheart.ellecity06.deepheart.R;
import com.deepheart.ellecity06.deepheart.app.Constants;
import com.deepheart.ellecity06.deepheart.app.DeepHeartApplication;
import com.deepheart.ellecity06.deepheart.base.BaseActivity2;
import com.deepheart.ellecity06.deepheart.base.BaseView;
import com.deepheart.ellecity06.deepheart.base.RxBus;
import com.deepheart.ellecity06.deepheart.bean.MyUser;
import com.deepheart.ellecity06.deepheart.listener.DialogListener;
import com.deepheart.ellecity06.deepheart.mvp.contract.LoginActivityContract;
import com.deepheart.ellecity06.deepheart.mvp.model.LoginActivityModel;
import com.deepheart.ellecity06.deepheart.mvp.presenter.LoginActivityPresenter;
import com.deepheart.ellecity06.deepheart.utils.DialogUtils;
import com.deepheart.ellecity06.deepheart.utils.LogUtil;
import com.deepheart.ellecity06.deepheart.utils.ToastUtils;
import com.deepheart.ellecity06.deepheart.utils.UMUtils;
import com.deepheart.ellecity06.deepheart.widget.ClearEditText;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

/**
 * @author ellecity06
 * @time 2018/5/29 16:53
 * @project DeepHeart
 * @packge name：com.deepheart.ellecity06.deepheart.ui.activity
 * @des 登陆界面
 */

public class LoginActivty extends BaseActivity2<LoginActivityModel, LoginActivityPresenter> implements LoginActivityContract.View, ClearEditText.EditFinishListener {
    @BindView(R.id.iv_login_delete)
    ImageView mIvLoginDelete;
    @BindView(R.id.et_username)
    ClearEditText mEtUsername;
    @BindView(R.id.et_password)
    ClearEditText mEtPassword;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.tv_forget_psw)
    TextView mTvForgetPsw;
    @BindView(R.id.tv_other)
    TextView mTvOther;
    @BindView(R.id.cardview)
    CardView mCardview;
    @BindView(R.id.fab_regist)
    FloatingActionButton mFabRegist;

    @Override
    public void showErrorWithStatus(String msg) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onInitView(Bundle bundle) {
        hideActionBar(); // 隐藏标题栏
        initEvent();


    }

    private void initEvent() {
        mEtUsername.setOnEditFinishListener(this);
        mEtPassword.setOnEditFinishListener(this);
    }

    @Override
    protected BaseView getView() {
        return this;
    }

    @OnClick({R.id.iv_login_delete, R.id.btn_login, R.id.tv_forget_psw, R.id.tv_other, R.id.fab_regist})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_login_delete: //返回
                finish();
                break;
            case R.id.btn_login: //登录
                toLogin();
                break;
            case R.id.tv_forget_psw: // 忘记密码
                startActivity(ForgetPasswordActivity.class);
                break;
            case R.id.tv_other: // 第三方登录
                // TODO 从底下弹出让用户选择第三方登录，暂时提供微信，qq，微博
                DialogUtils instance = DialogUtils.getInstance();
                instance.showSelectLoginDialog(this, getSupportFragmentManager());
                instance.setOnSelectLoginClickListener(new DialogListener.OnSelectLoginClickListener() {
                    @Override
                    public void onSelectClickLestener(View view) {
                        Dialog progressDialog = getProgressDialog("");
                        switch (view.getId()) {
                            case R.id.ll_weixin: // 微信
                                ToastUtils.showToast(DeepHeartApplication.getAppContext(), "微信微信微信微信微信微信微信微信微信微信微信");
                                UMUtils.loginForThirdParty(SHARE_MEDIA.WEIXIN, LoginActivty.this, progressDialog, BmobUser.BmobThirdUserAuth.SNS_TYPE_WEIXIN);
                                break;
                            case R.id.ll_qq: //qq
                                ToastUtils.showBottomToast(DeepHeartApplication.getAppContext(), "qq啦");
                                UMUtils.loginForThirdParty(SHARE_MEDIA.QQ, LoginActivty.this, progressDialog, BmobUser.BmobThirdUserAuth.SNS_TYPE_QQ);
                                break;
                            case R.id.ll_weibo: //微博
                                //                                ToastUtils.showToast(DeepHeartApplication.getAppContext(), "微博");
                                Toast.makeText(DeepHeartApplication.getAppContext(), "微博啦", Toast.LENGTH_LONG).show();
                                UMUtils.loginForThirdParty(SHARE_MEDIA.SINA, LoginActivty.this, progressDialog, BmobUser.BmobThirdUserAuth.SNS_TYPE_WEIBO);
                                break;

                        }
                    }
                });
                break;
            case R.id.fab_regist: // 去注册啦
                getWindow().setExitTransition(null);
                getWindow().setEnterTransition(null);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(this, mFabRegist, mFabRegist.getTransitionName());
                    startActivity(new Intent(this, BindPhoneActivity.class), options.toBundle());
                } else {
                    startActivity(new Intent(this, BindPhoneActivity.class));
                }
                break;
        }
    }


    private void toLogin() {
        showLoadingDialog();
        MyUser myUser = new MyUser();
        // 登录的操作
        String userName = mEtUsername.getText().toString().trim();
        String password = mEtPassword.getText().toString().trim();
        BmobUser.loginByAccount(DeepHeartApplication.getAppContext(), userName, password, new LogInListener<MyUser>() {
            @Override
            public void done(MyUser myUser, BmobException e) {
                hideLoadingDialog();
                if (e == null) { //没有异常，登录成功
                    //登录成功发送通知界面更新
                    RxBus.getInstance().post(Constants.LOGIN_SUCCESS, 1);
                    ToastUtils.showToast(DeepHeartApplication.getAppContext(), "登录成功");
                } else {
                    LogUtil.d("login", e.toString());
                    ToastUtils.showToast(DeepHeartApplication.getAppContext(), "帐号密码错误请检查");
                }
            }
        });
    }

    // 当输入完成的回调
    @Override
    public void onFinish(Editable s) {
        // 当输入框为空的时候不允许点击
        if (!mEtUsername.getText()
                .toString()
                .trim()
                .isEmpty() && !mEtPassword.getText()
                .toString()
                .trim()
                .isEmpty()) {
            mBtnLogin.setEnabled(true);
        } else {
            mBtnLogin.setEnabled(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        UMShareAPI.get(this).onSaveInstanceState(outState);
    }
}
