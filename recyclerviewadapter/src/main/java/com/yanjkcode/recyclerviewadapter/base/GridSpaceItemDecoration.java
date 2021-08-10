package com.yanjkcode.recyclerviewadapter.base;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Author : YanJk
 * Time : 2021/6/29 15:49
 * <p>
 * Description : RecyclerView列表 网格布局 间隔
 */
@NotProguard
public class GridSpaceItemDecoration extends RecyclerView.ItemDecoration {
    private final int mSpanCount;//横条目数量
    private final int mRowSpacing;//行间距
    private final int mColumnSpacing;// 列间距

    /**
     * @param spanCount     列数 dp
     * @param rowSpacing    行间距 dp
     * @param columnSpacing 列间距 dp
     */
    public GridSpaceItemDecoration(@NonNull Context context, int spanCount, int rowSpacing, int columnSpacing) {
        this.mSpanCount = spanCount;
        this.mRowSpacing = dip2px(context, rowSpacing);
        this.mColumnSpacing = dip2px(context, columnSpacing);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // 获取view 在adapter中的位置。
        int column = position % mSpanCount; // view 所在的列

        outRect.left = column * mColumnSpacing / mSpanCount; // column * (列间距 * (1f / 列数))
        outRect.right = mColumnSpacing - (column + 1) * mColumnSpacing / mSpanCount; // 列间距 - (column + 1) * (列间距 * (1f /列数))

        // 如果position > 行数，说明不是在第一行，则不指定行高，其他行的上间距为 top=mRowSpacing
        if (position >= mSpanCount) {
            outRect.top = mRowSpacing; // item top
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}