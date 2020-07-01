package com.example.baserecycleradapter.entity;

import com.example.recyclerviewadapter.base.BaseItemData;

public class TestBean2 extends BaseItemData {//继承于BaseItemData 如果不继承与baseItemData 默认type是0
    private String testData;

    public TestBean2(String testData) {
        super(0);//如果不是多布局可以设置为0
        this.testData = testData;
    }

    public TestBean2(int type, String testData) {
        super(type);//多布局自定义Type 如果这时候不知道Type可以传0 之后调用setItemType(type) 设置当前条目的Type
        this.testData = testData;
    }

    public String getTestData() {
        return testData;
    }

    public void setTestData(String testData) {
        this.testData = testData;
    }

}
