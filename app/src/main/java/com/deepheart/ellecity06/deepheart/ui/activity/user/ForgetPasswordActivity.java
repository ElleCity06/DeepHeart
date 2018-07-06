package com.deepheart.ellecity06.deepheart.ui.activity.user;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.deepheart.ellecity06.deepheart.R;
import com.deepheart.ellecity06.deepheart.app.DeepHeartApplication;
import com.deepheart.ellecity06.deepheart.base.BaseActivity2;
import com.deepheart.ellecity06.deepheart.base.BaseView;
import com.deepheart.ellecity06.deepheart.utils.LogUtils;
import com.deepheart.ellecity06.deepheart.utils.StringUtils;
import com.deepheart.ellecity06.deepheart.utils.ToastUtils;
import com.deepheart.ellecity06.deepheart.widget.ClearEditText;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.ResetPasswordByCodeListener;

/**
 * @author ellecity06
 * @time 2018/5/31 14:52
 * @project DeepHeart
 * @packge name：com.deepheart.ellecity06.deepheart.ui.activity
 * @des 忘记密码
 */

public class ForgetPasswordActivity extends BaseActivity2 implements ClearEditText.EditFinishListener {
    @BindView(R.id.et_phone_number)
    ClearEditText mEtPhoneNumber;
    @BindView(R.id.btn_get_code)
    TextView mBtnGetCode;
    @BindView(R.id.et_phone_code)
    ClearEditText mEtPhoneCode;
    @BindView(R.id.et_new_password)
    ClearEditText mEtNewPassword;
    @BindView(R.id.btn_go_bind)
    Button mBtnGoBind;
    @BindView(R.id.cardview)
    CardView mCardview;
    @BindView(R.id.fab_back)
    FloatingActionButton mFabBack;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected void onInitView(Bundle bundle) {
        hideActionBar();
        initEvent();

    }

    private void initEvent() {
        mEtPhoneNumber.setOnEditFinishListener(this);
        mEtNewPassword.setOnEditFinishListener(this);
        mEtPhoneCode.setOnEditFinishListener(this);
    }

    @Override
    protected BaseView getView() {
        return null;
    }


    @OnClick({R.id.btn_get_code, R.id.btn_go_bind, R.id.fab_back})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_get_code:
                // 获取验证码。先检查手机号码是否符合规则
                String phoneNumber = mEtPhoneNumber.getText().toString().trim();
                if (!StringUtils.isPhoneNum(phoneNumber)) {
                    ToastUtils.showToast(DeepHeartApplication.getAppContext(), getString(R.string.phone_error));
                    return;
                }
                getCode(phoneNumber);
                break;
            case R.id.btn_go_bind: //设置新的密码
                resetPassword();
                break;
            case R.id.fab_back:
                finish();
                break;
        }
    }

    /**
     * 重置密码
     */
    private void resetPassword() {
        showLoadingDialog();
        String code = mEtPhoneCode.getText().toString().trim();
        String newPassword = mEtNewPassword.getText().toString().trim();
        BmobUser.resetPasswordBySMSCode(DeepHeartApplication.getAppContext(), code, newPassword, new ResetPasswordByCodeListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    hideLoadingDialog();
                    ToastUtils.showToast(DeepHeartApplication.getAppContext(), getString(R.string.reset_psw_success));
                    finish();
                } else {
                    int errorCode = e.getErrorCode();
                    if (errorCode == 207) {
                        ToastUtils.showToast(DeepHeartApplication.getAppContext(), getString(R.string.sms_code_error));
                    } else {
                        ToastUtils.showToast(DeepHeartApplication.getAppContext(), getString(R.string.net_error_reset));
                    }
                }
            }
        });
    }

    /**
     * 网络获取验证码
     *
     * @param phoneNumber
     */
    private void getCode(String phoneNumber) {
        BmobSMS.requestSMSCode(DeepHeartApplication.getAppContext(), phoneNumber, "默认模板", new RequestSMSCodeListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e == null) { //发送成功
                    ToastUtils.showToast(DeepHeartApplication.getAppContext(), getString(R.string.sms_send_success));
                    // 发送成功，让按钮倒计时
                    new CountDownTimer(6000, 1000) {

                        @Override
                        public void onTick(long millisUntilFinished) {
                            mBtnGetCode.setText((millisUntilFinished / 1000) + "秒");
                        }

                        @Override
                        public void onFinish() {
                            mBtnGetCode.setEnabled(true);
                            mBtnGetCode.setText(R.string.sms_send_again);
                        }
                    }.start();
                } else {
                    ToastUtils.showToast(DeepHeartApplication.getAppContext(), getString(R.string.sms_send_error));
                    LogUtils.d("forgetpassword", "短信Id=" + integer + "\r\n异常=" + e.toString());
                }
            }
        });
    }

    @Override
    public void onFinish(Editable s) {
        if (!mEtPhoneNumber.getText().toString().trim().isEmpty() &&
                !mEtPhoneCode.getText().toString().trim().isEmpty() &&
                !mEtNewPassword.getText().toString().trim().isEmpty()) {
            mBtnGoBind.setEnabled(true);
        } else {
            mBtnGoBind.setEnabled(false);
        }
    }
}
