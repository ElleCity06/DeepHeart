package com.deepheart.ellecity06.deepheart.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.deepheart.ellecity06.deepheart.R;
import com.deepheart.ellecity06.deepheart.base.BaseFragment;
import com.deepheart.ellecity06.deepheart.base.BaseView;
import com.deepheart.ellecity06.deepheart.mvp.contract.MotifFragmentContract;
import com.deepheart.ellecity06.deepheart.mvp.model.MotifFragmentModel;
import com.deepheart.ellecity06.deepheart.mvp.presenter.MotifFragmentPresenter;

/**
 * @author ellecity06
 * @time 2018/5/25 18:03
 * @project DeepHeart
 * @packge name：com.deepheart.ellecity06.deepheart.ui.fragment
 * @des 主题界面
 */

public class MotifFragment extends BaseFragment<MotifFragmentModel,MotifFragmentPresenter> implements MotifFragmentContract.View{
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_motif;
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
