package com.unionpower.mculibrary.bean;

import android.util.Log;

import com.unionpower.mculibrary.mcu.McuUtil;

import java.nio.ByteBuffer;

public class TirePressure {

    private final String TAG="TirePressure";
    /*
    *  0：0-3 轮胎位置  4-7 轴位置
    *  1: 0-7 轮胎压力
    *  2-3: 0-15 轮胎温度
    *  4: 0-1 车轮传感器状态  2-3 轮胎状态  4-5 轮胎高温警告
    *  5: 0-1 防爆胎 应急装置状态
    *  6: 保留
    *  7: 压力阀检测 0-7
    * */
    byte[]  bytes = new byte[8];
    int  tirePosition;  // 轮胎位置
    int  axisPostion;   // 轴位置
    int  tirePressure;// 胎压
    int  temperature;   //轮胎温度
    boolean tireSensorEnable;// 轮胎sensor 状态
    boolean  tireLeak;  //轮胎是否漏气
    boolean tireHighTemper;// 轮胎是否温度过高
    boolean isRunflatInstalled;// 是否安装 防爆胎 应急装置

    byte   pressureValue; // 00000000 超高压 超30%  ,00100000  高压 超20%， 01000000 正常压力, 01100000 低压 低20, 10000000 超低压 低30% , 10100000 未定义, 11000000 传感器自检
    public TirePressure(byte[] data){
        Log.d(TAG, " data "+ McuUtil.bytesToHexString(data));
        tirePosition = (int)(data[0] &0B00001111);
        axisPostion  = (int)(data[0] &0B11110000);
        tirePressure = (int)data[1];
//        byte[] temper = {data[2], data[3]};
//        temperature = McuUtil.byteArrayToIntLittleEndian(temper, 0);
        if( (data[4] &0B00000001)>0 ){
            tireSensorEnable = true;
        }else{
            tireSensorEnable = false;
        }
        if( (data[4] &0B00000100)>0 ){
            tireLeak = true;
        }else{
            tireLeak = false;
        }
        if( (data[4] &0B00010000)>0){
            tireHighTemper = true;
        }else{
            tireHighTemper = false;
        }

        if( (data[5] &0B00000001) >0 ){
            isRunflatInstalled =true;
        }else{
            isRunflatInstalled = false;
        }

        pressureValue = data[7];
    }

    public String getKey( ){
        return tirePosition+""+axisPostion;
    }
}
