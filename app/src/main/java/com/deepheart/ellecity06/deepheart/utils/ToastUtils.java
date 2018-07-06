package com.deepheart.ellecity06.deepheart.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.deepheart.ellecity06.deepheart.R;


/**
 * Toast的工具类
 */

public class ToastUtils {

    public static Toast sToast;

    /**
     * @param context
     * @param msg     短时间的toast
     */
    public static void showToast(Context context, String msg) {

        View layout = LayoutInflater.from(context).inflate(R.layout.view_toast, null);
        TextView toastText = (TextView) layout.findViewById(R.id.toast_text);

        if (sToast == null) {
            sToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        }

        toastText.setText(msg);
        sToast.setView(layout);
        sToast.setGravity(Gravity.CENTER, 0, 0);
        sToast.setDuration(Toast.LENGTH_SHORT);
        sToast.show();
    }

    /**
     * @param context
     * @param msg     长时间的toast
     */
    public static void showLongToast(Context context, String msg) {

        View layout = LayoutInflater.from(context).inflate(R.layout.view_toast, null);
        TextView toastText = (TextView) layout.findViewById(R.id.toast_text);

        if (sToast == null) {
            sToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        }

        toastText.setText(msg);
        sToast.setView(layout);
        sToast.setGravity(Gravity.CENTER, 0, 0);
        sToast.setDuration(Toast.LENGTH_LONG);
        sToast.show();
    }

    /**
     * @param context
     * @param msg     弹在下方的toast
     */
    public static void showBottomToast(Context context, String msg) {

        View layout = LayoutInflater.from(context).inflate(R.layout.view_toast, null);
        TextView toastText = (TextView) layout.findViewById(R.id.toast_text);

        if (sToast == null) {
            sToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        }

        toastText.setText(msg);
        sToast.setView(layout);
        //        sToast.setGravity(Gravity.CENTER, 0, 0);
        sToast.setDuration(Toast.LENGTH_SHORT);
        sToast.show();
    }

    public static void showBigToast(Context context, String msg) {
        View layout = LayoutInflater.from(context).inflate(R.layout.view_big_toast, null);
        TextView toastText = (TextView) layout.findViewById(R.id.toast_text);

        if (sToast == null) {
            sToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        }

        toastText.setText(msg);
        sToast.setView(layout);
        sToast.setGravity(Gravity.CENTER, 0, 0);
        sToast.setDuration(Toast.LENGTH_SHORT);
        sToast.show();
    }
}
