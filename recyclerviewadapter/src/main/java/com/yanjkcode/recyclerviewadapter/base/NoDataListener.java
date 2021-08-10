package com.yanjkcode.recyclerviewadapter.base;

@NotProguard
public interface NoDataListener {
    /**
     * 没有数据时的布局
     *
     * @return 布局ID
     */
    int getNoDataLayoutId();

    /**
     * 没有数据时显示的数据
     *
     * @param holder           当前布局的VH
     * @param noDataBackground 图片ID
     * @param noDataText       文本
     */
    void setNoDataLayout(ViewHolder holder, int noDataBackground, String noDataText);
}