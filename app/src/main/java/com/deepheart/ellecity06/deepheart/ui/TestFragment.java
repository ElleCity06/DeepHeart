package com.deepheart.ellecity06.deepheart.ui;

import android.view.View;

import com.deepheart.ellecity06.deepheart.R;
import com.deepheart.ellecity06.deepheart.adapter.TestAdapter;
import com.deepheart.ellecity06.deepheart.base.BaseRecyclerViewFragment2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ellecity06
 * @time 2018/5/17 11:02
 * @project DeepHeart
 * @packge name：com.deepheart.ellecity06.deepheart.ui
 * @des 测试fragment
 */

public class TestFragment extends BaseRecyclerViewFragment2 {
    List<String> mStrings;
    private View mInflate;



/*    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInflate = inflater.inflate(R.layout.test_item, container, false);
        return mInflate;
    }*/

    @Override
    public void initAdapter() {
        baseQuickAdapter = new TestAdapter(R.layout.test_item, mStrings);
        mRecyclerView.setAdapter(baseQuickAdapter);
    }

    @Override
    public void addContentRequest() {
        mStrings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {

            mStrings.add("西红柿炒鸡蛋");
        }
        commonProcess(mStrings);
    }



}
