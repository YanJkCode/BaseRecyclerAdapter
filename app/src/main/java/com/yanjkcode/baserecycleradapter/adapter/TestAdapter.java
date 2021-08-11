package com.yanjkcode.baserecycleradapter.adapter;

import com.yanjkcode.baserecycleradapter.R;
import com.yanjkcode.recyclerviewadapter.base.BaseRecyclerAdapter;
import com.yanjkcode.recyclerviewadapter.base.ItemLayoutType;
import com.yanjkcode.recyclerviewadapter.base.ItemType;
import com.yanjkcode.recyclerviewadapter.base.OnEmptyListener;
import com.yanjkcode.recyclerviewadapter.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class TestAdapter extends BaseRecyclerAdapter<TestAdapter.ItemData> {
    public TestAdapter() {
        //直接在构造中添加布局文件 可以应用于多布局
        //ArrayList<ItemLayoutType> itemViewData = new ArrayList<>();
        //itemViewData.add(new ItemLayoutType(R.layout.item_layout_test, 0));
        //itemViewData.add(new ItemLayoutType(R.layout.item_layout_test1, 1));
        //itemViewData.add(new ItemLayoutType(R.layout.item_layout_test2, 2));
        //addLayouts(itemViewData);

        addLayouts(new ItemLayoutType(R.layout.item_layout_test, 0),
                new ItemLayoutType(R.layout.item_layout_test1, 1),
                new ItemLayoutType(R.layout.item_layout_test2, 2));

        //也可以直接调用
        //addLayout(R.layout.item_layout_test, 0);
        //addLayout(R.layout.item_layout_test1, 1);
        //addLayout(R.layout.item_layout_test2, 2);

        //设置数据为空时的布局
        setOnEmptyListener(new OnEmptyListener() {
            @Override
            public void setEmptyView(ViewHolder holder, int emptyBackground, String emptyText) {
                holder.setImageResource(R.id.iv_empty, emptyBackground);
            }

            @Override
            public int getEmptyLayoutId() {
                return R.layout.test_empty_layout;
            }
        });
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
         * @return 所属类型
         */
        @Override
        public int getItemType(int position) {
            return type;
        }
    }
}
