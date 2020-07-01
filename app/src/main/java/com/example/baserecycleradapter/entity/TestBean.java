package com.example.baserecycleradapter.entity;


import com.example.recyclerviewadapter.base.BaseItemType;

public class TestBean implements BaseItemType {//继承于BaseItemData  如果不继承与baseItemData 默认type是0
    private String testData;
    private int type;

    public TestBean(String testData) {
        this.testData = testData;
    }

    public TestBean(int type, String testData) {
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
