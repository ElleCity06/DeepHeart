package com.deepheart.ellecity06.deepheart.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.deepheart.ellecity06.deepheart.R;
import com.deepheart.ellecity06.deepheart.base.BaseFragment;
import com.deepheart.ellecity06.deepheart.base.BaseView;
import com.deepheart.ellecity06.deepheart.ui.activity.DHMianActivity;
import com.deepheart.ellecity06.deepheart.utils.LogUtil;

import rx.functions.Action1;

/**
 * @author ellecity06
 * @time 2018/5/25 10:25
 * @project DeepHeart
 * @packge name：com.deepheart.ellecity06.deepheart.ui.fragment
 * @des TODO
 */

public class TestFragment2 extends BaseFragment implements DHMianActivity.onRepeatClickListener {

    private DHMianActivity mActivity;

    @Override
    protected int getLayoutId() {
        return R.layout.empty_view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("sdasdasda", "我想看看我创建了几次");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("sdasdasda", "我死了几次呢");
    }

    @Override
    protected void onInitView(Bundle bundle, View rootView) {
        mActivity = (DHMianActivity) getActivity();
        mActivity.setOnRepeatClickListener(this);

    }

    @Override
    protected void onEvent() {
//        if (mRxManager != null) {
//            mRxManager.on("shijian", new Action1<Integer>() {
//
//                @Override
//                public void call(Integer s) {
//                    if (s != null)
//                        LogUtil.d("shijian", s + "我收到事件拉拉");
//                }
//            });
//        }
        mRxManager.on("shijian", new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                LogUtil.d("shijian", integer + "我收到事件拉拉");
            }
        });
    }

    @Override
    protected BaseView getViewImp() {
        return null;
    }

    @Override
    protected void lazyFetchData() {

    }

    @Override
    public void OnRepeatClickListener(int index) {
        Log.d("sadawdwdwdwdw", index + "你好啊");
    }
}
