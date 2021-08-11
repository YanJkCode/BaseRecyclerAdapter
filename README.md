# BaseRecyclerAdapter
个人制作的列表适配器 使用方法和BaseRecyclerViewAdapterHelper差不多 主要是自己优化了点击事件的回调 使用了1个适配器完成单或多布局 点击回调接口中回传点击的数据不需要转换数据 使用更加方便  
下拉刷新 上拉加载可以配合SmartRefreshLayout使用
## 导入方式
### 将JitPack存储库添加到您的构建文件中(项目根目录下build.gradle文件)
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

### 添加依赖项
[![](https://jitpack.io/v/YanJkCode/BaseRecyclerAdapter.svg)](https://jitpack.io/#YanJkCode/BaseRecyclerAdapter)
```
dependencies {
    implementation 'com.github.YanJkCode:BaseRecyclerAdapter:1.2.0'
}
```
