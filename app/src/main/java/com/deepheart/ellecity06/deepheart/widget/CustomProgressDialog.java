package com.deepheart.ellecity06.deepheart.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.deepheart.ellecity06.deepheart.R;

/**
 * 自定义Loading动画，用于网络请求时
 */

public class CustomProgressDialog extends Dialog {

    private static CustomProgressDialog sDialog = null;

    public CustomProgressDialog(Context context) {
        super(context);
    }

    public CustomProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    /**
     * 当窗口焦点改变时
     *
     * @param hasFocus
     */
    public void onWindowFocusChanged(boolean hasFocus) {
        if (sDialog != null) {
            ImageView loadingPicture = (ImageView) sDialog.findViewById(R.id.loading_picture);
            // 获取动画背景
            AnimationDrawable spinner = (AnimationDrawable) loadingPicture.getBackground();
            // 开始动画
            spinner.start();
        }
    }

    /**
     * 设置提示信息
     */
    public CustomProgressDialog setMessage(String message) {
        TextView loadingText = (TextView) sDialog.findViewById(R.id.loading_text);
        loadingText.setVisibility(View.VISIBLE);
        if (loadingText != null) {
            loadingText.setText(message);
        }
        return sDialog;
    }

    public static CustomProgressDialog creatDialog(Context context) {
        sDialog = new CustomProgressDialog(context, R.style.progress_dialog);
        sDialog.setContentView(R.layout.progressdialog);

        // 设置对话框是否能被返回键取消
        sDialog.setCancelable(false);
        // 设置居中
        sDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        sDialog.onStart();

        return sDialog;
    }

}
