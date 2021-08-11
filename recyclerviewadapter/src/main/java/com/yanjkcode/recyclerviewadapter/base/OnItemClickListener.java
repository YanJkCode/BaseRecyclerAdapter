package com.yanjkcode.recyclerviewadapter.base;

import android.view.View;

/**
 * 条目点击回调接口
 */
@NotProguard
public interface OnItemClickListener<T> {
    void onItemClick(BaseRecyclerAdapter<T> adapter, ViewHolder holder, View view, int position);
}
