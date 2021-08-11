package com.yanjkcode.recyclerviewadapter.base;

@NotProguard
public interface ItemType {
    /**
     * 用于判断当前条目的类型获取对应的布局
     *
     * @param position 下标
     * @return 所属类型
     */
    int getItemType(int position);
}
