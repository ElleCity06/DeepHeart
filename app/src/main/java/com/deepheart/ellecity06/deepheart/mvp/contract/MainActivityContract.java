package com.deepheart.ellecity06.deepheart.mvp.contract;

import com.deepheart.ellecity06.deepheart.base.BaseModel;
import com.deepheart.ellecity06.deepheart.base.BasePresenter;
import com.deepheart.ellecity06.deepheart.base.BaseView;

/**
 * @author ellecity06
 * @time 2018/5/25 11:26
 * @project DeepHeart
 * @packge name：com.deepheart.ellecity06.deepheart.mvp.contract
 * @des 主界面 mvp的约束接口
 */

public interface MainActivityContract {
    interface View extends BaseView {


    }
    interface  Model extends BaseModel {


    }
    abstract class Presenter extends BasePresenter<View,Model> {

    }
}
