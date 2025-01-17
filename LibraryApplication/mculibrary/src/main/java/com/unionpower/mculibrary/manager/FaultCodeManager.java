package com.unionpower.mculibrary.manager;

import static com.unionpower.mculibrary.mcu.McuType.FAULT_CODE;

import android.util.Log;

import com.unionpower.mculibrary.bean.FaultCode;
import com.unionpower.mculibrary.listener.IMcuBaseListener;
import com.unionpower.mculibrary.listener.IFaultCodeListener;
import com.unionpower.mculibrary.mcu.McuUtil;
import com.unionpower.server.McuManagerBase;

/*  故障诊断信息 */
public class FaultCodeManager  extends McuManagerBase  implements IMcuBaseListener {
    private static FaultCodeManager mInstance;
    private static IFaultCodeListener   listener;

    private String TAG ="FaultCodeManager";
    private FaultCodeManager( ){
        super();

    }
    public static  FaultCodeManager getInstance( ){
        if( mInstance == null ){
            mInstance = new FaultCodeManager();
        }
        return  mInstance;
    }
    @Override
    public void onMcuDataCbk(byte[] data) {
        Log.d(TAG, "报文长度 "+data.length+" 报文体: "+ McuUtil.bytesToHexString(data));
        if( listener != null ){
            FaultCode faultCode = new FaultCode( data );
            listener.onFaultCodeCallback( faultCode );
        }
    }


    public  void setFaultCodeListener(IFaultCodeListener mListener){
        mInstance.addCallbackListener( FAULT_CODE, this);
        listener = mListener;
    }
    public void removeFaultCodeListener( ){
        mInstance.removeCallbackListener( FAULT_CODE );
        listener = null;
    }
    /*
    * 函数功能： 获取故障诊断信息
    * 参数：
    * 返回值：
    * */
    public void getFaultCode() {

    }

    /*
    * 函数功能 ：
    * */
    public void  getEngineDTCStateInfo( ){

    }

    public void getBrakingDTCStateInfo( ){

    }



}
