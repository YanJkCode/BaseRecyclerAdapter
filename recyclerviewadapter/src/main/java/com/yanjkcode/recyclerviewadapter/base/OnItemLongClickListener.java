package com.yanjkcode.recyclerviewadapter.base;

import android.view.View;

/**
 * 长按点击事件的接口
 */
@NotProguard
public interface OnItemLongClickListener<T> {
    boolean onItemLongClick(BaseRecyclerAdapter<T> adapter, ViewHolder holder, View view, int position);
}
