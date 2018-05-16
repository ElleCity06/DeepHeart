package com.deepheart.ellecity06.deepheart.net.transformer;


import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author ellecity06
 * @time 2018/5/9 9:57
 * @des 预处理异常信息
 */
public class DefaultTransformer<T> implements Observable.Transformer<T, T> {
    @Override
    public Observable<T> call(Observable<T> httpResponseObservable) {
        return httpResponseObservable.subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }
}
