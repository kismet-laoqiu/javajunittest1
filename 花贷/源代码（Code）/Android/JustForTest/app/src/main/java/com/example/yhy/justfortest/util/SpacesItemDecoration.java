package com.example.yhy.justfortest.util;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {


    private int space; //间隔
    private StaggeredGridLayoutManager.LayoutParams lp;

    public SpacesItemDecoration(int spacing) {
        this.space = spacing;
    }


        //这里是关键，需要根据你有几列来判断
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
        {
            int position = parent.getChildAdapterPosition(view);

            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;

            if (position == 0) {
                // 用于设第一个位置跟顶部的距离
                outRect.top = space;
            } else {
                lp = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
                if (lp.getSpanIndex() == 0) {
                    // 用于设同行两个间隔间跟其距离左右屏幕间隔相同
                    outRect.right = 0;
                }
            }


    }

}