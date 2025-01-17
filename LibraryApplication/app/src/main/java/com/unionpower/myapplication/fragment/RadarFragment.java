package com.unionpower.myapplication.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.unionpower.mculibrary.bean.Radar;
import com.unionpower.mculibrary.listener.IDefrostListener;
import com.unionpower.mculibrary.listener.IRadarListener;
import com.unionpower.mculibrary.manager.RadarDataManager;
import com.unionpower.mculibrary.mcu.McuSubType;
import com.unionpower.mculibrary.mcu.McuType;
import com.unionpower.mculibrary.mcu.McuUtil;
import com.unionpower.myapplication.R;

public class RadarFragment extends Fragment implements View.OnClickListener{


    private String TAG="Radar";
    IRadarListener  listener = new IRadarListener() {
        @Override
        public void onRadarCallback(Radar radar) {
            Log.d(TAG , radar.toString());
        }
    };
    RadarDataManager  radarDataManager;
    Button  getContent;
    TextView   contentFrame;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_rader_data, container, false);
        return  view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        radarDataManager = RadarDataManager.getInstance();
        radarDataManager.setRadarDataListener( listener);

        getContent = view.findViewById( R.id.getContent);
        getContent.setOnClickListener( this );
        contentFrame = view.findViewById( R.id.contentFrame);
    }



    @Override
    public void onClick(View v) {
        if( v.getId() == R.id.getContent ){
            byte[]  content = radarDataManager.getCachedMcuData(McuType.TYPE_RADAR_DATA, McuSubType.SUB_TYPE_RADAR_DATA);
            if( content !=null ){
                Log.d(TAG, "缓存  长度:"+content.length+" 内容: "+ McuUtil.bytesToHexString( content) );
                contentFrame.setText( McuUtil.bytesToHexString( content));
            }
        }
    }
}
