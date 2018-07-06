package com.deepheart.ellecity06.deepheart.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.deepheart.ellecity06.deepheart.R;
import com.deepheart.ellecity06.deepheart.base.BaseFragment;
import com.deepheart.ellecity06.deepheart.base.BaseView;
import com.deepheart.ellecity06.deepheart.mvp.model.HomeFragmentModel;
import com.deepheart.ellecity06.deepheart.mvp.presenter.HomeFragmentPresenter;

/**
 * @author ellecity06
 * @time 2018/5/25 18:01
 * @project DeepHeart
 * @packge name：com.deepheart.ellecity06.deepheart.ui.fragment
 * @des 首页界面
 */

public class HomeFragment extends BaseFragment<HomeFragmentModel,HomeFragmentPresenter> implements BaseView {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void onInitView(Bundle bundle, View rootView) {

    }

    @Override
    protected void onEvent() {

    }

    @Override
    protected BaseView getViewImp() {
        return this;
    }

    @Override
    protected void lazyFetchData() {

    }

    @Override
    public void showErrorWithStatus(String msg) {

    }
}
