package com.unionpower.mculibrary.bean;

import android.util.Log;

import com.unionpower.mculibrary.mcu.McuUtil;

public class DirectionControl {
    private final String TAG="DirectionControl";
//      0: 0-1 电话接听 00 无效 01 有效 , 2-3 电话挂断 00 有效 01 无效,   4-5 静音 00有效 01 无效,  6-7 not used
//      1: 0-1  音量 + 00 无效 01 有效, 2-3 音量 - 00 无效 01 有效, 4-5 上一曲 00 无效 01 有效, 6-7 下一曲 00无效 01 有效
//      2: 0-3 not used  , 4-5 车速加/恢复巡航 00 无效 01 有效 , 6-7 车速减/进入巡航 00 无效 01 有效
//      3: 0-1 not used, 2-3 巡航待命及退出 00 巡航退出 01 巡航待命 , 4-7 not used
//      4: 0-1 小计清零 00 无效 01 有效, 2-3 仪表翻页  00 无效 01 有效, 4-5 not used 6-7 not used
//      5-7 not used
    public byte[]  bytes = new byte[8];

    public boolean  answerPhoneCall;// 接听电话
    public boolean  endPhoneCall;// 挂断电话
    public boolean mutePhone;//静音
    public boolean increaseVolume;// 音量+
    public boolean decreaseVolume;//音量-
    public boolean preTrack;// 上一曲
    public boolean nextTrack;// 下一曲
    public boolean  increaseSpeed;// 车速+
    public boolean  decreaseSpeed;// 车速-
    public boolean  cruiseStandbyAndExit;// 巡航待命及退出
    public boolean clearSubtotal;// 小计清零
    public boolean pageTurnInstrument;// 仪表翻页
    public DirectionControl(byte[] data){
        Log.d(TAG , "directionControl "+ McuUtil.bytesToHexString(data));

        if( (data[0] &0B00000001) >0){ // 接听电话
            answerPhoneCall = true;
        }else{
            answerPhoneCall = false;
        }

        if( (data[0]&0B00000100) >0 ){// 挂断电话
            endPhoneCall = true;
        }else{
            endPhoneCall = false;
        }

        if( (data[0] &0B00010000) >0 ){//静音
            mutePhone = true;
        }else{
            mutePhone = false;
        }

        if( (data[1] &0B00000001 ) >0 ){// 音量+
            increaseVolume = true;
        }else{
            increaseVolume = false;
        }

        if( (data[1] &0B00000100 ) >0){// 音量 -
            decreaseVolume =true;
        }else{
            decreaseVolume = false;
        }
        if( (data[1] &0B00010000) >0){//上一曲
            preTrack = true;
        }else{
            preTrack = false;
        }

        if( (data[1] &0B01000000) >0){ // 下一曲
            nextTrack = true;
        }else{
            nextTrack = false;
        }

        if( (data[2] &0B00010000) >0){// 车速加
            increaseSpeed = true;
        }else{
            increaseSpeed = false;
        }

        if( (data[2] &0B01000000) >0){// 车速减
            decreaseSpeed = true;
        }else{
            decreaseSpeed = false;
        }

        if( (data[3]&0B00000100) >0){ // 巡航待命及退出
            cruiseStandbyAndExit = true;
        }else{
            cruiseStandbyAndExit = false;
        }
        if( (data[4]&0B00000001) >0){ // 小计清零
            clearSubtotal = true;
        }else{
            clearSubtotal = false;
        }

        if( (data[4] & 0B00000100) >0 ){// 仪表翻页
            pageTurnInstrument = true;
        }else{
            pageTurnInstrument = false;
        }
    }
}
