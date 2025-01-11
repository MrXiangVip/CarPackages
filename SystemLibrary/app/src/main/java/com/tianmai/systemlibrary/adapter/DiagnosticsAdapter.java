package com.tianmai.systemlibrary.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianmai.systemlibrary.R;
import com.tianmai.systemlibrary.bean.DiagnosticsBean;
import com.tianmai.systemlibrary.utils.ConfigDatas;
import com.tianmai.systemlibrary.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class DiagnosticsAdapter extends BaseAdapter {

    private final  String  TAG = Utils.TAG+"FactoryBaseAdapter";
    public LayoutInflater mInflater;
    public Context mContext;
    public List<DiagnosticsBean>  mFactoryDatas= new ArrayList<>();

    public DiagnosticsAdapter(Context context){
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mFactoryDatas = ConfigDatas.getInstance(context).getListFactoryBean();
        Log.d(TAG, "Data Size: "+mFactoryDatas.size());
    }

    @Override
    public int getCount() {
        if( mFactoryDatas != null) {
            return mFactoryDatas.size();
        }else{
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return mFactoryDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.grid_item_layout, parent, false); //加载布局
            holder = new ViewHolder();
            holder.linearLayout = convertView.findViewById( R.id.item_layout);
            holder.title = (TextView) convertView.findViewById(R.id.item_title);

            convertView.setTag(holder);
        } else {   //else里面说明，convertView已经被复用了，说明convertView中已经设置过tag了，即holder
            holder = (ViewHolder) convertView.getTag();
        }

        DiagnosticsBean bean = mFactoryDatas.get(position);
//        Log.d(TAG, "getView: "+bean.toString());
        holder.title.setText( mContext.getResources().getString(bean.getTitleID() ) );
        if( bean.getStatus() == Utils.FAILED ){
//            holder.linearLayout.setBackgroundResource( R.drawable.bk_fail );
            holder.linearLayout.setBackgroundColor(Color.RED  );
        } else if (bean.getStatus() == Utils.SUCCESS) {
//            holder.linearLayout.setBackgroundResource( R.drawable.bk_true );
            holder.linearLayout.setBackgroundColor( Color.GREEN );
        }else{
//            holder.linearLayout.setBackgroundResource( R.drawable.bk_press );
            holder.linearLayout.setBackgroundColor( Color.GRAY );
        }
        return convertView;
    }

    public DiagnosticsBean  getItemBean(int postion){
        if( mFactoryDatas != null ) {
            return mFactoryDatas.get(postion);
        }else{
            return null;
        }
    }
    public class ViewHolder {
        ImageView image;
        TextView title;
        LinearLayout linearLayout;
    }
}
