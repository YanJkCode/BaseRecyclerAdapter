package com.yanjkcode.recyclerviewadapter.base;

@NotProguard
public interface OnEmptyListener {
    /**
     * 没有数据时的布局
     *
     * @return 布局ID
     */
    int getEmptyLayoutId();

    /**
     * 没有数据时显示的数据
     *
     * @param holder           当前布局的VH
     * @param emptyBackground 图片ID
     * @param emptyText       文本
     */
    void setEmptyView(ViewHolder holder, int emptyBackground, String emptyText);
}