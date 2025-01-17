package com.unionpower.mculibrary.manager;

import static com.unionpower.mculibrary.mcu.McuType.IO_STATUS;

import android.util.Log;

import com.unionpower.mculibrary.bean.IOStatus;
import com.unionpower.mculibrary.listener.IIOStatusListener;
import com.unionpower.mculibrary.listener.IMcuBaseListener;
import com.unionpower.mculibrary.mcu.McuUtil;
import com.unionpower.server.McuManagerBase;

/*  */
public class IOStatusManager extends McuManagerBase implements IMcuBaseListener {


    private static IOStatusManager mInstance;
    private static IIOStatusListener listener;

    private String TAG ="IOStatusManager";
    private IOStatusManager( ){
        super();

    }
    public static  IOStatusManager getInstance( ){
        if( mInstance == null ){
            mInstance = new IOStatusManager();
        }
        return  mInstance;
    }
    public  void setIOSystemListener(IIOStatusListener mListener){
        mInstance.addCallbackListener( IO_STATUS, this);
        listener = mListener;
    }
    public void removeIOSystemListener( ){
        listener = null;
        mInstance.removeCallbackListener( IO_STATUS );
    }

    @Override
    public void onMcuDataCbk(byte[] data) {
        Log.d(TAG, "报文长度 "+data.length+" 报文体: "+ McuUtil.bytesToHexString(data));
        if( listener != null ){
            IOStatus  ioStatus = new IOStatus( data);
            listener.onIOStatusCallback(ioStatus);
        }
    }


}
