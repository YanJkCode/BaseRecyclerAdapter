package com.yanjkcode.recyclerviewadapter.base;

@NotProguard
public interface LoadNoDataListener {
    /**
     * 没有更多数据时的布局
     *
     * @return 布局ID
     */
    int getLoadNoDataLayoutId();

    /**
     * 加载更多没有数据时显示
     *
     * @param holder         当前布局的VH
     * @param loadNoDataText 数据
     */
    void setLoadNoDataLayout(ViewHolder holder, String loadNoDataText);
}

