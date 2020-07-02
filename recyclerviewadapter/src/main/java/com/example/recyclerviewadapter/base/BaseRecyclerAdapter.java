package com.example.recyclerviewadapter.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewadapter.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {

    private HashMap<Integer, Integer> mItemViewDatas = new HashMap<>();//布局Id与布局Type
    private LayoutInflater mLayoutInflater;//布局载入器
    private List<T> mData = new ArrayList<>();//数据源

    /**
     * 无数据无布局
     */
    public BaseRecyclerAdapter() {
    }

    /**
     * 先设置数据后设置布局
     *
     * @param data 数据
     */
    public BaseRecyclerAdapter(List<T> data) {
        if (isArrNoData(data)) {
            mData.addAll(data);
        }
    }

    /**
     * 有布局  无数据
     */
    public BaseRecyclerAdapter(@LayoutRes int layoutId) {
        addLayout(layoutId, 0);
    }

    /**
     * 默认单条目时
     *
     * @param layoutId
     * @param data
     */
    public BaseRecyclerAdapter(@LayoutRes int layoutId, List<T> data) {
        if (isArrNoData(data)) {
            mData.addAll(data);
        }
        addLayout(layoutId, 0);
    }


    /**
     * 多布局
     *
     * @param itemViewDatas 布局条目对象
     * @param data          数据
     */
    public BaseRecyclerAdapter(List<BaseItemViewType> itemViewDatas, List<T> data) {
        if (isArrNoData(data)) {
            mData.addAll(data);
        }
        addLayouts(itemViewDatas);
    }

    /**
     * 多布局 无数据
     *
     * @param itemViewDatas 布局条目对象
     */
    public BaseRecyclerAdapter(BaseItemViewType... itemViewDatas) {
        addLayouts(itemViewDatas);
    }

    /**
     * 获取当前适配器的全部数据
     *
     * @return
     */
    public List<T> getDataList() {
        return mData == null ? new ArrayList<T>() : mData;
    }

    /**
     * 获取当前适配器指定下标的数据
     *
     * @param position
     * @return
     */
    public T getData(int position) {
        int size = mData.size();
        if (size > 0 && size > position) {
            return mData.get(position);
        }
        return null;
    }

    /**
     * 载入多个布局 使用重复的layoutType会被覆盖
     *
     * @param itemViewDatas 布局id和Type
     */
    public BaseRecyclerAdapter addLayouts(List<BaseItemViewType> itemViewDatas) {
        for (BaseItemViewType itemViewData : itemViewDatas) {
            mItemViewDatas.put(itemViewData.getLayoutType(), itemViewData.getLayoutID());
        }
        notifyDataSetChanged();
        return this;
    }

    /**
     * 载入多个布局 使用重复的layoutType会被覆盖
     *
     * @param itemViewDatas 布局id和Type
     */
    public BaseRecyclerAdapter addLayouts(BaseItemViewType... itemViewDatas) {
        for (BaseItemViewType itemViewData : itemViewDatas) {
            mItemViewDatas.put(itemViewData.getLayoutType(), itemViewData.getLayoutID());
        }
        notifyDataSetChanged();
        return this;
    }

    /**
     * 载入布局
     *
     * @param layoutId   布局Id
     * @param layoutType 使用重复的layoutType会被覆盖
     */
    public BaseRecyclerAdapter addLayout(@LayoutRes int layoutId, int layoutType) {
        mItemViewDatas.put(layoutType, layoutId);
        notifyDataSetChanged();
        return this;
    }

    /**
     * 设置数据 会覆盖原有数据
     *
     * @param data
     */
    public BaseRecyclerAdapter setNewData(T data) {
        if (data != null) {
            if (mData.size() > 0) {
                mData.clear();
            }
            mData.add(data);
            notifyDataSetChanged();
        }
        return this;
    }

    /**
     * 设置数据 会覆盖原有数据
     *
     * @param data
     */
    public BaseRecyclerAdapter setNewData(List<T> data) {
        if (isArrNoData(data)) {
            if (mData.size() > 0) {
                mData.clear();
            }
            mData.addAll(data);
            notifyDataSetChanged();
        }
        return this;
    }

    /**
     * 删除指定下标的数据 并刷新  带有删除动画
     *
     * @param position 数据下标
     */
    public BaseRecyclerAdapter removeData(int position) {
        int size = mData.size();
        if (size > 0 && size > position) {
            mData.remove(position);
            notifyItemRemoved(position);
        }
        return this;
    }

    /**
     * 删除指定数据 并刷新 因为不知道下标所以没有删除动画
     *
     * @param data 目标
     */
    public BaseRecyclerAdapter removeData(T data) {
        if (data != null) {
            mData.remove(data);
            notifyDataSetChanged();
        }
        return this;
    }

    /**
     * 清除数据 清空全部数据
     */
    public BaseRecyclerAdapter dataClear() {
        if (mData.size() > 0) {
            mData.clear();
            notifyDataSetChanged();
        }
        return this;
    }

    /**
     * 添加数据 如果本来就没有数据则会直接覆盖  如果有数据则添加到后面
     *
     * @param data
     */
    public BaseRecyclerAdapter addNewData(List<T> data) {
        if (isArrNoData(data)) {
            int startSize = mData.size();
            mData.addAll(data);
            notifyItemRangeInserted(startSize, mData.size());
            // 刷新之后的数据.不使用notifyDataSetChanged() 因为每次调用都会重新创建新的ViewHolder
        }
        return this;
    }

    /**
     * 添加数据 如果本来就没有数据则会直接覆盖  如果有数据则添加到后面
     *
     * @param data
     */
    public BaseRecyclerAdapter addNewData(T data) {
        if (data != null) {
            int startSize = mData.size();
            mData.add(data);
            notifyItemRangeInserted(startSize, mData.size());
            // 刷新之后的数据.不使用notifyDataSetChanged() 因为每次调用都会重新创建新的ViewHolder
        }
        return this;
    }

    /**
     * 创建ViewHolder
     */
    @NonNull
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());//创建布局填充器 如果已经有了则不会创建
        }
        BaseRecyclerViewHolder vh = null;
        if (mItemViewDatas.size() > 0) {
            Integer layoutId = mItemViewDatas.get(viewType);//用viewType获取一个布局
            View inflate = null;
            if (layoutId != null || layoutId != 0) {
                inflate = mLayoutInflater.inflate(layoutId, parent, false);//使用这个layoutId创建一个View
            } else {
                inflate = mLayoutInflater.inflate(R.layout.base_def_item, parent, false);//使用默认布局
            }
            vh = new BaseRecyclerViewHolder(inflate);//把View
            // 放到BaseViewHolder中
            vh.setAdapter(this);//把当前Adapter载入到BaseViewHolder中方便设置点击事件
            setItemViewListener(vh);
        }
        //如果走到这里还是空说明前面设置有问题.直接给一个空的布局
        if (vh == null) {
            vh = new BaseRecyclerViewHolder(new TextView(parent.getContext()));
        }
        return vh;
    }

    /**
     * 设置条目监听
     *
     * @param holder
     */
    protected void setItemViewListener(@NonNull BaseRecyclerViewHolder holder) {
    }

    /**
     * 绑定数据
     *
     * @param holder   当前条目的BaseViewHolder
     * @param position 下标
     */
    @Override
    public final void onBindViewHolder(@NonNull BaseRecyclerViewHolder holder, int position) {
        setItemViewData(holder, mData.get(position), getItemViewType(position));
    }

    /**
     * 设置数据
     *
     * @param holder       当前条目的BaseViewHolder
     * @param t            数据
     * @param itemViewType
     */
    protected abstract void setItemViewData(@NonNull BaseRecyclerViewHolder holder, T t, int itemViewType);

    /**
     * 获取当前下标的布局类型
     *
     * @param position 下标
     * @return viewType
     */
    @Override
    public int getItemViewType(int position) {
        int size = mData.size();
        if (size > 0 && size > position) {
            T t = mData.get(position);//获取当前下标的数据
            if (t != null && t instanceof BaseItemType) {//判断数据是否存在,并且是否是继承与BaseItemType类
                return ((BaseItemType) t).getItemType(position);//BaseItemType实现了获取条目状态接口
            }
        }
        return 0;//如果不是这个类型默认返回0 防止 不继承BaseItemType类的类出现
    }

    /**
     * 获取总条目数
     */
    @Override
    public int getItemCount() {
        //判断当前是否有数据和布局
        return isArrNoData(mData) || isMapNoData(mItemViewDatas) ? mData.size() : 0;
    }

    /**
     * 判断集合是否有数据
     *
     * @return true 有数据  false没有数据
     */
    private static boolean isArrNoData(Collection collection) {
        return collection != null && collection.size() > 0;
    }

    /**
     * 判断MAP集合是否有数据
     *
     * @return true 有数据  false没有数据
     */
    private static boolean isMapNoData(Map map) {
        return map != null && map.size() > 0;
    }

    /**
     * 点击事件接口
     */
    private OnItemClickListener<T> mOnItemClickListener;

    /**
     * 设置条目里的点击 监听(包括普通的点击事件)
     */
    public BaseRecyclerAdapter setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
        return this;
    }

    /**
     * 获取条目点击事件
     */
    public OnItemClickListener<T> getOnItemClickListener() {
        return mOnItemClickListener;
    }

    /**
     * 条目点击回调接口
     */
    public interface OnItemClickListener<T> {
        void onItemClick(BaseRecyclerAdapter<T> adapter, View view, int position);
    }

    /**
     * 长按点击事件接口
     */
    private OnItemLongClickListener<T> mOnItemLongClickListener;

    /**
     * 设置条目里的长按点击 监听(包括普通的长按点击事件)
     *
     * @param onItemLongClickListener
     */
    public BaseRecyclerAdapter setOnItemLongClickListener(OnItemLongClickListener<T> onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
        return this;
    }

    /**
     * 获取长按点击事件的接口
     */
    public OnItemLongClickListener getOnItemLongClickListener() {
        return mOnItemLongClickListener;
    }

    /**
     * 长按点击事件的接口
     */
    public interface OnItemLongClickListener<T> {
        boolean onItemLongClick(BaseRecyclerAdapter<T> adapter, View view, int position);
    }
}
