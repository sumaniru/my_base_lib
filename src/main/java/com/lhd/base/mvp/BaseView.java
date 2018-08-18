package com.lhd.base.mvp;

public interface BaseView<P extends BasePresenter> {

    void showProgress();

    void cancelProgress();

}
