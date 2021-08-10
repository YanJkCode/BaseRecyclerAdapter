package com.yanjkcode.recyclerviewadapter.base;

@NotProguard
public abstract class ItemData implements ItemType {
    private int type;

    public ItemData(int type) {
        this.type = type;
    }

    @Override
    public int getItemType(int position) {
        return type;
    }
}
