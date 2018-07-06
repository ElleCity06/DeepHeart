package com.deepheart.ellecity06.deepheart;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.deepheart.ellecity06.deepheart.adapter.RecycleViewDivider;
import com.deepheart.ellecity06.deepheart.adapter.TestAdapter;
import com.deepheart.ellecity06.deepheart.base.BaseActivity2;
import com.deepheart.ellecity06.deepheart.base.BaseView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class Test2Activity2 extends BaseActivity2 implements BaseQuickAdapter.RequestLoadMoreListener {


    public View emptyView;
    public View loadingView;
    private RefreshLayout mRefreshLayout;
    private BezierRadarHeader mBezierRadarHeader;
    public RecyclerView mRecyclerView;
    public BaseQuickAdapter baseQuickAdapter;
    public static final int PAGE_SIZE = 10;
    public int mPageNo = 1;
    List<String> mStrings;
    /**
     * 下拉刷新中
     */
    public boolean inPullDownProcess;
    /**
     * 上拉加载中
     */
    public boolean inPullUpProcess;

    @Override
    protected int getLayoutId() {
        return R.layout.base_recyclerview_fragment;
    }

    @Override
    protected void onInitView(Bundle bundle) {
        // 创建状态视图
        emptyView = getLayoutInflater().inflate(R.layout.empty_view, null, false); // 空视图
        loadingView = getLayoutInflater().inflate(R.layout.loading_view, null, false); //加载中的视图
        initView();

    }

    private void initView() {
        mRefreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        mBezierRadarHeader = (BezierRadarHeader) findViewById(R.id.header);
        mRecyclerView = findViewById(R.id.recyclerView);
        //        if (isFirstEnter) {
        //            isFirstEnter = false;
        //            mRefreshLayout.autoRefresh();//第一次进入触发自动刷新
        //        }
        mRefreshLayout.setEnableHeaderTranslationContent(true); //内容随header迁移
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mPageNo = 1;
                inPullDownProcess = true;
                //                showLoadingDialog();
                OnRefresh();
            }
        });
        initBaseRecycleView();
        OnRefresh();
    }

    protected RecyclerView.ItemDecoration mItemDecoration;

    public void initBaseRecycleView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(Test2Activity2.this));
        mItemDecoration = new RecycleViewDivider(
                Test2Activity2.this, LinearLayoutManager.HORIZONTAL, R.drawable.item_divider);
        //    base_recycler_view.addItemDecoration(mItemDecoration);
        initAdapter();
    }

    private void initAdapter() {
        baseQuickAdapter = new TestAdapter(R.layout.test_item, mStrings);
        mRecyclerView.setAdapter(baseQuickAdapter);
    }

    private void addContentRequest() {
        mStrings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {

            mStrings.add("西红柿炒鸡蛋");
        }
        commonProcess(mStrings);
    }

    public void OnRefresh() {
        if (baseQuickAdapter != null) {
            baseQuickAdapter.setEnableLoadMore(false);
            baseQuickAdapter.setOnLoadMoreListener(this, mRecyclerView);

            if (inPullDownProcess) {

                addContentRequest();

                return;
            }

            baseQuickAdapter.setEmptyView(loadingView);
            addContentRequest();
        }
    }


    @Override
    protected BaseView getView() {
        return null;
    }


    @Override
    public void onLoadMoreRequested() {
        mRefreshLayout.setEnableRefresh(false);
        if (baseQuickAdapter.getData().size() < PAGE_SIZE) {
            baseQuickAdapter.loadMoreEnd(true);
        } else {
            inPullUpProcess = true;

            addContentRequest1();
        }
        mRefreshLayout.setEnableRefresh(true);
    }

    private void addContentRequest1() {
        mStrings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {

            mStrings.add("鸡蛋吵番茄");
        }
        commonProcess(mStrings);
    }

    /**
     * 对返回结果做统一处理
     *
     * @param list
     */
    public void commonProcess(List<?> list) {
        if (inPullDownProcess) {//下拉
            inPullDownProcess = false;
            if (list != null && list.size() > 0) {
                baseQuickAdapter.setNewData(list);
                if (list.size() < PAGE_SIZE) {
                    baseQuickAdapter.setEnableLoadMore(false);
                } else {
                    baseQuickAdapter.setEnableLoadMore(true);
                }
            } else {
                baseQuickAdapter.setNewData(null);//不清空原先数据 不显示空视图
                baseQuickAdapter.setEmptyView(emptyView);
            }
            stopRefresh();
            return;
        }
        if (inPullUpProcess) {//上拉
            inPullUpProcess = false;
            if (list != null && list.size() > 0) {
                baseQuickAdapter.addData(list);
                baseQuickAdapter.loadMoreComplete();
                baseQuickAdapter.setEnableLoadMore(true);
            } else {
                baseQuickAdapter.loadMoreEnd(false);
                baseQuickAdapter.setEnableLoadMore(false);
            }
            return;
        }

        if (list != null && list.size() > 0) {//正常加载
            baseQuickAdapter.addData(list);
            if (list.size() < PAGE_SIZE) {

                baseQuickAdapter.setEnableLoadMore(false);
            } else {

                baseQuickAdapter.setEnableLoadMore(true);
            }
        } else {
            baseQuickAdapter.setEmptyView(emptyView);
        }
    }

    /**
     * 停止下拉刷新
     */
    public void stopRefresh() {
        if (mRefreshLayout != null) {
            mRefreshLayout.finishRefresh();
        }
        //        reset();
    }
}
