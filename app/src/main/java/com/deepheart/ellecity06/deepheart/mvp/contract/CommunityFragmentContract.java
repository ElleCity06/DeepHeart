package com.deepheart.ellecity06.deepheart.mvp.contract;

import com.deepheart.ellecity06.deepheart.base.BaseModel;
import com.deepheart.ellecity06.deepheart.base.BasePresenter;
import com.deepheart.ellecity06.deepheart.base.BaseView;

/**
 * @author ellecity06
 * @time 2018/5/29 11:08
 * @project DeepHeart
 * @packge name：com.deepheart.ellecity06.deepheart.mvp.contract
 * @des TODO
 */

public interface CommunityFragmentContract {
    interface View extends BaseView {

    }

    interface Model extends BaseModel {

    }

    abstract class Presenter extends BasePresenter<View, Model> {

    }

}
