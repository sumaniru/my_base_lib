package com.lhd.base.http.retrofit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.gson.Gson;
import com.lhd.base.R;
import com.lhd.base.http.retrofit.exception.ApiException;
import com.lhd.base.interfaces.AdapterRetrofitResponse;
import com.lhd.base.interfaces.RetrofitResponse;
import com.lhd.base.main.BaseApplication;
import com.lhd.base.main.BaseBean;
import com.lhd.base.utils.ACache;
import com.lhd.base.utils.DeviceUtils;
import com.lhd.base.utils.ToastTool;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class MyRetrofitRequest {

    @SuppressLint("CheckResult")
    public void request(final int id, Observable<? extends BaseBean> observable, final RetrofitResponse response) {
        if (DeviceUtils.isNetworkAvailable(BaseApplication.getContext())) {
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).filter(new Predicate<BaseBean>() {
                @Override
                public boolean test(BaseBean baseBean) {
                    if (baseBean.getCode() == 2)
                        showTokenError();
                    if (baseBean.getCode() == 1) {
                        return true;
                    } else {
                        if (id == R.string.checkReg) {
                            return true;
                        }
                        throw new ApiException(baseBean.getMsg());
                    }
                }
            }).subscribe(new Consumer<BaseBean>() {
                @Override
                public void accept(BaseBean bean) {
                    Logger.d(BaseApplication.getContext().getResources().getString(id) + "-->" + new Gson().toJson(bean));
                    response.success(id, bean);
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) {
                    Logger.e(BaseApplication.getContext().getResources().getString(id) + "error-->" + throwable.getMessage());
                    response.error(id, throwable);
                }
            });
        } else {
            ToastTool.showErrorToast("请检测当前网络是否可用");
        }
    }

    @SuppressLint("CheckResult")
    public void request(final int id, Observable<? extends BaseBean> observable, final int position, final AdapterRetrofitResponse response) {
        if (DeviceUtils.isNetworkAvailable(BaseApplication.getContext())) {
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).filter(new Predicate<BaseBean>() {
                @Override
                public boolean test(BaseBean baseBean) {
                    if (baseBean.getCode() == 1 || baseBean.getCode() == 2) {
                        return true;
                    } else {
                        if (id == R.string.checkReg) {
                            return true;
                        }
                        throw new ApiException(baseBean.getMsg());
                    }
                }
            }).subscribe(new Consumer<BaseBean>() {
                @Override
                public void accept(BaseBean bean) {
                    Logger.d(BaseApplication.getContext().getResources().getString(id) + "-->" + new Gson().toJson(bean));
                    response.success(id, bean, position);
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) {
                    Logger.e(BaseApplication.getContext().getResources().getString(id) + "error-->" + throwable.getMessage());
                    response.error(id, throwable, position);
                }
            });
        } else {
            ToastTool.showErrorToast("请检测当前网络是否可用");
        }
    }

    private void showTokenError() {
        Activity activity = getCurrentActivity();
        if (activity!=null){
            new AlertDialog.Builder(activity)
                    .setMessage("您的账号在别处登录,请重新登录")
                    .setCancelable(false)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            exit();
                            toLogin();
                        }
                    })
                    .show();
        }
    }

    private Activity getCurrentActivity() {
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(
                    null);
            Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
            activitiesField.setAccessible(true);
            Map activities = (Map) activitiesField.get(activityThread);
            for (Object activityRecord : activities.values()) {
                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);
                if (!pausedField.getBoolean(activityRecord)) {
                    Field activityField = activityRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    Activity activity = (Activity) activityField.get(activityRecord);
                    return activity;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void exit() {
        ACache cache = ACache.get(BaseApplication.getContext());
        try {
            cache.put("exit", "1");
            cache.remove("user_id");
            cache.remove("user_name");
            cache.remove("nick_name");
            cache.remove("mobile");
            cache.remove("user_token");
            cache.remove("source");
            cache.remove("is_authentic");
            cache.remove("u_pic");
            cache.getAsString("user_id");
            cache.getAsString("user_name");
            cache.getAsString("nick_name");
            cache.getAsString("mobile");
            cache.getAsString("user_token");
            cache.getAsString("source");
            cache.getAsString("is_authentic");
            cache.getAsString("u_pic");
        } catch (Exception e) {
            Log.d("bbbbb", "exit-->" + e.getMessage());
            e.printStackTrace();
        }
    }

    private void toLogin() {
        ComponentName componetName = new ComponentName(
                //这个是另外一个应用程序的包名
                "com.zhijian.wuma",
                //这个参数是要启动的Activity
                "com.zhijian.wuma.login.activity.LoginActivity");
        Intent intent = new Intent();
        intent.setComponent(componetName);
        BaseApplication.getContext().startActivity(intent);
    }

}
