package com.lhd.base.interfaces;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by arutoria on 2017/7/11.
 */

public interface RecyclerViewGetLayout {
    RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType);
}
