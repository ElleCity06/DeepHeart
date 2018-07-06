package com.deepheart.ellecity06.deepheart.base;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.deepheart.ellecity06.deepheart.R;
import com.deepheart.ellecity06.deepheart.adapter.RecycleViewDivider;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

/**
 * @author ellecity06
 * @time 2018/5/16 16:54
 * @project DeepHeart
 * @packge name：com.deepheart.ellecity06.deepheart.base
 * @des RecyclerViewFragment的基类 ,只需继承，然后提供数据就可以完成
 */

public abstract class BaseRecyclerViewFragment<M extends BaseModel, P extends BasePresenter> extends BaseDHFragment<M, P> implements BaseQuickAdapter.RequestLoadMoreListener {


    public View emptyView;
    public View loadingView;
    private RefreshLayout mRefreshLayout;
    private BezierRadarHeader mBezierRadarHeader;
    public RecyclerView mRecyclerView;
    public BaseQuickAdapter baseQuickAdapter;
    public static final int PAGE_SIZE = 10;
    /**
     * pageNo 默认为 1
     */
    public int mPageNo = 1;

    /**
     * 初始化adpter
     */
    public abstract void initAdapter();

    /**
     * 请求数据
     */
    public abstract void addContentRequest();

    @Override
    protected int getContentViewId() {
        return R.layout.base_recyclerview_fragment;
    }

    @Override
    protected void init(Bundle bundle, View rootView) {
        // 创建状态视图
        emptyView = getActivity().getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) rootView, false); // 空视图
        loadingView = View.inflate(getActivity(), R.layout.loading_view, (ViewGroup) rootView); //加载中的视图
        if (rootView != null) {
            initView(rootView);

        } else {
//            Log.d("SDASDASDASD", "rootview为空了乐乐乐");
        }
    }

    private static boolean isFirstEnter = true; //记录是否是第一次进入

    private void initView(View rootView) {
        mRefreshLayout = (RefreshLayout) rootView.findViewById(R.id.refreshLayout);
        mBezierRadarHeader = (BezierRadarHeader) rootView.findViewById(R.id.header);
        mRecyclerView = rootView.findViewById(R.id.recyclerView);
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
                OnRefresh();
            }
        });
        initBaseRecycleView();
        OnRefresh();
    }

    protected RecyclerView.ItemDecoration mItemDecoration;

    public void initBaseRecycleView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mItemDecoration = new RecycleViewDivider(
                getActivity(), LinearLayoutManager.HORIZONTAL, R.drawable.item_divider);
        //    base_recycler_view.addItemDecoration(mItemDecoration);
        initAdapter();
    }

    /**
     * 下拉刷新中
     */
    public boolean inPullDownProcess;
    /**
     * 上拉加载中
     */
    public boolean inPullUpProcess;

    /**
     * OnRefresh的时候 baseQuickAdapter 已经创建
     */
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

    // 重置状态
    public void reset() {
        inPullDownProcess = false;
        inPullUpProcess = false;
    }

    public BaseQuickAdapter getBaseQuickAdapter() {
        return baseQuickAdapter;
    }

    public void setBaseQuickAdapter(BaseQuickAdapter baseQuickAdapter) {
        this.baseQuickAdapter = baseQuickAdapter;
    }

    @Override
    public void onLoadMoreRequested() {
        mRefreshLayout.setEnableRefresh(false);
        if (baseQuickAdapter.getData().size() < PAGE_SIZE) {
            baseQuickAdapter.loadMoreEnd(true);
        } else {
            inPullUpProcess = true;
            addContentRequest();
        }
        mRefreshLayout.setEnableRefresh(true);
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
}
