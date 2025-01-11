package com.tianmai.systemlibrary.widget;

import android.widget.LinearLayout;

public class WifiItem {
    public String ssid ;
    public String bssid ;
    public int rssi ;

    private OnClickListener mOnClickListener;
    public LinearLayout container;
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
        void onClick( WifiItem item);
    }

    public String toString( ){
        return ssid +bssid +rssi;
    }
}
