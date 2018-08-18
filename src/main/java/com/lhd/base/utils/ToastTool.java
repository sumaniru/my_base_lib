package com.lhd.base.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lhd.base.R;
import com.lhd.base.main.BaseApplication;
import com.lhd.base.view.CustomToast;

public class ToastTool {
    private static String oldMsg;
    protected static Toast toast = null;
    private static long oneTime = 0;
    private static long twoTime = 0;

    public static void showToast(Context context, String s) {
        if (toast == null) {
            toast = Toast.makeText(context, s, Toast.LENGTH_SHORT);
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (s.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    toast.show();
                }
            } else {
                oldMsg = s;
                toast.setText(s);
                toast.show();
            }
        }
        oneTime = twoTime;
    }

    public static void showToast(Context context, int resId) {
        showToast(context, context.getString(resId));
    }

    public static void showErrorToast(String s) {
        showErrorToast(BaseApplication.getContext(), s);
    }

    public static void showErrorToast(Context context, String s) {
        if (toast == null) {
            toast = new CustomToast(context, false);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 0, DeviceUtils.dip2px(context, 60));
            toast.setText(s);
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (s.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_LONG) {
                    toast.show();
                }
            } else {
                oldMsg = s;
                toast.setText(s);
                toast.show();
            }
        }
        oneTime = twoTime;
    }

    public static void showRightToast(String s) {
        showRightToast(BaseApplication.getContext(), s);
    }

    public static void showRightToast(Context context, String s) {
        if (toast == null) {
            toast = new CustomToast(context, true);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 0, DeviceUtils.dip2px(context, 60));
            toast.setText(s);
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (s.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_LONG) {
                    toast.show();
                }
            } else {
                oldMsg = s;
                toast.setText(s);
                toast.show();
            }
        }
        oneTime = twoTime;
    }

    public static void dismiss() {
        if (toast != null) {
            toast.cancel();
        }
    }

}
