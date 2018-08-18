package com.lhd.base.mvp;

import android.content.Context;

import com.lhd.base.http.retrofit.BaseRetrofitApi;
import com.lhd.base.http.retrofit.MyRetrofitRequest;
import com.lhd.base.main.BaseApplication;

import java.lang.ref.WeakReference;
import java.util.Map;

import retrofit2.Retrofit;

public abstract class BasePresenterImpl<V extends BaseView> implements BasePresenter {

    protected V view;
    private final WeakReference<V> vWeakReference;
    protected MyRetrofitRequest request;
    protected Context context = BaseApplication.getContext();
    protected Retrofit retrofit;
    protected Map<String,String> params;

    public BasePresenterImpl(BaseView view) {
        vWeakReference = (WeakReference<V>) new WeakReference<>(view);
        this.view = vWeakReference.get();
        request = new MyRetrofitRequest();
        retrofit = BaseRetrofitApi.getInstance();
    }

    @Override
    public void doNetWorkById(int id) {

    }
}
