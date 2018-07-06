package com.deepheart.ellecity06.deepheart.ui.activity;

import android.os.Bundle;
import android.os.Handler;

import com.deepheart.ellecity06.deepheart.R;
import com.deepheart.ellecity06.deepheart.app.Constants;
import com.deepheart.ellecity06.deepheart.base.BaseActivity2;
import com.deepheart.ellecity06.deepheart.base.BaseView;
import com.deepheart.ellecity06.deepheart.utils.XPreferencesUtils;

/**
 * @author ellecity06
 * @time 2018/5/28 15:22
 * @project DeepHeart
 * @packge name：com.deepheart.ellecity06.deepheart.ui.activity
 * @des TODO
 */

public class SplashActivity extends BaseActivity2 {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onInitView(Bundle bundle) {
        hideActionBar();
        //        System.out.println(ConfigUtils.getInstance().getCurUserInfo().getToken()+"Token++");
        if ((Boolean) XPreferencesUtils.get(Constants.SHOW_INTRODUCTION_PAGE, false)) { // 表示已经显示过了
            enterMainDelay();
        } else { // 显示
            //            Handler handler = new Handler();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            startActivity(DHMianActivity.class);
                            finish();
                        }
                    });
                }
            }, 1000);


        }
    }

    private void enterMainDelay() {

    }

    @Override
    protected BaseView getView() {
        return null;
    }
}
