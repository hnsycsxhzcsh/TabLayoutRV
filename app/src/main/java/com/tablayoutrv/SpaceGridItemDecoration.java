package com.tablayoutrv;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by HARRY on 2018/12/18 0018.
 */

public class SpaceGridItemDecoration extends RecyclerView.ItemDecoration {


    private final int space;
    private final int num;
    private final int headerCount;

    public SpaceGridItemDecoration(int dimensionPixelOffset, int num, int rvHeaderCount) {
        this.space = dimensionPixelOffset;
        this.num = num;
        this.headerCount = rvHeaderCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int childPosition = parent.getChildLayoutPosition(view);
        if (childPosition >= headerCount) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams layoutParamsSta = (
                        StaggeredGridLayoutManager.LayoutParams) layoutParams;
                int spanIndex = layoutParamsSta.getSpanIndex();
                outRect.left = space;
                outRect.top = space;
                if (spanIndex == num - 1) {
                    outRect.right = space;
                } else {
                    outRect.right = 0;
                }
            }
        }
    }
}
