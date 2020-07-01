package com.example.recyclerviewadapter.base;

public interface BaseItemType{
    /**
     * 用于判断当前条目的类型获取对应的布局
     *
     * @param position
     */
    int getItemType(int position);
}
