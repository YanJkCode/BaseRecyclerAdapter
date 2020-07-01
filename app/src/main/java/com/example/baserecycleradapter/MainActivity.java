package com.example.baserecycleradapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baserecycleradapter.adapter.EasyListAdapter;
import com.example.baserecycleradapter.adapter.TestAdapter;
import com.example.baserecycleradapter.entity.TestBean;
import com.example.recyclerviewadapter.base.BaseItemViewType;
import com.example.recyclerviewadapter.base.BaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private TestAdapter mAdapter;
    private ArrayList<TestBean> mTestBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.rec);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mTestBeans = new ArrayList<>();
        Random random = new Random();
        for (int i = 0 ; i < 30 ; i++) {
            //mTestBeans.add(new TestBean("测试" + i));//不设置值默认Type是0
            int type = random.nextInt(3);//随机type
            mTestBeans.add(new TestBean(type, "测试"));
            //mTestBeans.add(new TestBean(0, "测试" + i));
        }

        // 直接传一个layoutID单布局 然后传一个Data数组
        //mAdapter = new TestAdapter(R.layout.item_layout_test, testBeans); //这样默认布局type是0

        //多布局时  可以直接在构造中添加布局数据
        ArrayList<BaseItemViewType> itemViewDatas = new ArrayList<>();//多布局数据
        itemViewDatas.add(new BaseItemViewType(R.layout.item_layout_test, 0));//布局文件ID Type
        itemViewDatas.add(new BaseItemViewType(R.layout.item_layout_test1, 1));
        itemViewDatas.add(new BaseItemViewType(R.layout.item_layout_test2, 2));

        //创建方式
        //mAdapter = new TestAdapter(itemViewDatas, mTestBeans);//直接传入布局和数据  一般用于回调数据成功后直接创建并设置
        //mAdapter = new TestAdapter(itemViewDatas, null); //data可空所以直接传了布局进去
        mAdapter = new TestAdapter(mTestBeans);

        mRecyclerView.setAdapter(mAdapter);

        //mAdapter.addLayout(R.layout.item_layout_test, 0);//载入布局1个布局
        mAdapter.addLayouts(itemViewDatas);//载入布局 多个布局

        //设置条目里的点击事件
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<TestBean>() {
            @Override
            public void onItemClick(BaseRecyclerAdapter<TestBean> adapter, View view, int position) {
                TestBean data = adapter.getData(position);  //直接获取当前点击的条目数据
                Toast.makeText(MainActivity.this, "Click = " + data.getTestData() + position, Toast.LENGTH_SHORT).show();
            }
        });

        //设置条目里的长按点击事件  模拟长按删除
        mAdapter.setOnItemLongClickListener(new BaseRecyclerAdapter.OnItemLongClickListener<TestBean>() {
            @Override
            public boolean onItemLongClick(BaseRecyclerAdapter<TestBean> adapter, View view, int position) {
                adapter.removeData(position); //带有删除动画 (推荐)
                //adapter.removeData(adapter.getData(position));//不带删除动画
                Toast.makeText(MainActivity.this, "LongClick = " + position, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        //模拟添加数据
        findViewById(R.id.bottom01).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.addNewData(mTestBeans);
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
                mAdapter.dataClear();
                MainActivity.this.<TextView>findViewById(R.id.textnum).setText(mAdapter.getItemCount() + "");
            }
        });

        //最简单的用法.....
        //ArrayList<String> strings = new ArrayList<>(); //数据
        //for (int i = 0 ; i < 30 ; i++) {
        //    strings.add("最简单的实现方式" + i);
        //}
        //EasyListAdapter easyListAdapter = new EasyListAdapter(strings);
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //mRecyclerView.setAdapter(easyListAdapter);

    }
}
