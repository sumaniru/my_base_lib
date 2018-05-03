package com.lhd.base.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.SwipeRefreshTrigger;
import com.aspsine.swipetoloadlayout.SwipeTrigger;

/**
 * Created by arutoria on 2017/1/13.
 */

@SuppressLint("AppCompatCustomView")
public class CustomRefreshHeadView extends TextView implements SwipeTrigger, SwipeRefreshTrigger {
    public CustomRefreshHeadView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRefreshHeadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public CustomRefreshHeadView(Context context) {
        super(context);
    }

    @Override
    public void onRefresh() {
        setText("正在刷新");
    }

    @Override
    public void onPrepare() {
        setText("");
    }

    @Override
    public void onMove(int yScrolled, boolean isComplete, boolean automatic) {
        if (!isComplete) {
            if (yScrolled >= getHeight()) {
                setText("释放刷新");
            } else {
                setText("下拉刷新");
            }
        } else {
            setText("释放取消刷新");
        }
    }

    @Override
    public void onRelease() {
    }

    @Override
    public void onComplete() {
        setText("刷新完成");
    }

    @Override
    public void onReset() {
        setText("");
    }
}
