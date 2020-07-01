package com.example.baserecycleradapter.adapter;

import androidx.annotation.NonNull;

import com.example.baserecycleradapter.R;
import com.example.baserecycleradapter.entity.TestBean;
import com.example.recyclerviewadapter.base.BaseItemViewType;
import com.example.recyclerviewadapter.base.BaseRecyclerAdapter;
import com.example.recyclerviewadapter.base.BaseRecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

public class TestAdapter extends BaseRecyclerAdapter<TestBean> {
    public TestAdapter(List<TestBean> data) {
        super(data);
        //直接在构造中添加布局文件
        //ArrayList<BaseItemViewType> itemViewDatas = new ArrayList<>();
        //itemViewDatas.add(new BaseItemViewType(R.layout.item_layout_test, 0));
        //itemViewDatas.add(new BaseItemViewType(R.layout.item_layout_test1, 1));
        //itemViewDatas.add(new BaseItemViewType(R.layout.item_layout_test2, 2));
        //addLayouts(itemViewDatas);

        //也可以直接调用
        //addLayout(R.layout.item_layout_test, 0);
        //addLayout(R.layout.item_layout_test1, 1);
        //addLayout(R.layout.item_layout_test2, 2);
    }

    /**
     * 单个条目 用的构造
     *
     * @param layoutId 条目的id
     * @param data     数据可空 之后调用addNewData() 或 setNewData()都可以加载数据
     */
    public TestAdapter(int layoutId, List<TestBean> data) {
        super(layoutId, data);
        //也可以直接传入布局
        //super(R.layout.item_layout_test, data);
    }

    /**
     * 多个条目 用的构造
     *
     * @param itemViewDatas 条目数组其中包括布局ID和布局ID对应的Type
     * @param data          数据可空 之后调用addNewData() 或 setNewData()都可以加载数据
     */
    public TestAdapter(ArrayList<BaseItemViewType> itemViewDatas, List<TestBean> data) {
        super(itemViewDatas, data);
    }

    /**
     * 用于设置点击监听 只会调用一次
     *
     * @param holder 当前条目的VH
     */
    @Override
    protected void setItemViewListener(@NonNull BaseRecyclerViewHolder holder) {
        holder.addOnClickListener(R.id.test11);
        holder.addOnLongClickListener(R.id.test11);
    }

    /**
     * 用于绑定数据 被显示时调用
     *
     * @param holder   当前条目的VH
     * @param testBean 当前条目的数据对象
     */
    @Override
    protected void setItemViewData(@NonNull BaseRecyclerViewHolder holder, TestBean testBean, int itemViewType) {
        holder.setText(R.id.test11, testBean.getTestData() + holder.getAdapterPosition());
    }
}
