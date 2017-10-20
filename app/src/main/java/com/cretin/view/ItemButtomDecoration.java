package com.cretin.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cretin.utils.CommonUtil;


public class ItemButtomDecoration extends RecyclerView.ItemDecoration {
    private Context mContext;

    public ItemButtomDecoration(Context context) {
        mContext = context;
    }

    /**
     * @param outRect 边界
     * @param view    recyclerView ItemView
     * @param parent  recyclerView
     * @param state   recycler 内部数据管理
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //设定底部边距为10px
        if ( parent.getChildLayoutPosition(view) == state.getItemCount() - 1
                || parent.getChildLayoutPosition(view) == -1 ) {
            outRect.set(0, 0, 0, 0);
        } else {
            outRect.set(0, 0, 0, ( int ) (CommonUtil.getScreenScale(mContext) * 10));
        }
    }
}
