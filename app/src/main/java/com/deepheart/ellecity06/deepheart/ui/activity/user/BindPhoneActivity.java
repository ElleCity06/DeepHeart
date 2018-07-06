package com.deepheart.ellecity06.deepheart.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.deepheart.ellecity06.deepheart.R;
import com.deepheart.ellecity06.deepheart.app.Constants;
import com.deepheart.ellecity06.deepheart.app.DeepHeartApplication;
import com.deepheart.ellecity06.deepheart.base.BaseActivity2;
import com.deepheart.ellecity06.deepheart.base.BaseView;
import com.deepheart.ellecity06.deepheart.bean.MyUser;
import com.deepheart.ellecity06.deepheart.utils.SerializableMap;
import com.deepheart.ellecity06.deepheart.utils.StringUtils;
import com.deepheart.ellecity06.deepheart.utils.ToastUtils;
import com.deepheart.ellecity06.deepheart.widget.ClearEditText;

import org.json.JSONObject;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.OtherLoginListener;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;

/**
 * @author ellecity06
 * @time 2018/5/31 14:02
 * @project DeepHeart
 * @packge name：com.deepheart.ellecity06.deepheart.ui.activity
 * @des 绑定手机号码
 */

public class BindPhoneActivity extends BaseActivity2 implements ClearEditText.EditFinishListener {
    @BindView(R.id.et_phone_number)
    ClearEditText mEtPhoneNumber;
    @BindView(R.id.btn_get_code)
    TextView mBtnGetCode;
    @BindView(R.id.et_phone_code)
    ClearEditText mEtPhoneCode;
    @BindView(R.id.btn_go_bind)
    Button mBtnGoBind;
    @BindView(R.id.cardview)
    CardView mCardview;
    @BindView(R.id.fab_back)
    FloatingActionButton mFabBack;
    private String mLoginType;
    private SerializableMap mSerializableMap;
    private int mBind_type;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bindphone;
    }

    @Override
    protected void onInitView(Bundle bundle) {
        mLoginType = getIntent().getStringExtra(Constants.LOGIN_TYPE);
        mBind_type = getIntent().getIntExtra(Constants.BIND_PHONE_TYPE, 0);
        mSerializableMap = (SerializableMap) getIntent().getSerializableExtra(Constants.LOGIN_DATA);
        if (mBind_type == 1) { //属于第三方登录之后的绑定手机
            //属于三方登录
            mBtnGoBind.setText("绑定");
        }
        //        Log.d("sdasdawdadaksdka", "类别==" + mLoginType + "\r\n 数据集合==" + mSerializableMap.getMap().toString());
        hideActionBar();
        initEvent();
    }

    private void initEvent() {
        mEtPhoneNumber.setOnEditFinishListener(this);
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
                // 点击获取验证码，为了节省我们的验证码次数，先对手机号码进行校验
                String phoneNumber = mEtPhoneNumber.getText().toString().trim();
                if (!StringUtils.isPhoneNum(phoneNumber)) {
                    ToastUtils.showToast(DeepHeartApplication.getAppContext(), getString(R.string.phone_error));
                    return;
                }
                getCode(phoneNumber);
                break;
            case R.id.btn_go_bind: // 验证验证码是否正确
                verifyCode();

                break;
            case R.id.fab_back:
                finish();
                break;
        }
    }

    /**
     * 校验验证码
     */
    private void verifyCode() {
        showLoadingDialog();
        String phoneCode = mEtPhoneCode.getText().toString().trim();
        final String phonenumber = mEtPhoneNumber.getText().toString().trim();
        BmobSMS.verifySmsCode(DeepHeartApplication.getAppContext(), phonenumber, phoneCode, new VerifySMSCodeListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) { //没有异常，验证码正确
                    if (mBind_type == 1) { //属于第三方登录之后的绑定手机
                        updateUserInfo();

                    } else {
                        hideLoadingDialog();
                        Intent intent = new Intent(BindPhoneActivity.this, BindPhoneActivity.class);
                        intent.putExtra(Constants.PHONE_NUMBER, phonenumber);
                        startActivity(intent);
                    }
                } else {
                    hideLoadingDialog();
                    ToastUtils.showToast(DeepHeartApplication.getAppContext(), getString(R.string.sms_code_error));
                }
            }
        });
    }

    /**
     * 更新用户信息，比如 手机号
     */
    private void updateUserInfo() {
        MyUser myUser = new MyUser();
        BmobUser currentUser = BmobUser.getCurrentUser(DeepHeartApplication.getAppContext());
        myUser.setMobilePhoneNumber(mEtPhoneNumber.getText().toString().trim());
        myUser.setMobilePhoneNumberVerified(true);
        myUser.update(DeepHeartApplication.getAppContext(), currentUser.getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {
                hideLoadingDialog();
                BindPhoneActivity.this.finish();
                ToastUtils.showToast(DeepHeartApplication.getAppContext(), "手机号码绑定成功");
            }

            @Override
            public void onFailure(int i, String s) {
                hideLoadingDialog();
                ToastUtils.showToast(DeepHeartApplication.getAppContext(), "网络好像是出了一点小问题，请稍候重试");
            }
        });
    }

    /**
     * 第三方授权成功之后进项帐号的绑定
     */
    private void thirdPartyLogin() {
        final Map<String, String> map = mSerializableMap.getMap();
        if (mLoginType.equals(Constants.TYPE_WEIXIN)) {

        } else if (mLoginType.equals(Constants.TYPE_QQ)) {
            BmobUser.BmobThirdUserAuth authInfo = new BmobUser.BmobThirdUserAuth(BmobUser.BmobThirdUserAuth.SNS_TYPE_QQ, map.get("accessToken"), map.get("expires_in"), map.get("openid"));
            BmobUser.loginWithAuthData(DeepHeartApplication.getAppContext(), authInfo, new OtherLoginListener() {
                @Override
                public void onSuccess(JSONObject jsonObject) {
                    ToastUtils.showToast(DeepHeartApplication.getAppContext(), "成功啦啦");
                    MyUser myUser = new MyUser();
                    BmobUser currentUser = BmobUser.getCurrentUser(DeepHeartApplication.getAppContext());
                    myUser.setUsername(map.get("screen_name"));
                    myUser.setMobilePhoneNumber(mEtPhoneNumber.getText().toString().trim());
                    myUser.setMobilePhoneNumberVerified(true);
                    myUser.update(DeepHeartApplication.getAppContext(), currentUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void onSuccess() {
                            hideLoadingDialog();
                            BindPhoneActivity.this.finish();
                            ToastUtils.showToast(DeepHeartApplication.getAppContext(), "更新用户数据成功啦啦");
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            hideLoadingDialog();
                            ToastUtils.showToast(DeepHeartApplication.getAppContext(), "网络好像是出了一点小问题，请稍候重试");
                        }
                    });
                }

                @Override
                public void onFailure(int i, String s) {
                    hideLoadingDialog();
                    ToastUtils.showToast(DeepHeartApplication.getAppContext(), "登录失败了，请重试");
                }
            });

        } else if (mLoginType.equals(Constants.TYPE_WEIBO)) {

        }
    }

    /**
     * 网络获取验证码
     *
     * @param phoneNumber
     */
    private void getCode(String phoneNumber) {
        BmobSMS.requestSMSCode(DeepHeartApplication.getAppContext(), phoneNumber, "短信模板", new RequestSMSCodeListener() {
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
                }
            }
        });
    }

    @Override
    public void onFinish(Editable s) {
        if (!mEtPhoneNumber.getText().toString().trim().isEmpty() && !mEtPhoneCode.getText().toString().trim().isEmpty()) {
            mBtnGoBind.setEnabled(true);
        } else {
            mBtnGoBind.setEnabled(false);
        }
    }
}
