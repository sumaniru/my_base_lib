package com.lhd.base.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.SwipeLoadMoreTrigger;
import com.aspsine.swipetoloadlayout.SwipeTrigger;

/**
 * Created by Administrator on 2017/1/16.
 */

@SuppressLint("AppCompatCustomView")
public class CustomLoadMoreFooterView extends TextView implements SwipeTrigger,SwipeLoadMoreTrigger {


    public CustomLoadMoreFooterView(Context context) {
        super(context);
    }

    public CustomLoadMoreFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onLoadMore() {
        setText("正在加载中");
    }

    @Override
    public void onPrepare() {
        setText("");
    }

    @Override
    public void onMove(int yScrolled, boolean isComplete, boolean automatic) {
        if (!isComplete) {
            if (yScrolled <= -getHeight()) {
                setText("释放加载更多");
            } else {
                setText("上拉加载更多");
            }
        } else {
            setText("释放取消加载更多");
        }
    }

    @Override
    public void onRelease() {
        setText("正在加载更多");
    }

    @Override
    public void onComplete() {
        setText("加载完成");
    }

    @Override
    public void onReset() {
        setText("");
    }
}
