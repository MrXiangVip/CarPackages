package com.unionpower.myapplication.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.unionpower.mculibrary.listener.ITirePressureListener;
import com.unionpower.mculibrary.manager.TirePressureManager;
import com.unionpower.mculibrary.mcu.McuSubType;
import com.unionpower.mculibrary.mcu.McuType;
import com.unionpower.mculibrary.mcu.McuUtil;
import com.unionpower.myapplication.R;

public class TirePressureFragment extends Fragment implements View.OnClickListener{

    ITirePressureListener   listener= new ITirePressureListener() {
        @Override
        public void onTirePressureCallback(ArrayMap tirePressure) {

        }
    };
    TirePressureManager  tirePressureManager;

    Button   getContent;
    TextView contentFrame;
    private static String TAG ="TirePressure";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tire_pressure, container, false);
        return  view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tirePressureManager = TirePressureManager.getInstance();
        tirePressureManager.addTirePressureListener( listener);

        getContent = view.findViewById(R.id.getContent);
        getContent.setOnClickListener( this );
        contentFrame = view.findViewById( R.id.contentFrame );
    }



    @Override
    public void onClick(View v) {
        if( v.getId() == R.id.getContent ){
            byte[]  content = tirePressureManager.getCachedMcuData(McuType.TYPE_TIRE_PRESSURE, McuSubType.SUB_TYPE_TIRE_PRESSURE);
            if( content !=null ){
                Log.d(TAG, "缓存  长度:"+content.length+" 内容: "+ McuUtil.bytesToHexString( content) );
                contentFrame.setText( McuUtil.bytesToHexString( content));
            }
        }
    }
}
