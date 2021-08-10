package com.yanjkcode.baserecycleradapter.adapter;

import androidx.annotation.NonNull;

import com.yanjkcode.baserecycleradapter.R;
import com.yanjkcode.recyclerviewadapter.base.BaseRecyclerAdapter;
import com.yanjkcode.recyclerviewadapter.base.ViewHolder;

import java.util.List;

public class EasyListAdapter extends BaseRecyclerAdapter<String> {
    public EasyListAdapter() {
        super(R.layout.item_easy);
    }

    @Override
    protected void setItemViewData(@NonNull ViewHolder holder, String data, int type) {
        holder.setText(R.id.tv, data);
    }
}
