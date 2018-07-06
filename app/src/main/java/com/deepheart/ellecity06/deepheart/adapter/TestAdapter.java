package com.deepheart.ellecity06.deepheart.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.deepheart.ellecity06.deepheart.R;

import java.util.List;

/**
 * @author ellecity06
 * @time 2018/5/17 11:45
 * @project DeepHeart
 * @packge name：com.deepheart.ellecity06.deepheart.adapter
 * @des TODO
 */

public class TestAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public TestAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.list_item_text, item);
    }
}
