package com.yanjkcode.recyclerviewadapter.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yanjkcode.recyclerviewadapter.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Author : YanJk
 * Time : 2020/7/27 9:18
 * <p>
 * Description : RecyclerView列表 适配器  可以用于单布局或多布局
 *
 * @param <T> 每个条目的数据;
 */
@NotProguard
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    private static NoDataListener sNoDataListener;//全局 没有数据监听

    private final HashMap<Integer, Integer> mItemViewDatas = new HashMap<>();//布局Id与布局Type
    private LayoutInflater mLayoutInflater;//布局载入器
    private final List<T> mData = new ArrayList<>();//数据源

    private boolean isAddNoDataLayout;//是否已经更改了布局管理器
    private int noDataBackground;//没有数据的背景
    private String noDataText;//没有数据的背景文字
    private RecyclerView.LayoutManager noDataLayoutManager;//没有数据时的布局管理器 防止其他类型的管理器导致页面效果不正确
    private NoDataListener mNoDataListener;//当前 没有数据监听

    private RecyclerView curBindRecyclerView;//当前绑定的列表
    private RecyclerView.LayoutManager layoutManager;//当前的管理器
    private static final int TYPE_NO_DATA = 0x3d821;//没有数据时的布局类型

    /**
     * 绑定视图时
     *
     * @param recyclerView 当前绑定的RecyclerView
     */
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        curBindRecyclerView = recyclerView;
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    /**
     * 解绑视图
     *
     * @param recyclerView 解除绑定的RecyclerView
     */
    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        if (curBindRecyclerView != null && curBindRecyclerView == recyclerView) {
            curBindRecyclerView = null;
        }
    }

    /**
     * 设置没有数据时的背景
     *
     * @param noDataBackground 资源ID
     */
    public final void setNoDataBackground(@DrawableRes int noDataBackground) {
        setNoDataBackground(noDataBackground, null);
    }

    /**
     * 设置没有数据时的背景 
     *
     * @param noDataBackground 资源ID
     * @param noDataText       文本
     */
    public final void setNoDataBackground(@DrawableRes int noDataBackground, String noDataText) {
        this.noDataBackground = noDataBackground;
        this.noDataText = noDataText;
        int noDataLayoutId = 0;
        if (mNoDataListener != null) {
            noDataLayoutId = mNoDataListener.getNoDataLayoutId();
        }
        if (noDataLayoutId == 0 && sNoDataListener != null) {
            noDataLayoutId = sNoDataListener.getNoDataLayoutId();
        }
        if (noDataLayoutId != 0 && !mItemViewDatas.containsKey(TYPE_NO_DATA)) {
            mItemViewDatas.put(TYPE_NO_DATA, noDataLayoutId);
            notifyDataSetChanged();
        }
    }

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
        if (data != null) {
            mData.addAll(data);
        }
    }

    /**
     * 有布局  无数据
     *
     * @param layoutId 布局ID
     */
    public BaseRecyclerAdapter(@LayoutRes int layoutId) {
        addLayout(layoutId, 0);
    }

    /**
     * 默认单条目时
     *
     * @param layoutId 布局ID
     * @param data     数据
     */
    public BaseRecyclerAdapter(@LayoutRes int layoutId, List<T> data) {
        if (data != null) {
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
    public BaseRecyclerAdapter(List<ItemLayoutType> itemViewDatas, List<T> data) {
        if (data != null) {
            mData.addAll(data);
        }
        if (itemViewDatas != null) {
            addLayouts(itemViewDatas);
        }
    }

    /**
     * 多布局 无数据
     *
     * @param itemViewDatas 布局条目对象
     */
    public BaseRecyclerAdapter(ItemLayoutType... itemViewDatas) {
        addLayouts(itemViewDatas);
    }

    /**
     * 获取当前适配器的全部数据
     *
     * @return 集合数据
     */
    public final List<T> getDataList() {
        return mData;
    }

    /**
     * 获取当前适配器指定下标的数据
     *
     * @param position 下标
     * @return 下标内的数据
     */
    public final T getData(int position) {
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
    public final void addLayouts(@NonNull List<ItemLayoutType> itemViewDatas) {
        for (ItemLayoutType itemViewData : itemViewDatas) {
            mItemViewDatas.put(itemViewData.getLayoutType(), itemViewData.getLayoutID());
        }
        notifyDataSetChanged();
    }

    /**
     * 载入多个布局 使用重复的layoutType会被覆盖
     *
     * @param itemViewDatas 布局id和Type
     */
    public final void addLayouts(ItemLayoutType... itemViewDatas) {
        if (itemViewDatas == null) {
            return;
        }
        for (ItemLayoutType itemViewData : itemViewDatas) {
            mItemViewDatas.put(itemViewData.getLayoutType(), itemViewData.getLayoutID());
        }
        notifyDataSetChanged();
    }

    /**
     * 载入布局
     *
     * @param layoutId   布局Id
     * @param layoutType 使用重复的layoutType会被覆盖
     */
    public final void addLayout(@LayoutRes int layoutId, int layoutType) {
        mItemViewDatas.put(layoutType, layoutId);
        notifyDataSetChanged();
    }

    /**
     * 设置数据 会覆盖原有数据
     *
     * @param data 单个数据
     */
    public final void setNewData(T data) {
        if (data != null) {
            mData.clear();
            notifyDataSetChanged();
            mData.add(data);
            notifyItemRangeInserted(0, mData.size());
        }
    }

    /**
     * 设置数据 会覆盖原有数据
     *
     * @param data 数据集合
     */
    public final void setNewData(List<T> data) {
        if (data != null) {
            mData.clear();
            notifyDataSetChanged();
            mData.addAll(data);
            notifyItemRangeInserted(0, mData.size());
        }
    }

    /**
     * 删除指定下标的数据 并刷新  带有删除动画
     *
     * @param position 数据下标
     */
    public final T removeData(int position) {
        int size = mData.size();
        if (size > 0 && size > position) {
            T remove = mData.remove(position);
            if (mData.size() > 0) {
                notifyItemRemoved(position);
            } else {
                notifyDataSetChanged();
            }
            return remove;
        }
        return null;
    }

    /**
     * 删除指定数据 并刷新 因为不知道下标所以没有删除动画
     *
     * @param data 目标
     */
    public final T removeData(T data) {
        if (data != null) {
            int position = -1;
            for (int i = 0 ; i < mData.size() ; i++) {
                if (mData.get(i) == data) {
                    position = i;
                    break;
                }
            }
            if (position != -1) {
                T remove = mData.remove(position);
                if (mData.size() > 0) {
                    notifyItemRemoved(position);
                } else {
                    notifyDataSetChanged();
                }
                return remove;
            }
        }
        return null;
    }

    /**
     * 清除数据 清空全部数据
     */
    public final void clearData() {
        if (mData.size() > 0) {
            mData.clear();
            notifyDataSetChanged();
        }
    }

    /**
     * 添加数据 如果本来就没有数据则会直接覆盖  如果有数据则添加到后面
     *
     * @param data 数据集合
     */
    public final void addNewData(int position, List<T> data) {
        if (data != null) {
            int size = mData.size();
            if (size < position) {
                position = size;
            }
            mData.addAll(position, data);
            if (size == 0) {
                notifyItemRangeChanged(0, mData.size());
            } else {
                notifyItemRangeInserted(position, data.size());
            }
            // 刷新之后的数据.不使用notifyDataSetChanged() 因为每次调用都会重新创建新的ViewHolder
        }
    }

    /**
     * 添加数据 如果本来就没有数据则会直接覆盖  如果有数据则添加到后面
     *
     * @param data 数据集合
     */
    public final void addNewData(List<T> data) {
        if (data != null) {
            int startSize = mData.size();
            mData.addAll(data);
            if (startSize == 0) {
                notifyItemRangeChanged(0, mData.size());
            } else {
                notifyItemRangeInserted(startSize, data.size());
            }
            // 刷新之后的数据.不使用notifyDataSetChanged() 因为每次调用都会重新创建新的ViewHolder
        }
    }

    /**
     * 添加1条数据 如果本来就没有数据则会直接覆盖  如果有数据则添加到后面
     *
     * @param data 单条数据
     */
    public final void addNewData(int position, T data) {
        if (data != null) {
            int size = mData.size();
            if (size < position) {
                position = size;
            }
            mData.add(position, data);
            if (size == 0) {
                notifyItemChanged(0);
            } else {
                notifyItemInserted(position);
            }
        }
    }

    /**
     * 添加1条数据 如果本来就没有数据则会直接覆盖  如果有数据则添加到后面
     *
     * @param data 单条数据
     */
    public final void addNewData(T data) {
        if (data != null) {
            int size = mData.size();
            mData.add(data);
            if (size == 0) {
                notifyItemChanged(0);
            } else {
                notifyItemInserted(mData.size() - 1);
            }
        }
    }

    /**
     * 创建ViewHolder
     */
    @NonNull
    @Override
    public final ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());//创建布局填充器 如果已经有了则不会创建
        }
        ViewHolder vh = null;
        if (mItemViewDatas.size() > 0) {
            Integer layoutId;
            try {
                layoutId = mItemViewDatas.get(viewType);//用viewType获取一个布局
            } catch (Exception e) {
                throw new NullPointerException("Error:获取布局ID失败 请检查布局ID与TYPE是否对应");
            }
            View inflate;
            if (layoutId != null && layoutId > 0) {
                inflate = mLayoutInflater.inflate(layoutId, parent, false);//使用这个layoutId创建一个View
            } else {
                throw new NullPointerException("Error:获取布局失败 请检查布局ID");
            }
            vh = new ViewHolder(inflate);//把View放到BaseViewHolder中
            vh.setAdapter(this);//把当前Adapter载入到BaseViewHolder中方便设置点击事件
        }
        if (vh == null) {//如果走到这里还是空说明前面设置有问题.
            throw new NullPointerException("Error:获取布局失败 请检查布局ID");
        }
        vh.setType(viewType);
        return vh;
    }

    /**
     * 绑定数据
     *
     * @param holder   当前条目的BaseViewHolder
     * @param position 下标
     */
    @Override
    public final void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (noDataBackground > 0 && position == 0 && mData.size() == 0) {//没有数据时加载
            if (mNoDataListener != null) {//优先使用当前类的加载回调
                mNoDataListener.setNoDataLayout(holder, noDataBackground, noDataText);
            } else {
                if (sNoDataListener != null) {//使用全局加载回调
                    sNoDataListener.setNoDataLayout(holder, noDataBackground, noDataText);
                }
            }
        } else {
            setItemViewListener(holder);
            setItemViewData(holder, mData.get(position), getItemViewType(position));
        }
    }

    /**
     * 设置条目监听
     *
     * @param holder 当前条目的BaseViewHolder
     */
    protected void setItemViewListener(ViewHolder holder) {
    }

    /**
     * 设置数据
     *
     * @param holder 当前条目的BaseViewHolder
     * @param data   数据
     * @param type   ItemData的类型
     */
    protected abstract void setItemViewData(ViewHolder holder, T data, int type);

    /**
     * 获取当前下标的布局类型
     *
     * @param position 下标
     * @return viewType
     */
    @Override
    public final int getItemViewType(int position) {
        int size = mData.size();
        if (position == 0 &&
                noDataBackground > 0 &&
                mData.size() == 0 &&
                mItemViewDatas.containsKey(TYPE_NO_DATA) &&
                (mNoDataListener != null || sNoDataListener != null)) {
            return TYPE_NO_DATA;
        } else {
            if (size > position) {
                T t = mData.get(position);//获取当前下标的数据
                if (t instanceof ItemType) {//判断数据是否存在,并且是否是继承与BaseItemType类
                    return ((ItemType) t).getItemType(position);//BaseItemType实现了获取条目状态接口
                }
            }
        }
        return 0;//如果不是这个类型默认返回0 防止 不继承BaseItemType类的类出现
    }

    /**
     * 获取总条目数
     */
    @Override
    public final int getItemCount() {
        int size;
        //判断当前是否有数据和布局
        if (mData.size() > 0 && mItemViewDatas.size() > 0) {
            if (isAddNoDataLayout) {
                curBindRecyclerView.setLayoutManager(layoutManager);
                isAddNoDataLayout = false;
            }
            size = mData.size();
        } else {
            if (noDataBackground > 0 &&
                    mData.size() == 0 &&
                    mItemViewDatas.containsKey(TYPE_NO_DATA) &&
                    (mNoDataListener != null || sNoDataListener != null)) {//空数据时是否有数据

                if (layoutManager == null) {
                    layoutManager = curBindRecyclerView.getLayoutManager();
                }
                if (noDataLayoutManager == null) {
                    noDataLayoutManager = new LinearLayoutManager(curBindRecyclerView.getContext());
                }
                curBindRecyclerView.setLayoutManager(noDataLayoutManager);
                isAddNoDataLayout = true;
                size = 1;
            } else {
                size = 0;
            }
        }
        return size;
    }

    /**
     * 点击事件接口
     */
    private OnItemClickListener<T> mOnItemClickListener;

    /**
     * 设置条目里的点击 监听(包括普通的点击事件)
     */
    public final void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    /**
     * 获取条目点击事件
     */
    public final OnItemClickListener<T> getOnItemClickListener() {
        return mOnItemClickListener;
    }

    /**
     * 条目点击回调接口
     */
    @NotProguard
    public interface OnItemClickListener<T> {
        void onItemClick(BaseRecyclerAdapter<T> adapter, ViewHolder holder, View view, int position);
    }

    /**
     * 长按点击事件接口
     */
    private OnItemLongClickListener<T> mOnItemLongClickListener;

    /**
     * 设置条目里的长按点击 监听(包括普通的长按点击事件)
     *
     * @param onItemLongClickListener 条目长按监听
     */
    public final void setOnItemLongClickListener(OnItemLongClickListener<T> onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 获取长按点击事件的接口
     */
    public final OnItemLongClickListener<T> getOnItemLongClickListener() {
        return mOnItemLongClickListener;
    }

    /**
     * 长按点击事件的接口
     */
    @NotProguard
    public interface OnItemLongClickListener<T> {
        boolean onItemLongClick(BaseRecyclerAdapter<T> adapter, ViewHolder holder, View view, int position);
    }

    /**
     * 设置全局 优先度低 没有数据时回调
     */
    public static void initNoDataListener(NoDataListener noDataListener) {
        sNoDataListener = noDataListener;
    }

    /**
     * 设置当前 优先度高 没有数据时回调
     */
    protected final void setNoDataListener(NoDataListener noDataListener) {
        mNoDataListener = noDataListener;
    }
}

