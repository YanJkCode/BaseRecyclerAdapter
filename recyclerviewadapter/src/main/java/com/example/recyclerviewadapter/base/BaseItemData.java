package com.example.recyclerviewadapter.base;

public abstract class BaseItemData implements BaseItemType {
    private int type;

    public BaseItemData(int type) {
        this.type = type;
    }

    @Override
    public int getItemType(int position) {
        return type;
    }

    public void setItemType(int type) {
        this.type = type;
    }

}
