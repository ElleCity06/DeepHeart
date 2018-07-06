package com.deepheart.ellecity06.deepheart.utils;

import android.app.Activity;
import android.os.Parcel;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.deepheart.ellecity06.deepheart.R;
import com.deepheart.ellecity06.deepheart.listener.DialogListener;
import com.deepheart.ellecity06.deepheart.widget.dialog.BaseDialogFragment;
import com.deepheart.ellecity06.deepheart.widget.dialog.MyDialogFragment;
import com.deepheart.ellecity06.deepheart.widget.dialog.ViewConvertListener;
import com.deepheart.ellecity06.deepheart.widget.dialog.ViewHolder;

/**
 * @author ellecity06
 * @time 2018/6/1 15:01
 * @project DeepHeart
 * @packge name：com.deepheart.ellecity06.deepheart.utils
 * @des 用于管理dialog的弹框处理
 */

public class DialogUtils {
    private DialogListener.OnSelectLoginClickListener mOnSelectLoginClickListener = null;

    private DialogUtils() {
    }

    public static DialogUtils getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * 静态内部类,只有在装载该内部类时才会去创建单例对象
     */
    private static class SingletonHolder {
        private static final DialogUtils instance = new DialogUtils();
    }

    public void showSelectLoginDialog(Activity activity, FragmentManager fragmentManager) {
        MyDialogFragment.init().setLayoutId(R.layout.fragment_sharedialog)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final BaseDialogFragment dialog) {
                        holder.setOnClickListener(R.id.ll_weixin, new myClickListener(dialog));
                        holder.setOnClickListener(R.id.ll_qq, new myClickListener(dialog));
                        holder.setOnClickListener(R.id.ll_weibo, new myClickListener(dialog));
                        //                        holder.setOnClickListener(R.id.ll_weixin, new View.OnClickListener() { // 选择微信
                        //                            @Override
                        //                            public void onClick(View v) {
                        //                                mOnSelectLoginClickListener.onSelectClickLestener(v);
                        //                                dialog.dismiss();
                        //                            }
                        //                        });
                        //                        holder.setOnClickListener(R.id.ll_qq, new View.OnClickListener() { // 选择qq
                        //                            @Override
                        //                            public void onClick(View v) {
                        //                                mOnSelectLoginClickListener.onSelectClickLestener(v);
                        //                                dialog.dismiss();
                        //                            }
                        //                        });
                        //                        holder.setOnClickListener(R.id.ll_weibo, new View.OnClickListener() { // 选择微博
                        //                            @Override
                        //                            public void onClick(View v) {
                        //                                mOnSelectLoginClickListener.onSelectClickLestener(v);
                        //                                dialog.dismiss();
                        //                            }
                        //                        });

                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {

                    }
                }).setShowBottom(true).setDimAmount(0.1f) //底部显示
                .setOutCancel(true) // 点击外部取消
                .show(fragmentManager);
    }
    /*==============--============^^==============我是一条可爱的分割线==========^^==============--===============*/

    public class myClickListener implements View.OnClickListener {
        private BaseDialogFragment mBaseDialogFragment;

        public myClickListener(BaseDialogFragment dialog) {
            mBaseDialogFragment = dialog;
        }

        @Override
        public void onClick(View v) {
            mOnSelectLoginClickListener.onSelectClickLestener(v);
            mBaseDialogFragment.dismiss();
        }
    }

    /*==============--============^^==============我是一条可爱的分割线==========^^==============--===============*/
    public void setOnSelectLoginClickListener(DialogListener.OnSelectLoginClickListener listener) {
        this.mOnSelectLoginClickListener = listener;
    }
}
