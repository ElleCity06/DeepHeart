package com.deepheart.ellecity06.deepheart.widget.dialog;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import java.io.Serializable;

public class MyDialogFragment extends BaseDialogFragment implements Serializable {
    private ViewConvertListener convertListener;
    public static MyDialogFragment mMyDialogFragment;

    public static MyDialogFragment init() {
        if (mMyDialogFragment == null) {
            mMyDialogFragment = new MyDialogFragment();
        }
        return mMyDialogFragment;
    }

    @Override
    public int intLayoutId() {
        return layoutId;

    }

    @Override
    public void convertView(ViewHolder holder, BaseDialogFragment dialog) {
        if (convertListener != null) {
            convertListener.convertView(holder, dialog);
        }
    }


    public MyDialogFragment setLayoutId(@LayoutRes int layoutId) {
        this.layoutId = layoutId;
        return this;
    }

    public MyDialogFragment setConvertListener(ViewConvertListener convertListener) {
        this.convertListener = convertListener;
        return this;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            convertListener = (ViewConvertListener) savedInstanceState.getParcelable("listener");
        }
    }

    /**
     * 保存接口
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("listener", convertListener);
    }
}
