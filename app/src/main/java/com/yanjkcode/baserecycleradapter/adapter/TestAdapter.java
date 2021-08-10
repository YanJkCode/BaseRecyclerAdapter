package com.yanjkcode.baserecycleradapter.adapter;

import com.yanjkcode.baserecycleradapter.R;
import com.yanjkcode.recyclerviewadapter.base.BaseRecyclerAdapter;
import com.yanjkcode.recyclerviewadapter.base.ItemLayoutType;
import com.yanjkcode.recyclerviewadapter.base.ItemType;
import com.yanjkcode.recyclerviewadapter.base.LoadNoDataListener;
import com.yanjkcode.recyclerviewadapter.base.NoDataListener;
import com.yanjkcode.recyclerviewadapter.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class TestAdapter extends BaseRecyclerAdapter<TestAdapter.ItemData> {
    public TestAdapter(List<ItemData> data) {
        super(data);
        //直接在构造中添加布局文件 可以应用于多布局
        //ArrayList<BaseItemViewType> itemViewDatas = new ArrayList<>();
        //itemViewDatas.add(new BaseItemViewType(R.layout.item_layout_test, 0));
        //itemViewDatas.add(new BaseItemViewType(R.layout.item_layout_test1, 1));
        //itemViewDatas.add(new BaseItemViewType(R.layout.item_layout_test2, 2));
        //addLayouts(itemViewDatas);

        //也可以直接调用
        //addLayout(R.layout.item_layout_test, 0);
        //addLayout(R.layout.item_layout_test1, 1);
        //addLayout(R.layout.item_layout_test2, 2);

        //设置数据为空时的布局
        setNoDataListener(new NoDataListener() {
            @Override
            public void setNoDataLayout(ViewHolder holder, int noDataBackground, String noDataText) {
                holder.setImageResource(R.id.iv_no, noDataBackground);
            }

            @Override
            public int getNoDataLayoutId() {
                return R.layout.test_no_data_layout;
            }
        });
    }

    /**
     * 单个条目 用的构造
     *
     * @param layoutId 条目的id
     * @param data     数据可空 之后调用addNewData() 或 setNewData()都可以加载数据
     */
    public TestAdapter(int layoutId, List<ItemData> data) {
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
    public TestAdapter(ArrayList<ItemLayoutType> itemViewDatas, List<ItemData> data) {
        super(itemViewDatas, data);
    }

    /**
     * 用于设置点击监听 只会调用一次
     *
     * @param holder 当前条目的VH
     */
    @Override
    protected void setItemViewListener(ViewHolder holder) {
        holder.addOnClickListener(R.id.tv);
        holder.addOnLongClickListener(R.id.tv);

        holder.addItemOnClickListener();
        holder.addItemOnLongClickListener();
    }


    /**
     * 用于绑定数据 被显示时调用
     *
     * @param holder   当前条目的VH
     * @param itemData 当前条目的数据对象
     */
    @Override
    protected void setItemViewData(ViewHolder holder, ItemData itemData, int itemViewType) {
        holder.setText(R.id.tv, itemData.getTestData());
    }

    public static class ItemData implements ItemType {//继承于BaseItemData  如果不继承与baseItemData 默认type是0
        private String testData;
        private int type;

        public ItemData(String testData) {
            this.testData = testData;
        }

        public ItemData(int type, String testData) {
            this.testData = testData;
            this.type = type;
        }

        public String getTestData() {
            return testData;
        }

        public void setTestData(String testData) {
            this.testData = testData;
        }

        /**
         * 重写后可以在这里传入对应的值或者在这里进行判断
         *
         * @param position 当前条目的下标
         * @return
         */
        @Override
        public int getItemType(int position) {
            return type;
        }
    }
}
