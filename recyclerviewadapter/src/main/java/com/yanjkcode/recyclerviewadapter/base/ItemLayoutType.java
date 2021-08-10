package com.yanjkcode.recyclerviewadapter.base;

import androidx.annotation.LayoutRes;

@NotProguard
public class ItemLayoutType {
    private int layoutID;
    private int layoutType;

    public ItemLayoutType(@LayoutRes int layoutID, int layoutType) {
        this.layoutID = layoutID;
        this.layoutType = layoutType;
    }

    public int getLayoutID() {
        return layoutID;
    }

    public void setLayoutID(@LayoutRes int layoutID) {
        this.layoutID = layoutID;
    }

    public int getLayoutType() {
        return layoutType;
    }

    public void setLayoutType(int layoutType) {
        this.layoutType = layoutType;
    }
}
