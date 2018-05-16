package com.deepheart.ellecity06.deepheart.base;

import android.content.Context;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * @author ellecity06
 * @time 2018/5/9 9:26
 * @des TODO presenter的基类 ，
 * 1.获取绑定View实例传递到子类中进行调用!
 * <p>
 * 2.注销View实例
 * <p>
 * 3.创建 Model 实例
 * <p>
 * 4.注销Model实例
 * <p>
 * 5.通过RxJava进行绑定activity和fragment生命周期绑定
 */
public class BasePresenter<V extends BaseView, M extends BaseModel> implements Presenter<V, M> {

    protected Context mContext;

    protected V mView; // View的实例

    protected M mModel; //Model的实例

    protected CompositeSubscription mCompositeSubscription;

    protected void unSubscribe() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }

    protected void addSubscribe(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    // 获取绑定View实例
    @Override
    public void attachView(V view) {
        this.mView = view;
    }

    // 取绑定Model层实例
    @Override
    public void attachModel(M m) {
        this.mModel = m;
    }

    public M getModel() {
        return mModel;
    }

    // 注销mModel实例
    @Override
    public void detachModel() {
        this.mModel = null;
    }

    // 注销View实例
    @Override
    public void detachView() {
        this.mView = null;
        unSubscribe();
    }

    public V getView() {
        return mView;
    }

    public boolean isViewBind() {
        return mView != null;
    }
}
