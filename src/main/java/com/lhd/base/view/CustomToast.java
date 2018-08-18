package com.lhd.base.view;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lhd.base.R;
import com.lhd.base.utils.DeviceUtils;

public class CustomToast extends Toast {

    private TextView tv;
    private Context context;

    /**
     * Construct an empty Toast object.  You must call {@link #setView} before you
     * can call {@link #show}.
     *
     * @param context The context to use.  Usually your {@link Application}
     *                or {@link Activity} object.
     */
    public CustomToast(Context context, boolean isRight) {
        super(context);
        this.context = context;
        initTextView(isRight);
    }

    private void initTextView(boolean isRight) {
        int widthPadding = DeviceUtils.dip2px(context, 12);
        int heightPadding = DeviceUtils.dip2px(context, 6);
        tv = new TextView(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tv.setPadding(widthPadding, heightPadding, widthPadding, heightPadding);
        tv.setLayoutParams(params);
        if (isRight) {
            tv.setBackgroundResource(R.drawable.theme_color_dp5);
        } else {
            tv.setBackgroundResource(R.drawable.red_dp5);
        }
        tv.setGravity(Gravity.CENTER);
        tv.setMinWidth(DeviceUtils.dip2px(context, 100));
        tv.setTextSize(14);
        tv.setTextColor(Color.WHITE);
        setView(tv);
    }

    @Override
    public void setText(CharSequence s) {
        tv.setText(s);
    }
}
