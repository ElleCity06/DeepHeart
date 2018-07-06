package com.deepheart.ellecity06.deepheart.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.deepheart.ellecity06.deepheart.R;
import com.deepheart.ellecity06.deepheart.base.BaseFragment;
import com.deepheart.ellecity06.deepheart.base.BaseView;
import com.deepheart.ellecity06.deepheart.mvp.contract.CommunityFragmentContract;
import com.deepheart.ellecity06.deepheart.mvp.model.CommunityFragmentModel;
import com.deepheart.ellecity06.deepheart.mvp.presenter.CommunityFragmentPresenter;

/**
 * @author ellecity06
 * @time 2018/5/25 18:02
 * @project DeepHeart
 * @packge name：com.deepheart.ellecity06.deepheart.ui.fragment
 * @des 社区界面
 */

public class CommunityFragment extends BaseFragment<CommunityFragmentModel, CommunityFragmentPresenter> implements CommunityFragmentContract.View {
    @Override
    public void showErrorWithStatus(String msg) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_community;
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
}
