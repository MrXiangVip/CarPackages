package com.unionpower.server;

import static com.unionpower.mculibrary.mcu.McuType.AIR_CONTAINER;
import static com.unionpower.mculibrary.mcu.McuType.BYTE_TRANSMIT;
import static com.unionpower.mculibrary.mcu.McuType.DIRECTION_CONTROL;
import static com.unionpower.mculibrary.mcu.McuType.FAULT_CODE;
import static com.unionpower.mculibrary.mcu.McuType.MCU_UPDATE;
import static com.unionpower.mculibrary.mcu.McuType.RADAR_DATA;
import static com.unionpower.mculibrary.mcu.McuType.CAR_SYSTEM;
import static com.unionpower.mculibrary.mcu.McuType.TIRE_PRESSURE;
import static com.unionpower.mculibrary.mcu.McuType.VIRTUAL_SWITCH;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.ArrayMap;
import android.util.Log;

import com.unionpower.mculibrary.listener.IMcuBaseListener;
import com.unionpower.mculibrary.mcu.McuType;
import com.unionpower.mculibrary.mcu.McuUtil;

public class McuServer {

    static {
        try {
            System.loadLibrary("mcuserver_jni");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static McuServer  mInstance;
    private static EventHandler mEventHandler;

    private static String TAG="McuServer.";

    private static ArrayMap<String, IMcuBaseListener>   sListeners= new ArrayMap<>();
    public static McuServer  getInstance(){

            if( mInstance == null  ){
                mInstance = new McuServer();
            }
            if( mEventHandler == null){
                mEventHandler = new EventHandler();
            }
            return  mInstance;
    }

    private  McuServer( ){
            init( );
    }

    private boolean init( ){

            boolean  ret = _init();
            return  ret;
    }
    public static boolean sendMcuData(byte[] data) {
        return mInstance._sendMcuData(data);
    }

    /*
    * 函数功能: 获取缓存在 mcuserver 进程里的字节数组.
    * 输入参数: type 报文主类型,  subType 报文子类型
    * 返回值: 对应报文的字节数组,  如果 报文type和subType 对应的缓存不存在 返回空值
    * */
    public  static byte[]  getCachedMcuData( int type, int subType){
        Log.d(TAG, "请求缓存: "+type +" "+ subType);
        byte[] cache =mInstance._getMcuData(type, subType);
        Log.d(TAG, "返回的缓存字节数组: length: "+ cache.length+ " value: "+McuUtil.bytesToHexString(cache));
        return  cache;
    }
    public static boolean sendMcuCommand(int type, int subType, byte[] data) {
        return mInstance._sendMcuCommand(type, subType, data);
    }

    public boolean sendPackedBytesToMcu( byte[] data ){
//        return  mInstance._sendPackedBytesToMcu( data);
        return  mInstance._sendMcuData( data);
    }


    public boolean  sendUnPackedBytesToMcu(  int type, int subType, byte[] data ){
//        return mInstance._sendUnPackedBytesToMcu( type, subType, data);
        Log.d(TAG, "发往MCU ->Type "+McuUtil.intsToHexString(type)+" SubType "+McuUtil.intsToHexString(subType)+" 内容: "+McuUtil.bytesToHexString(data));
        return mInstance._sendMcuCommand( type, subType, data);
    }

    public static void addCallbackListener( String key, IMcuBaseListener listener){
        sListeners.put( key, listener);
    }
    public static void removeCallbackListener( String key){
        sListeners.remove( key);
    }
    private static class EventHandler extends Handler {

        private EventHandler() {
            super(Looper.getMainLooper());
        }

        @Override
        public void handleMessage(Message msg) {
//            final OnMcuServerListener listener = sWeakListener == null ? null : sWeakListener.get();
            Log.d(TAG, "报文 TYPE: 0x"+ McuUtil.intsToHexString(msg.arg1)  );
            IMcuBaseListener listener=null;
            switch (msg.arg1) {

                case McuType.TYPE_CAR_SYSTEM:
                    listener = sListeners.get( CAR_SYSTEM );
                    break;
                case McuType.TYPE_AIR_CONTAINER:
                    listener = sListeners.get(AIR_CONTAINER );
                    break;
                case McuType.TYPE_DIRECTION_CONTROL:
                    listener = sListeners.get( DIRECTION_CONTROL);
                    break;
                case McuType.TYPE_TIRE_PRESSURE:
                    listener = sListeners.get( TIRE_PRESSURE);
                    break;
                case McuType.TYPE_RADAR_DATA:
                    listener = sListeners.get( RADAR_DATA );
                    break;
                case McuType.TYPE_FAULT_CODE:
                    listener = sListeners.get( FAULT_CODE );
                    break;
                case McuType.TYPE_VIRTUAL_SWITCHER:
                    listener = sListeners.get( VIRTUAL_SWITCH );
                    break;
                case McuType.TYPE_MCU_UPDATE:
                    listener = sListeners.get( MCU_UPDATE);
                    break;
                default:
                    break;
            }
            if( listener != null ){
                listener.onMcuDataCbk( (byte[]) msg.obj );
            }
            //增加一条透传回传
            listener =sListeners.get(BYTE_TRANSMIT);
            if( listener != null ){
                listener.onMcuDataCbk( (byte[])msg.obj);
            }
        }
    }


    /* */
    private static void callbackFromNative(int msg, byte[] data, int value) {
//        final OnMcuServerListener listener = sWeakListener == null ? null : sWeakListener.get();
        if(mEventHandler != null) {
            Message.obtain(mEventHandler, 0, msg, value, data).sendToTarget();
        }
    }
    private native boolean _init();
    private native boolean _sendMcuData( byte[] data);
    private native byte[]  _getMcuData(int type, int subType);
    private native boolean _sendMcuCommand(int var1, int var2, byte[] var3);
    private native boolean _sendPackedBytesToMcu( byte[] data);
    private native boolean _sendUnPackedBytesToMcu(int type, int subType, byte[] data);
    private native boolean _sendCanData(byte[] data);
    private native boolean _sendHvacData(byte[] data);
    private native boolean _sendDtcData(int subType, byte[] data);
    private native boolean _getHvacData();
    private native String _getDefrostData();
    private native boolean _deinit();

}
