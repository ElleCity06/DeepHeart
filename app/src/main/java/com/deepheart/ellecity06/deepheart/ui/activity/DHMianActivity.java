package com.deepheart.ellecity06.deepheart.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.deepheart.ellecity06.deepheart.R;
import com.deepheart.ellecity06.deepheart.adapter.BaseFragmentAdapter;
import com.deepheart.ellecity06.deepheart.app.DeepHeartApplication;
import com.deepheart.ellecity06.deepheart.base.BaseActivity2;
import com.deepheart.ellecity06.deepheart.base.BaseView;
import com.deepheart.ellecity06.deepheart.base.RxBus;
import com.deepheart.ellecity06.deepheart.listener.DialogListener;
import com.deepheart.ellecity06.deepheart.mvp.model.MainActivityModel;
import com.deepheart.ellecity06.deepheart.mvp.presenter.MianActivityPresenter;
import com.deepheart.ellecity06.deepheart.ui.fragment.TestFragment2;
import com.deepheart.ellecity06.deepheart.utils.DialogUtils;
import com.deepheart.ellecity06.deepheart.utils.ToastUtils;
import com.deepheart.ellecity06.deepheart.utils.UMUtils;
import com.deepheart.ellecity06.deepheart.widget.CustomProgressDialog;
import com.deepheart.ellecity06.deepheart.widget.NoScrollViewPager;
import com.deepheart.ellecity06.deepheart.widget.tabbar.BaseTabItem;
import com.deepheart.ellecity06.deepheart.widget.tabbar.NavigationController;
import com.deepheart.ellecity06.deepheart.widget.tabbar.NormalItemView;
import com.deepheart.ellecity06.deepheart.widget.tabbar.OnTabItemSelectedListener;
import com.deepheart.ellecity06.deepheart.widget.tabbar.PageNavigationView;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobUser;

/**
 * @author ellecity06
 * @time 2018/5/25 11:13
 * @project DeepHeart
 * @packge name：com.deepheart.ellecity06.deepheart.ui.activity
 * @des 🐖界面
 */

public class DHMianActivity extends BaseActivity2<MainActivityModel, MianActivityPresenter> implements BaseView {
    @BindView(R.id.main_viewpager)
    NoScrollViewPager mMainViewpager;
    @BindView(R.id.bottom_tabbar)
    PageNavigationView mBottomTabbar;
    private CustomProgressDialog mCustomProgressDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onInitView(Bundle bundle) {
        //隐藏返回键
        hideBackButton();
        //        setNavBarViewStatusHeightPadding(true);
        mCustomProgressDialog = new CustomProgressDialog(this);
        initView();


    }

    private void initView() {
        //        NavigationController controller = mBottomTabbar.custom().addItem(newItem(R.drawable.ic_restore_gray_24dp, R.drawable.ic_restore_teal_24dp, getString(R.string.main_tab_home)))
        //                .addItem(newItem(R.drawable.ic_favorite_gray_24dp, R.drawable.ic_favorite_teal_24dp, getString(R.string.main_tab_motif)))
        //                .addItem(newItem(R.drawable.ic_nearby_gray_24dp, R.drawable.ic_nearby_teal_24dp, getString(R.string.main_tab_community)))
        //                .addItem(newItem(R.drawable.ic_nearby_gray_24dp, R.drawable.ic_nearby_teal_24dp, getString(R.string.main_tab_mine)))
        //                .build();
        NavigationController controller = mBottomTabbar.material().addItem(R.drawable.ic_restore_gray_24dp, R.drawable.ic_restore_teal_24dp, getString(R.string.main_tab_home))
                .addItem(R.drawable.ic_favorite_gray_24dp, R.drawable.ic_favorite_teal_24dp, getString(R.string.main_tab_motif))
                .addItem(R.drawable.ic_nearby_gray_24dp, R.drawable.ic_nearby_teal_24dp, getString(R.string.main_tab_community))
                .addItem(R.drawable.ic_nearby_gray_24dp, R.drawable.ic_nearby_teal_24dp, getString(R.string.main_tab_mine)).build();
        controller.setupWithViewPager(mMainViewpager);
        initAdapter();
        controller.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                Log.d("sdaasd", "index==" + index + "\r\nold==" + old);
                setFragmentTitle(index);
            }

