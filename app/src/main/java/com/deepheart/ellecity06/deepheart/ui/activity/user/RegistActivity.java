package com.deepheart.ellecity06.deepheart.ui.activity.user;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.view.View;
import android.widget.Button;

import com.deepheart.ellecity06.deepheart.R;
import com.deepheart.ellecity06.deepheart.app.Constants;
import com.deepheart.ellecity06.deepheart.app.DeepHeartApplication;
import com.deepheart.ellecity06.deepheart.base.BaseActivity2;
import com.deepheart.ellecity06.deepheart.base.BaseView;
import com.deepheart.ellecity06.deepheart.bean.MyUser;
import com.deepheart.ellecity06.deepheart.utils.LogUtil;
import com.deepheart.ellecity06.deepheart.utils.ToastUtils;
import com.deepheart.ellecity06.deepheart.widget.ClearEditText;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.listener.SaveListener;

/**
 * @author ellecity06
 * @time 2018/5/30 11:34
 * @project DeepHeart
 * @packge name：com.deepheart.ellecity06.deepheart.ui.activity
 * @des 注册界面
 */

public class RegistActivity extends BaseActivity2 implements ClearEditText.EditFinishListener {
    @BindView(R.id.et_username)
    ClearEditText mEtUsername;
    @BindView(R.id.et_password)
    ClearEditText mEtPassword;
    @BindView(R.id.et_repeatpassword)
    ClearEditText mEtRepeatpassword;
    @BindView(R.id.bt_regist)
    Button mBtRegist;
    @BindView(R.id.cardview)
    CardView mCardview;
    @BindView(R.id.fab_back)
    FloatingActionButton mFabBack;
    private String mPhoneNumber;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_regist;
    }

    @Override
    protected void onInitView(Bundle bundle) {
        mPhoneNumber = getIntent().getStringExtra(Constants.PHONE_NUMBER);
        hideActionBar();
        initEvent();
    }

    private void initEvent() {
        mEtUsername.setOnEditFinishListener(this);
        mEtPassword.setOnEditFinishListener(this);
        mEtRepeatpassword.setOnEditFinishListener(this);
    }

    @Override
    protected BaseView getView() {
        return null;
    }


    @OnClick({R.id.bt_regist, R.id.fab_back})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.bt_regist: // 下一步
                next();
                break;
            case R.id.fab_back:
                break;
        }
    }

    private void next() {

        String userName = mEtUsername.getText().toString().trim();
        String password = mEtPassword.getText().toString().trim();
        String password_repeat = mEtRepeatpassword.getText().toString().trim();
        if (password.length() < 6) {
            ToastUtils.showToast(DeepHeartApplication.getAppContext(), getString(R.string.regist_psw_toshort));
            return;
        }
        if (password != password_repeat) {
            ToastUtils.showToast(DeepHeartApplication.getAppContext(), getString(R.string.psw_not_equal));
            return;
        }
        showLoadingDialog();
        MyUser myUser = new MyUser();
        myUser.setUsername(userName);
        myUser.setPassword(password);
        if (mPhoneNumber != null) {

            myUser.setMobilePhoneNumber(mPhoneNumber);
            myUser.setMobilePhoneNumberVerified(true); //说明手机号已经经过验证了
        }
        myUser.signUp(getApplication(), new SaveListener() {
                    @Override
                    public void onSuccess() {
                        hideLoadingDialog();
                        // 注册成功，跳转下一步绑定银行卡
                        ToastUtils.showToast(DeepHeartApplication.getAppContext(), getString(R.string.regist_success));
                        startActivity(LoginActivty.class);
                        finish();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        hideLoadingDialog();
                        ToastUtils.showToast(DeepHeartApplication.getAppContext(), s);
                        LogUtil.d("regist", "注册失败原因：" + s);
                    }
                }
        );

    }

    @Override
    public void onFinish(Editable s) {
        // 当输入框为空的时候不允许点击
        if (!mEtUsername.getText()
                .toString()
                .trim()
                .isEmpty() && !mEtPassword.getText()
                .toString()
                .trim()
                .isEmpty() && !mEtRepeatpassword.getText().toString().trim().isEmpty()) {
            mBtRegist.setEnabled(true);
        } else {
            mBtRegist.setEnabled(false);
        }
    }
}
