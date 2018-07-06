package com.deepheart.ellecity06.deepheart.base;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.deepheart.ellecity06.deepheart.R;
import com.deepheart.ellecity06.deepheart.utils.ContractProxy;
import com.trello.rxlifecycle.components.support.RxFragment;

/**
 * @author ellecity06
 * @time 2018/5/17 14:46
 * @project DeepHeart
 * @packge name：com.deepheart.ellecity06.deepheart.base
 * @des TODO
 */

public abstract class BaseDHFragment<M extends BaseModel, P extends BasePresenter> extends RxFragment {
    // RxBus管理类
    public RxManager mRxManager;

    private Handler handler;
    // 定义Presenter
    protected P mPresenter;
    private View root;
    //    private final String PAGE_NAME;

    public BaseDHFragment() {
        //        Class<? extends BaseDHFragment> clazz = getClass();
        //        PageName pageName = clazz.getAnnotation(PageName.class);
        //        if (pageName != null) {
        //            PAGE_NAME = pageName.value();
        //        } else {
        //            PAGE_NAME = clazz.getSimpleName();
        //        }
    }

    // 获取抽取View对象
    protected abstract BaseView getViewImp();

    // 获得抽取接口Model对象
    protected Class getModelClazz() {
        return (Class<M>) ContractProxy.getModelClazz(getClass(), 0);
    }

    // 获得抽取接口Presenter对象
    protected Class getPresenterClazz() {
        return (Class<P>) ContractProxy.getPresnterClazz(getClass(), 1);
    }

    private void bindVM() {
        if (mPresenter != null && !mPresenter.isViewBind() && getModelClazz() != null && getViewImp() != null) {
            ContractProxy.getInstance().bindModel(getModelClazz(), mPresenter);
            ContractProxy.getInstance().bindView(getViewImp(), mPresenter);
            mPresenter.mContext = getActivity();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            ContractProxy.getInstance().unbindView(getViewImp(), mPresenter);
            ContractProxy.getInstance().unbindModel(getModelClazz(), mPresenter);
        }

        if (mRxManager != null) {
            mRxManager.clear();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        handler = new Handler();
        root = inflater.inflate(R.layout.base_fragment, container, false);
        FrameLayout frameLayout = root.findViewById(R.id.rootContainer);
//        View childView = inflater.inflate(getContentViewId(), null);
        //        ViewGroup parent = (ViewGroup) childView.getParent();
        //        if (parent != null) {
        //            parent.removeView(childView);
        //
        //        }
        //        frameLayout.addView(childView);
        inflater.inflate(this.getContentViewId(), (ViewGroup) findView(root, R.id.rootContainer), true);

        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        init(savedInstanceState, root);

    }

    protected abstract void init(Bundle savedInstanceState, View root);

    protected abstract int getContentViewId();

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @SuppressWarnings("unchecked")
    protected <F extends BaseDHFragment> F findChildFragment(int id) {
        return (F) getChildFragmentManager().findFragmentById(id);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @SuppressWarnings("unchecked")
    protected <F extends BaseDHFragment> F findChildFragment(String tag) {
        return (F) getChildFragmentManager().findFragmentByTag(tag);
    }

    @SuppressWarnings("unchecked")
    protected <V extends View> V findView(int id) {
        return (V) root.findViewById(id);
    }

    @SuppressWarnings("unchecked")
    protected <V extends View> V findView(View parent, int id) {
        return (V) parent.findViewById(id);
    }
}
