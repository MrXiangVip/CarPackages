package com.unionpower.mculibrary.manager;

import static com.unionpower.mculibrary.mcu.McuType.MCU_UPDATE;

import android.util.Log;

import com.unionpower.mculibrary.listener.IMcuBaseListener;
import com.unionpower.mculibrary.listener.IMcuUpdateListener;
import com.unionpower.mculibrary.mcu.McuType;
import com.unionpower.mculibrary.mcu.McuUtil;
import com.unionpower.mculibrary.mcu.McuSubType;
import com.unionpower.mculibrary.mcu.ByteUtils;

import com.unionpower.server.McuManagerBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class McuUpdateManager extends McuManagerBase implements IMcuBaseListener {


    private static McuUpdateManager  mInstance;
    private static IMcuUpdateListener  listener;
    private String TAG ="McuUpdateManager.";

    private static final int ONE_PACK=128;

    public static final int TO_MCU_NORMAL =0x00;// 正常模式
    public static final int TO_MCU_START_UPDATE =0x01;// 请求进入升级
    public static final int TO_MCU_UPDATING =0x02;// 进入升级
    public static final int TO_MCU_QUERY =0x03;// 查看当前 mcu模式

    public static final int FROM_MCU_UPDATING=0x02;// 升级中
    public static final int FROM_MCU_UPDATE_SUCCESS =0x03;// 升级完成 之后断电重启
    public static final int  FROM_MCU_UPDATE_START=0x04; //请求升级
    public static final int  FROM_MCU_UPDATE_FAIL=0x05;// 升级错误 checksum 不对, 要断电重来
    public static final int  FROM_MCU_APP_IAP_START = 0x06;// mcu 开始从 app 切换到 iap
    public static final int  FROM_MCU_APP_IAP_OVER = 0x07;// MCU从app切换到IAP 完成
    public static final int  FROM_MCU_MODE = 0x08;// 当前mcu 模式

    private List<byte[]>  fileBytes;// 文件字节

    byte fileCheckSum =0x00;//文件校验位

    private enum  MCU_STATUS{
        APP,  //app 模式
        IAP // iap 升级模式
    }
    private  MCU_STATUS  mcuStatus = MCU_STATUS.APP;
    private int currentFrame=0;// 当前帧
    private McuUpdateManager(){
        super();
    }
    public static McuUpdateManager getInstance(){
        if( mInstance == null){
            mInstance = new McuUpdateManager();
        }
        return mInstance;
    }
    public void setMcuUpdateListener(IMcuUpdateListener mListener){
        mInstance.addCallbackListener( MCU_UPDATE, this);
        listener  =mListener;
    }
    public void removeMcuUpdateListener( ){
        listener = null;
        mInstance.removeCallbackListener( MCU_UPDATE);
    }

    @Override
    public void onMcuDataCbk(byte[] data) {
        Log.d(TAG, "onMcuDataCbk  "+ McuUtil.bytesToHexString(data));
        int type = data[0];
        switch ( type ){
            case FROM_MCU_UPDATING:
            case FROM_MCU_UPDATE_START:
                sendMcuUpdateData();
                break;
            case FROM_MCU_UPDATE_SUCCESS:
                finishMcuUpdate( true);
                break;
            case FROM_MCU_UPDATE_FAIL:
                finishMcuUpdate( false);
                break;
            case FROM_MCU_APP_IAP_START:
                Log.d(TAG, "mcu 开始 从app 切换到 iap");
                break;
            case FROM_MCU_APP_IAP_OVER:
                Log.d(TAG, "mcu 从app 切换到 iap完成");
                if( mcuStatus ==MCU_STATUS.APP ){
                    startMcuUpdate();
                    mcuStatus = MCU_STATUS.IAP;
                }
                break;
            default:
                Log.d(TAG, "unkown type");
                break;
        }
    }

    /*
    *  函数功能: 开始mcu 升级
    *  TYPE :0x7a , SUBTYPE: 0x01
    *   DATA ：0 正常工作状态
    *   DATA : 1  总数据帧数 低8位
    *   DATA ：2 总数据帧数 高8位
    *   DATA : 3  checkesum  低8位
    *   DATA :4  checksum 高8位
    * */
    public boolean startMcuUpdate(File file){
        Log.d(TAG, "startMcuUpdate "+file!=null?file.getAbsolutePath():"文件不能为空");
        if( file !=null && file.exists() ){
            fileBytes = new ArrayList<>();
            byte[]  buffer = new byte[ONE_PACK];
            try{
                InputStream in = new FileInputStream( file );
                while( in.read(buffer) >0){
                    fileBytes.add( buffer);
                    for (int i=0; i<buffer.length; i++){
                        fileCheckSum +=buffer[i];
                    }
//                    Arrays.fill( buffer, (byte)0);
                    buffer = new byte[ONE_PACK];
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            if( listener != null){
                listener.onMcuUpdateStart( false);
            }
            return  false;
        }
        int mcuTotalFrame = fileBytes.size();
        byte[]  sumTotalFrame = ByteUtils.convertIntToByte2(mcuTotalFrame);

        byte[] data ={TO_MCU_START_UPDATE, sumTotalFrame[1], sumTotalFrame[0], fileCheckSum, 0x00};
        mInstance.sendUnPackedBytesToMcu(McuType.TYPE_MCU_UPDATE, McuSubType.SUB_TYPE_MCU_UPDATE_REQ,data);
        if( listener != null){
            listener.onMcuUpdateStart( true);
        }
        return  true;
    }
    public boolean startMcuUpdate(){
        Log.d(TAG, "startMcuUpdate");
        int mcuTotalFrame = fileBytes.size();
        byte[]  sumTotalFrame = ByteUtils.convertIntToByte2(mcuTotalFrame);

        byte[] data ={TO_MCU_START_UPDATE, sumTotalFrame[1], sumTotalFrame[0], fileCheckSum, 0x00};
        mInstance.sendUnPackedBytesToMcu(McuType.TYPE_MCU_UPDATE, McuSubType.SUB_TYPE_MCU_UPDATE_REQ,data);
        if( listener != null){
            listener.onMcuUpdateStart( true);
        }
        return  true;
    }
    /*
    *  DATA 0: 当前帧数 低8位
    *  DATA 1: 当前帧数 高8位
    *  ... byte0-byte127
    * */
    public void sendMcuUpdateData( ){
        Log.d(TAG, "sendMcuUpdateData");
        if( fileBytes.size() >currentFrame ){
            byte[]  mcuFileBytes =fileBytes.get( currentFrame );
            byte[]  currentFrameByte = ByteUtils.convertIntToByte2( currentFrame+1);
            byte[]  data = new byte[ONE_PACK+2];

            data[0] = currentFrameByte[1];
            data[1] = currentFrameByte[0];
            for( int i=0; i<mcuFileBytes.length; i++){
                data[2+i] = mcuFileBytes[i];
            }
            currentFrame +=1;
            sendUnPackedBytesToMcu( McuType.TYPE_MCU_UPDATE, McuSubType.SUB_TYPE_MCU_UPDATE_DATA, data);
        }else{
            Log.d(TAG, ""+fileBytes.size());
        }
        if( listener != null){
            listener.onMcuUpdating( fileBytes.size(), currentFrame);
        }

    }

    public void  finishMcuUpdate( boolean flag){
        Log.d(TAG, "finish Mcu update"+flag);
        if( listener !=null){
            listener.onMcuUpdated( flag);
        }
    }
}
