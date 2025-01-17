package com.unionpower.mculibrary.manager;

import static com.unionpower.mculibrary.mcu.McuType.RADAR_DATA;

import android.util.Log;

import com.unionpower.mculibrary.bean.Radar;
import com.unionpower.mculibrary.listener.IMcuBaseListener;
import com.unionpower.mculibrary.listener.IRadarListener;
import com.unionpower.mculibrary.mcu.McuUtil;
import com.unionpower.server.McuManagerBase;

/* 雷达 */
public class RadarDataManager extends McuManagerBase  implements IMcuBaseListener {

    private static RadarDataManager mInstance;
    private static IRadarListener listener;
    private String TAG ="RadarDataManager";
    private RadarDataManager( ){
        super();

    }
    public static  RadarDataManager getInstance( ){
        if( mInstance == null ){
            mInstance = new RadarDataManager();

        }
        return  mInstance;
    }


    @Override
    public void onMcuDataCbk(byte[] data) {
        Log.d(TAG, "报文长度 "+data.length+" 报文体: "+ McuUtil.bytesToHexString(data));
        if( listener != null ){
            Radar radar = new Radar( data);
            listener.onRadarCallback(  radar);
        }
    }

    public void setRadarDataListener(IRadarListener mListener){
        mInstance.addCallbackListener( RADAR_DATA, this);
        listener = mListener;
    }

    public void removeRadarDataListener( ){
        listener = null;
        mInstance.removeCallbackListener( RADAR_DATA);
    }



}
