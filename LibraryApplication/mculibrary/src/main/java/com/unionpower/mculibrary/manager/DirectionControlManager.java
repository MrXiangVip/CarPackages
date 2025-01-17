package com.unionpower.mculibrary.manager;

import static com.unionpower.mculibrary.mcu.McuType.DIRECTION_CONTROL;

import android.util.Log;

import com.unionpower.mculibrary.listener.IDirectionControlListener;
import com.unionpower.mculibrary.bean.DirectionControl;
import com.unionpower.mculibrary.listener.IMcuBaseListener;
import com.unionpower.mculibrary.mcu.McuUtil;
import com.unionpower.server.McuManagerBase;

/*  方控 */
public class DirectionControlManager extends McuManagerBase  implements IMcuBaseListener {

    private static DirectionControlManager mInstance;
    private static IDirectionControlListener listener;
    private String TAG="DirectionControlManager.";

    private DirectionControlManager( ){
        super();
    }

    public static  DirectionControlManager getInstance( ){
        if( mInstance == null ){
            mInstance = new DirectionControlManager();
        }
        return  mInstance;
    }
    public void setDirectionControlListener(IDirectionControlListener mListener){
        listener = mListener;
        mInstance.addCallbackListener( DIRECTION_CONTROL, this );
    }
    public void removeDirectionControlListener( ){
        listener = null;
        mInstance.removeCallbackListener( DIRECTION_CONTROL);
    }

    /*
    * 函数功能： 发送方控指令到mcu
    * 参数
    * 返回值
    * */
    public void sendDirectionControlData() {

    }


    @Override
    public void onMcuDataCbk(byte[] data) {
        Log.d(TAG, "报文长度 "+data.length+" 报文体: "+ McuUtil.bytesToHexString(data));
        if( listener!= null ){
            DirectionControl directionControl = new DirectionControl(data);
            listener.onDirectionControlCallback( directionControl);
        }
    }
}
