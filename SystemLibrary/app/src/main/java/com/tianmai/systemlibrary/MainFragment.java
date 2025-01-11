package com.tianmai.systemlibrary;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.tianmai.systemlibrary.adapter.DiagnosticsAdapter;
import com.tianmai.systemlibrary.bean.DiagnosticsBean;

public class MainFragment extends Fragment  {

    private DiagnosticsAdapter mAdapter;
    private GridView mGridView;
    private String TAG;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return  view;
    }
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initGridView(view);
    }

    private void initGridView(View mContentView ) {
        Log.d(TAG, "initGridView: ");
        mGridView = mContentView.findViewById( R.id.grid_factory );

        //为数据绑定适配器
        mAdapter = new DiagnosticsAdapter(getContext());
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: "+ position+" : " +id);
                DiagnosticsBean bean = mAdapter.getItemBean( position );
                ((MainActivity)getActivity()).pushBackFragment( bean.getFragment(), null);
            }
        });
    }
}
