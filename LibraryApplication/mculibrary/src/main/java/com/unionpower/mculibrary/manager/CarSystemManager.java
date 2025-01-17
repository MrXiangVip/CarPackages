package com.unionpower.mculibrary.manager;


import static com.unionpower.mculibrary.mcu.McuType.CAR_SYSTEM;
import static com.unionpower.mculibrary.mcu.McuType.TYPE_CAR_SYSTEM;

import android.util.Log;

import com.unionpower.mculibrary.listener.IMcuBaseListener;
import com.unionpower.mculibrary.bean.CarSystemData;
import com.unionpower.mculibrary.listener.ICarSystemListener;
import com.unionpower.mculibrary.mcu.McuSubType;
import com.unionpower.mculibrary.mcu.McuType;
import com.unionpower.mculibrary.mcu.McuUtil;
import com.unionpower.server.McuManagerBase;

import java.io.File;

public class CarSystemManager extends McuManagerBase implements IMcuBaseListener {
    private static final String TAG = "CarSystemManager.";
    private static CarSystemManager mInstance;
    private static ICarSystemListener systemListener;
    private CarSystemManager( ){
        super();

    }
    public static CarSystemManager getInstance(){
        if( mInstance == null ){
            mInstance = new CarSystemManager();
        }
        return  mInstance;
    }
    public void setSystemManagerListener(ICarSystemListener mListener){
        mInstance.addCallbackListener( CAR_SYSTEM, this );
        systemListener = mListener;
    }
    public void removeSystemManagerListener( ){
        mInstance.removeCallbackListener( CAR_SYSTEM);
        systemListener = null;
    }
    @Override
    public void onMcuDataCbk(byte[] data) {
        Log.d(TAG, "报文长度 "+data.length+" 报文体: "+ McuUtil.bytesToHexString(data));
        int subType = data[0];
        Log.d(TAG,"subType "+McuUtil.intsToHexString(subType));
        int dataLength = data.length;
        if(dataLength < 2){
            return;
        }
        byte[] bDate = new byte[dataLength -1];
        for(int i=1; i<dataLength -1; i++){
            bDate[i -1] = data[i]; // 获取data数据并赋值
        }
        if( systemListener != null ){
            CarSystemData systemData = new CarSystemData(bDate, McuSubType.intToSubCarSystemEnum(subType));
            systemListener.onSystemCallback( systemData, subType );
        }
    }

    /*
    *  函数功能: 系统数据， 发送握手信号
    * 参数：
    * 返回值：
    * */
    public void sendHandshakeSignal( ){
//         byte[]  data= {0x01};
//         mInstance.sendUnPackedBytesToMcu( TYPE_CAR_SYSTEM, SUB_TYPE_SYSTEM_HAND_SHAKE, data);

    }

    /*
    * 函数功能： 系统数据, 接收MCU 版本信息
    * 参数：
    * 返回值：
    * */
    public String receiveMcuVersionInfo(  ){

        return "1.10.0";
    }
    /*
    * 函数功能： 系统数据, 接收MCU ACC 状态
    * 参数:
    * 返回值 ：
    * */
    public String receiveMcuAccInfo( ){
        String data ="on/off";
        return  data;
    }

    /*
    * 函数功能： 系统数据， 同步安卓时间到mcu
    * 参数：
    * 返回值:
    * */
    public void pushAndroidTimeToMcu( ){

    }

    /*
    * 函数功能: 获取mcu版本号
    * */
    public void fetchMcuVersion(){
        byte data[]={0x01};
        mInstance.sendUnPackedBytesToMcu(TYPE_CAR_SYSTEM, McuSubType.SUB_TYPE_CAR_SYSTEM.MCU_VERSION.getValue(),data );
    }

/*
*  函数功能: 发送 360 快速数据请求
*  DATA0 : 0x01 请求 360 快起数据, 0x02 停止发送360 快速数据
* */
    public void request360QuickData(){
        byte data[]={0x01};
        mInstance.sendUnPackedBytesToMcu( TYPE_CAR_SYSTEM, McuSubType.SUB_TYPE_CAR_SYSTEM.CAR_360.getValue(), data);
    }




}
