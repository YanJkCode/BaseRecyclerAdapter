package com.example.baserecycleradapter.adapter;

import androidx.annotation.NonNull;

import com.example.baserecycleradapter.R;
import com.example.recyclerviewadapter.base.BaseRecyclerAdapter;
import com.example.recyclerviewadapter.base.BaseRecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

public class EasyListAdapter extends BaseRecyclerAdapter<String> {
    public EasyListAdapter(List<String> data) {
        super(R.layout.item_easy, data);
    }

    @Override
    protected void setItemViewData(@NonNull BaseRecyclerViewHolder holder, String s, int itemViewType) {
        holder.setText(R.id.tv, s);
    }
}
