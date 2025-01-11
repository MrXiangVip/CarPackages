package com.tianmai.systemlibrary.widget;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tianmai.systemlibrary.R;

import java.util.ArrayList;
import java.util.List;

public class WifiAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder>{
    List<WifiItem> mItems= new ArrayList<>();
    public WifiAdapter(List items){
        mItems = items;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.wifi_list_item, null);
        WifiAdapter.WifiViewHoder myViewHoder = new WifiAdapter.WifiViewHoder(view);
        return myViewHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        WifiItem item =mItems.get( position);
        ((WifiViewHoder)holder).mTitle.setText( item.ssid);
        ((WifiViewHoder)holder).mSummary.setText( item.bssid);
        WifiItem.OnClickListener listener =item.getOnClickListener();
        ((WifiViewHoder) holder).layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick( item );
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    static class WifiViewHoder extends RecyclerView.ViewHolder {
        TextView mTitle;
        TextView mSummary;
        LinearLayout layout;
        public WifiViewHoder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById( R.id.name);
            mSummary = itemView.findViewById(R.id.summary);
            layout = itemView.findViewById(R.id.layout_container);
        }

    }
}
