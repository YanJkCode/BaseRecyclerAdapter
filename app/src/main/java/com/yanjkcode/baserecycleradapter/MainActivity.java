package com.yanjkcode.baserecycleradapter;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yanjkcode.baserecycleradapter.adapter.TestAdapter;
import com.yanjkcode.recyclerviewadapter.base.BaseRecyclerAdapter;
import com.yanjkcode.recyclerviewadapter.base.ItemLayoutType;
import com.yanjkcode.recyclerviewadapter.base.NoDataListener;
import com.yanjkcode.recyclerviewadapter.base.ViewHolder;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private TestAdapter mAdapter;
    private ArrayList<TestAdapter.ItemData> mTestBeans;

    static {
        //设置全局 适配器没有数据时的布局
        BaseRecyclerAdapter.initNoDataListener(new NoDataListener() {
            @Override
            public int getNoDataLayoutId() {
                return R.layout.test_no_data_layout;
            }

            @Override
            public void setNoDataLayout(ViewHolder holder, int noDataBackground, String noDataText) {
                holder.setImageResource(R.id.iv_no, R.drawable.ic_launcher_background);
                holder.setText(R.id.tv_hint, noDataText);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.rec);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mTestBeans = new ArrayList<>();
        Random random = new Random();
        for (int i = 0 ; i < 2 ; i++) {
            //mTestBeans.add(new TestBean("测试" + i));//不设置值默认Type是0
            int type = random.nextInt(3);//随机type
            mTestBeans.add(new TestAdapter.ItemData(type, "测试"));
            //mTestBeans.add(new TestBean(0, "测试" + i));
        }

        // 直接传一个layoutID单布局 然后传一个Data数组
        //mAdapter = new TestAdapter(R.layout.item_layout_test, testBeans); //这样默认布局type是0

        //多布局时  可以直接在构造中添加布局数据
        ArrayList<ItemLayoutType> itemViewData = new ArrayList<>();//多布局数据
        itemViewData.add(new ItemLayoutType(R.layout.item_layout_test, 0));//布局文件ID Type
        itemViewData.add(new ItemLayoutType(R.layout.item_layout_test1, 1));
        itemViewData.add(new ItemLayoutType(R.layout.item_layout_test2, 2));


        //创建方式
        //mAdapter = new TestAdapter(itemViewDatas, mTestBeans);//直接传入布局和数据  一般用于回调数据成功后直接创建并设置
        //mAdapter = new TestAdapter(itemViewDatas, null); //data可空所以直接传了布局进去
        mAdapter = new TestAdapter(mTestBeans);
        mAdapter.setNoDataBackground(R.drawable.ic_launcher_background, "测试");

        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<TestAdapter.ItemData>() {
            @Override
            public void onItemClick(BaseRecyclerAdapter<TestAdapter.ItemData> adapter, ViewHolder holder, View view, int position) {

            }
        });
        mRecyclerView.setAdapter(mAdapter);

        //mAdapter.addLayout(R.layout.item_layout_test, 0);//载入布局1个布局
        mAdapter.addLayouts(itemViewData);//载入布局 多个布局

        //设置条目里的点击事件
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<TestAdapter.ItemData>() {
            @Override
            public void onItemClick(BaseRecyclerAdapter<TestAdapter.ItemData> adapter, ViewHolder viewHolder, View view, int position) {
                TestAdapter.ItemData data = adapter.getData(position);  //直接获取当前点击的条目数据
                Toast.makeText(MainActivity.this, "Click = " + data.getTestData() + position, Toast.LENGTH_SHORT).show();
            }
        });

        //设置条目里的长按点击事件  模拟长按删除
        mAdapter.setOnItemLongClickListener(new BaseRecyclerAdapter.OnItemLongClickListener<TestAdapter.ItemData>() {
            @Override
            public boolean onItemLongClick(BaseRecyclerAdapter<TestAdapter.ItemData> adapter, ViewHolder viewHolder, View view, int position) {
                adapter.removeData(position);
                //adapter.removeData(adapter.getData(position));
                MainActivity.this.<TextView>findViewById(R.id.textnum).setText(mAdapter.getItemCount() + "");
                Toast.makeText(MainActivity.this, "LongClick = " + position, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        //模拟添加数据
        findViewById(R.id.bottom01).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.addNewData( mTestBeans);
                //mAdapter.addNewData( new TestAdapter.ItemData(1, "测试"));
                MainActivity.this.<TextView>findViewById(R.id.textnum).setText(mAdapter.getItemCount() + "");
            }
        });

        //模拟重置数据
        findViewById(R.id.bottom02).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.setNewData(mTestBeans);
                MainActivity.this.<TextView>findViewById(R.id.textnum).setText(mAdapter.getItemCount() + "");
            }
        });

        //模拟清空数据
        findViewById(R.id.bottom03).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.clearData();
                MainActivity.this.<TextView>findViewById(R.id.textnum).setText(mAdapter.getItemCount() + "");
            }
        });

        //最简单的用法.....
        //ArrayList<String> strings = new ArrayList<>(); //数据
        //for (int i = 0 ; i < 30 ; i++) {
        //    strings.add("最简单的实现方式 :" + i);
        //}
        //EasyListAdapter easyListAdapter = new EasyListAdapter(strings);
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //mRecyclerView.setAdapter(easyListAdapter);

        //最简单的用法2.....直接内部类实现
        //ArrayList<String> strings = new ArrayList<>(); //数据
        //for (int i = 0 ; i < 30 ; i++) {
        //    strings.add("最简单的实现方式2 :" + i);
        //}
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //mRecyclerView.setAdapter(new BaseRecyclerAdapter<String>(R.layout.item_easy, strings) {
        //    @Override
        //    protected void setItemViewData(ViewHolder holder, String data, int type) {
        //        holder.setText(R.id.tv, data);
        //    }
        //});


        MainActivity.this.<TextView>findViewById(R.id.textnum).setText(mAdapter.getItemCount() + "");
    }
}
