package com.yanjkcode.baserecycleradapter;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.yanjkcode.baserecycleradapter.adapter.TestAdapter;
import com.yanjkcode.baserecycleradapter.databinding.ActivityMainBinding;
import com.yanjkcode.recyclerviewadapter.base.BaseRecyclerAdapter;
import com.yanjkcode.recyclerviewadapter.base.OnEmptyListener;
import com.yanjkcode.recyclerviewadapter.base.OnItemClickListener;
import com.yanjkcode.recyclerviewadapter.base.OnItemLongClickListener;
import com.yanjkcode.recyclerviewadapter.base.ViewHolder;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TestAdapter mAdapter;
    private ArrayList<TestAdapter.ItemData> mTestBeans;
    private ActivityMainBinding binding;
    private Random mRandom;

    static {
        //设置全局 适配器没有数据时的布局
        BaseRecyclerAdapter.initOnEmptyListener(new OnEmptyListener() {
            @Override
            public int getEmptyLayoutId() {
                return R.layout.test_empty_layout;
            }

            @Override
            public void setEmptyView(ViewHolder holder, int emptyBackground, String emptyText) {
                holder.setImageResource(R.id.iv_empty, R.drawable.ic_launcher_background);
                holder.setText(R.id.tv_hint, emptyText);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mRandom = new Random();

        initView();
        initListener();
        initData();
    }

    private void initView() {
        //常规用法
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TestAdapter();
        binding.recyclerview.setAdapter(mAdapter);

        //简单的用法.....
        //ArrayList<String> strings = new ArrayList<>(); //数据
        //for (int i = 0 ; i < 10 ; i++) {
        //    strings.add("简单的实现方式 :" + i);
        //}
        //EasyListAdapter easyListAdapter = new EasyListAdapter(strings);
        //binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        //binding.recyclerview.setAdapter(easyListAdapter);

        //简单的用法2.....直接内部类实现
        //ArrayList<String> strings = new ArrayList<>(); //数据
        //for (int i = 0 ; i < 10 ; i++) {
        //    strings.add("简单的实现方式2 :" + i);
        //}
        //binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        //binding.recyclerview.setAdapter(new BaseRecyclerAdapter<String>(R.layout.item_easy, strings) {
        //    @Override
        //    protected void setItemViewData(ViewHolder holder, String data, int type) {
        //        holder.setText(R.id.tv, data);
        //    }
        //});

        mAdapter.setEmptyBackground(R.drawable.ic_launcher_background, "什么都没有~");//设置空数据时的背景与文字
    }

    private void initListener() {
        binding.addData.setOnClickListener(this);
        binding.reData.setOnClickListener(this);
        binding.clearData.setOnClickListener(this);
        //设置条目的点击事件
        mAdapter.setOnItemClickListener(new OnItemClickListener<TestAdapter.ItemData>() {
            @Override
            public void onItemClick(BaseRecyclerAdapter<TestAdapter.ItemData> adapter, ViewHolder holder, View view, int position) {
                TestAdapter.ItemData data = adapter.getData(position);  //直接获取当前点击的条目数据
                Toast.makeText(MainActivity.this, "Click = " + data.getTestData() + position, Toast.LENGTH_SHORT).show();
            }
        });
        //设置条目的长按点击事件  模拟长按删除
        mAdapter.setOnItemLongClickListener(new OnItemLongClickListener<TestAdapter.ItemData>() {
            @Override
            public boolean onItemLongClick(BaseRecyclerAdapter<TestAdapter.ItemData> adapter, ViewHolder holder, View view, int position) {
                adapter.removeData(position);
                //adapter.removeData(adapter.getData(position));
                upAdapterCount();
                Toast.makeText(MainActivity.this, "LongClick = " + position, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void initData() {
        mTestBeans = new ArrayList<>();
        for (int i = 0 ; i < 2 ; i++) {
            int type = mRandom.nextInt(3);//随机type
            mTestBeans.add(new TestAdapter.ItemData(type, "测试"));
        }
        upAdapterCount();
    }

    private void upAdapterCount() {
        binding.tvNum.setText(mAdapter.getItemCount() + "");
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.add_data) {
            //模拟添加数据
            mAdapter.addNewData(mTestBeans);//批量添加数据
            //mAdapter.addNewData(1, mTestBeans);//指定下标插入批量数据
            //mAdapter.addNewData(new TestAdapter.ItemData(mRandom.nextInt(3), "测试"));//添加单个数据
            //mAdapter.addNewData(1, new TestAdapter.ItemData(mRandom.nextInt(3), "测试"));//指定下标添加数据
        } else if (id == R.id.re_data) {
            //模拟重置数据
            mAdapter.setNewData(mTestBeans);
        } else if (id == R.id.clear_data) {
            //模拟清空数据
            mAdapter.clearData();
        }
        upAdapterCount();
    }
}
