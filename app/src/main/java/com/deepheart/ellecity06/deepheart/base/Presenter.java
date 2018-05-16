package com.deepheart.ellecity06.deepheart.base;

/**
 * @author ellecity06
 * @time 2018/5/9 9:29
 * @des TODO presenter接口，提供绑定和注销view 与model对象
 */
public interface Presenter<View, Model> {
    //  绑定View控件
    void attachView(View view);

    // 绑定Model
    void attachModel(Model model);

    // 注销View控件
    void detachView();

    // 注销Model对象
    void detachModel();
}
