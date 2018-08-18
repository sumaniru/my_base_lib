package com.lhd.base.main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.lhd.base.R;
import com.lhd.base.http.retrofit.BaseRetrofitApi;
import com.lhd.base.http.retrofit.MyRetrofitRequest;
import com.lhd.base.interfaces.GetContentViewId;
import com.lhd.base.interfaces.PermissionResult;
import com.lhd.base.mvp.BasePresenter;
import com.lhd.base.mvp.BaseView;
import com.lhd.base.utils.ToastTool;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import retrofit2.Retrofit;

/**
 * Created by mac on 17/12/8.
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements GetContentViewId, BaseView {

    protected Activity context;
    protected ProgressDialog progressDialog;
    protected MyRetrofitRequest request;
    protected ImageLoader imageLoader;
    protected DisplayImageOptions options; // 设置图片显示相关参数
    private PermissionResult permissionInterface;
    protected final int PERMISSION_SUCCESS = 1001;
    protected P presenter;
    protected Retrofit retrofit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        request = new MyRetrofitRequest();
        ActivityController.addActivity(this);
        getWindow().setBackgroundDrawable(null);
        presenter = initPresenter();
        retrofit = BaseRetrofitApi.getInstance();
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
    }

    protected Toolbar initToolBar(String title, boolean isBack) {
        return initToolBar(title, isBack, 0);
    }

    protected Toolbar initToolBar(String title, boolean isBack, int bgColor) {
        return initToolBar(title, isBack, bgColor, 0);
    }

    protected Toolbar initToolBar(String title, boolean isBack, int bgColor, int titleColor) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText(title);
        setSupportActionBar(toolbar);
        if (isBack) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationIcon(R.mipmap.icon_back_black);
        }
        if (bgColor != 0) {
            toolbar.setBackgroundColor(getResources().getColor(bgColor));
            setStatusBar(bgColor, false);
        } else {
            toolbar.setBackgroundColor(getResources().getColor(R.color.white));
            setStatusBar(R.color.white, true);
        }
        if (titleColor != 0) {
            tv_title.setTextColor(getResources().getColor(titleColor));
        } else {
            tv_title.setTextColor(getResources().getColor(R.color.black_333));
        }
        return toolbar;
    }


    protected void setPermissionInterface(PermissionResult permissionInterface) {
        this.permissionInterface = permissionInterface;
    }

    protected int checkPermission(int requestCode, String permissions) {
        if (ContextCompat.checkSelfPermission(context, permissions) == PackageManager.PERMISSION_GRANTED) {
            return PERMISSION_SUCCESS;
        } else {
            ActivityCompat.requestPermissions(context, new String[]{permissions}, requestCode);
        }
        return 0;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (permissionInterface != null) {
                permissionInterface.success(requestCode);
            }
        } else {
            Toast.makeText(context, "拒绝权限", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

//    protected void initToolBar() {
//        toolbar = findViewById(R.id.toolbar);
//        tv_title = findViewById(R.id.tv_title);
//        if (toolbar != null) {
//            setSupportActionBar(toolbar);
//            toolbar.setNavigationIcon(R.mipmap.icon_back);
//        }
//    }

    public void setStatusBar(int color, boolean isLight) {
        if (isLight && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //根据上面设置是否对状态栏单独设置颜色
            if (color != -1) {
                getWindow().setStatusBarColor(getResources().getColor(color));
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//android6.0以后可以对状态栏文字颜色和图标进行修改
            if (isLight) {
                //isLight为true  文字为黑色，  isLight为false  文字为白色
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

    /*
   将状态栏字体颜色设为深色
    */
    private void setStatusBarTextColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
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
                .cacheInMemory(false) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(round)) // 设置成圆角图片
                .build(); // 构建完成
    }

    protected abstract P initPresenter();

    /**
     * 显示进度条
     */
    @Override
    public void showProgress() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage("别着急啊喵...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        progressDialog.show();
    }

    /**
     * 隐藏进度条
     */
    @Override
    public void cancelProgress() {
        if (progressDialog != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                }
            }, 300);
        }
    }

    protected void finishRefreshLoad(SmartRefreshLayout smartRefreshLayout) {
        if (smartRefreshLayout!=null){
            smartRefreshLayout.finishRefresh();
            smartRefreshLayout.finishLoadMore();
        }
    }

    @Override
    protected void onDestroy() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        ToastTool.dismiss();
        context = null;
        ActivityController.removeActivity(this);
        super.onDestroy();
    }
}