            @Override
            public void onRepeat(int index) { //重复点击，当重复点击的是时候我们可以刷新数据，类式头条
                // 接口回调给相应界面
                Log.d("sdaasd", "onRepeat index==" + index);
                listener.OnRepeatClickListener(index);
                RxBus.getInstance().post("shijian", index);
                //                mRxManager.post("shijian", index);
                DialogUtils instance = DialogUtils.getInstance();
                instance.showSelectLoginDialog(DHMianActivity.this, getSupportFragmentManager());
                instance.setOnSelectLoginClickListener(new DialogListener.OnSelectLoginClickListener() {
                    @Override
                    public void onSelectClickLestener(View view) {
                        Dialog progressDialog = getProgressDialog("");
                        switch (view.getId()) {
                            case R.id.ll_weixin: // 微信
                                ToastUtils.showToast(DeepHeartApplication.getAppContext(), "微信微信微信微信微信微信微信微信微信微信微信");
                                UMUtils.loginForThirdParty(SHARE_MEDIA.WEIXIN, DHMianActivity.this, progressDialog, BmobUser.BmobThirdUserAuth.SNS_TYPE_WEIXIN);
                                break;
                            case R.id.ll_qq: //qq
                                ToastUtils.showBottomToast(DeepHeartApplication.getAppContext(), "qq啦");
                                UMUtils.loginForThirdParty(SHARE_MEDIA.QQ, DHMianActivity.this, progressDialog, BmobUser.BmobThirdUserAuth.SNS_TYPE_QQ);
                                break;
                            case R.id.ll_weibo: //微博
                                //                                ToastUtils.showToast(DeepHeartApplication.getAppContext(), "微博");
                                Toast.makeText(DeepHeartApplication.getAppContext(), "微博啦", Toast.LENGTH_LONG).show();
                                UMUtils.loginForThirdParty(SHARE_MEDIA.SINA, DHMianActivity.this, progressDialog, BmobUser.BmobThirdUserAuth.SNS_TYPE_WEIBO);
                                break;

                        }
                    }
                });

            }
        });

    }

    private void setFragmentTitle(int index) {
        switch (index) {
            case 0:
                setTitle(getString(R.string.main_tab_home));
                break;
            case 1:
                setTitle(getString(R.string.main_tab_motif));
                break;
            case 2:
                setTitle(getString(R.string.main_tab_community));
                break;
            case 3:
                setTitle(getString(R.string.main_tab_mine));
                break;
        }
    }

    private void initAdapter() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new TestFragment2());
        fragmentList.add(new TestFragment2());
        fragmentList.add(new TestFragment2());
        fragmentList.add(new TestFragment2());
        mMainViewpager.setAdapter(new BaseFragmentAdapter(getSupportFragmentManager(), fragmentList));
    }

    //创建一个Item
    private BaseTabItem newItem(int drawable, int checkedDrawable, String text) {
        NormalItemView normalItemView = new NormalItemView(this);
        normalItemView.initialize(drawable, checkedDrawable, text);
        normalItemView.setTextDefaultColor(Color.GRAY);
        normalItemView.setTextCheckedColor(0xFF009688);
        return normalItemView;
    }

    @Override
    protected BaseView getView() {
        return this;
    }

    @Override
    public void showErrorWithStatus(String msg) {

    }

    public interface onRepeatClickListener {
        void OnRepeatClickListener(int index);
    }

    /**
     * 定义一个变量储存数据
     */
    private onRepeatClickListener listener;

    /**
     * 提供公共的方法,并且初始化接口类型的数据
     */
    public void setOnRepeatClickListener(onRepeatClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        UMShareAPI.get(this).onSaveInstanceState(outState);
    }
}
