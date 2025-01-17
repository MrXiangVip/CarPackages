package com.unionpower.mculibrary.manager;

import android.util.ArrayMap;
import android.util.Log;

import com.unionpower.mculibrary.bean.TirePressure;
import com.unionpower.mculibrary.listener.IMcuBaseListener;
import com.unionpower.mculibrary.mcu.McuType;
import com.unionpower.mculibrary.listener.ITirePressureListener;
import com.unionpower.mculibrary.mcu.McuUtil;
import com.unionpower.server.McuManagerBase;

public class TirePressureManager extends McuManagerBase  implements IMcuBaseListener {

    private static TirePressureManager mInstance;
    private  static ITirePressureListener listener;
    private  String TAG ="TirePressureManager";
    private static ArrayMap< String, TirePressure>  tirePressureArrayMap= new ArrayMap<>();// 将轮胎信息放到 arraymap ,可以去重. key 使用tirePosition+""+axisPostion的组合
    private TirePressureManager( ){
        super();

    }
    public static TirePressureManager getInstance( ){
        if( mInstance == null ){
            mInstance = new TirePressureManager();
        }
        return  mInstance;
    }

    public void addTirePressureListener( ITirePressureListener mListener){
        mInstance.addCallbackListener(McuType.TIRE_PRESSURE, this );
        listener = mListener;
    }
    public void removeTirePressureListener( ){
        mInstance.removeCallbackListener( McuType.TIRE_PRESSURE );
        listener = null;
    }


    @Override
    public void onMcuDataCbk(byte[] data) {
        Log.d(TAG, "报文长度 "+data.length+" 报文体: "+ McuUtil.bytesToHexString(data));
        if( listener != null ){
            TirePressure tirePressure = new TirePressure(data);
            tirePressureArrayMap.put( tirePressure.getKey(), tirePressure);
            listener.onTirePressureCallback(tirePressureArrayMap);
        }
    }
}
