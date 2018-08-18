package com.lhd.base.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lhd.base.R;
import com.lhd.base.http.retrofit.BaseRetrofitApi;
import com.lhd.base.http.retrofit.MyRetrofitRequest;
import com.lhd.base.http.retrofit.exception.ApiException;
import com.lhd.base.http.volley.MyNetRequest;
import com.lhd.base.interfaces.GetContentViewId;
import com.lhd.base.interfaces.RetrofitResponse;
import com.lhd.base.mvp.BasePresenter;
import com.lhd.base.mvp.BaseView;
import com.lhd.base.utils.ToastTool;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by mac on 17/12/9.
 */

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements GetContentViewId, BaseView {

    protected Activity context;
    protected ProgressDialog progressDialog;
    protected MyRetrofitRequest request;
    protected ImageLoader imageLoader;
    protected DisplayImageOptions options; // 设置图片显示相关参数
    protected View view;
    protected P presenter;
    protected Retrofit retrofit;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        request = new MyRetrofitRequest();
        presenter = initPresenter();
        retrofit = BaseRetrofitApi.getInstance();
    }

    protected abstract P initPresenter();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null && getLayoutId() != 0) {
            view = inflater.inflate(getLayoutId(), container, false);
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        ToastTool.dismiss();
        if (null != view) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
        super.onDestroyView();
    }

    /**
     * 显示进度条
     */
    @Override
    public void showProgress() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage("别着急啊喵...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        progressDialog.show();
    }

    protected void finishRefreshLoad(SmartRefreshLayout smartRefreshLayout) {
        if (smartRefreshLayout!=null){
            smartRefreshLayout.finishRefresh();
            smartRefreshLayout.finishLoadMore();
        }
    }

    /**
     * 隐藏进度条
     */

    @Override
    public void cancelProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    // imageLoader初始化
    protected void InitImageLoader(int round) {
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.icon_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.icon_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.icon_error) // 设置图片加载或解码过程中发生错误显示的图片
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT) // 图片缩放方式
                .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(round)) // 设置成圆角图片
                .build(); // 构建完成
    }

    protected Toolbar initToolBar(String title) {
        return initToolBar(title, 0);
    }

    protected Toolbar initToolBar(String title, int bgColor) {
        return initToolBar(title, bgColor, 0);
    }

    protected Toolbar initToolBar(String title, int bgColor, int titleColor) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        TextView tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText(title);
        if (bgColor != 0) {
            toolbar.setBackgroundColor(getResources().getColor(bgColor));
        } else {
            toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        }
        if (titleColor != 0) {
            tv_title.setTextColor(getResources().getColor(titleColor));
        } else {
            tv_title.setTextColor(getResources().getColor(R.color.black_333));
        }
        return toolbar;
    }

}
