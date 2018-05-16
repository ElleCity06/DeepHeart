package com.deepheart.ellecity06.deepheart.base;

/**
 * @author ellecity06
 * @time 2018/5/9 9:28
 * @des TODO View的基类，只定义了一个提示错误消息的方法，
 */

public interface BaseView<T> {
    //    提示错误消息
    void showErrorWithStatus(String msg);

}
