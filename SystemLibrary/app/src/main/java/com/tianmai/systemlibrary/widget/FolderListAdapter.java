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

public class FolderListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    List<FolderItem>        mItems= new ArrayList<>();
    public FolderListAdapter(List items){
        mItems = items;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.folder_list_item, null);
        FolderViewHoder myViewHoder = new FolderViewHoder(view);
        return myViewHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FolderItem item =mItems.get( position);
        ((FolderViewHoder)holder).mTitle.setText( item.getTitle());
        FolderItem.OnClickListener listener =item.getOnClickListener();
        ((FolderViewHoder) holder).layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 listener.onClick( item);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    static class FolderViewHoder extends RecyclerView.ViewHolder {
        TextView   mTitle;
        LinearLayout  layout;
        public FolderViewHoder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById( R.id.item_name);
            layout = itemView.findViewById(R.id.layout_container);
        }

    }
}
