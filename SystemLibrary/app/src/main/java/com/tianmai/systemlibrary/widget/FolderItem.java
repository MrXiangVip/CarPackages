package com.tianmai.systemlibrary.widget;

import android.text.Layout;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

public class FolderItem {

    public String mTitle; // 标题
    private OnClickListener mOnClickListener;
    public LinearLayout     container;
    public void setTitle(String name) {
        mTitle = name;
    }
    public String getTitle(){
        return mTitle;
    }
    public void setOnItemClickedListener(OnClickListener listener) {
        mOnClickListener = listener;
    }

    public OnClickListener getOnClickListener( ){
        return  mOnClickListener;
    }
    public interface OnClickListener {
        /**
         * Called when the item has been clicked.
         *
         * @param item whose checked state changed.
         */
        void onClick( FolderItem item);
    }
}
