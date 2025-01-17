package com.unionpower.mculibrary.manager;

import static com.unionpower.mculibrary.mcu.McuType.AIR_CONTAINER;
import static com.unionpower.mculibrary.mcu.McuType.BYTE_TRANSMIT;

import android.util.Log;

import com.unionpower.mculibrary.listener.IAirContainerListener;
import com.unionpower.mculibrary.listener.IByteTransmitListener;
import com.unionpower.mculibrary.listener.IMcuBaseListener;
import com.unionpower.mculibrary.mcu.McuUtil;
import com.unionpower.server.McuManagerBase;

/*   */
public class ByteTransmitManager extends McuManagerBase implements IMcuBaseListener {


    private static ByteTransmitManager mInstance;
    private IByteTransmitListener listener;

    private String TAG ="ByteTransmitManager";
    private ByteTransmitManager( ){
        super();
    }

    public static ByteTransmitManager getInstance( ){
        if( mInstance == null ){
            mInstance = new ByteTransmitManager();
        }
        return  mInstance;
    }

    @Override
    public void onMcuDataCbk(byte[] data) {
        Log.d(TAG, "报文长度 "+data.length+" 报文体: "+ McuUtil.bytesToHexString(data));
        if( listener != null ){
            listener.onTransmitCallback( data);
        }
    }

    public void sendBytesToMcu(  int type, int subType, byte[] data){
        mInstance.sendUnPackedBytesToMcu( type, subType, data );
    }
    public void setByteTransmitListener(IByteTransmitListener mListener){
        mInstance.addCallbackListener( BYTE_TRANSMIT, this );
        listener = mListener;
    }
    public void removeByteTransmitListener( ){
        listener = null;
        mInstance.removeCallbackListener( BYTE_TRANSMIT);
    }

}
