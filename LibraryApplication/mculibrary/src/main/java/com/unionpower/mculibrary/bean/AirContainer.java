package com.unionpower.mculibrary.bean;


import android.util.Log;

public class AirContainer {

    private final String TAG="AirContainer";
/*
*   中控屏发出报文，-> 空调系统接收
*   0 ： 0-1 空调电源开关状态 ,  2-3 空调工作模式 新风模式 , 4-5 空调工作模式 制热模式 , 6-7 空调工作模式 制冷模式,
*   1 : 0-1  空调工作模式 通风模式 , 2-3 空调工作模式 自动模式
*   2： 0-4 设定蒸发机档位
*   3:
*   4:
*   5: 0-7 设定温度
*
*   空调系统发出报文, -> 中控屏接收报文
*   0: 0-1 空调工作模式 新风模式 , 2-3 空调工作模式 制热模式 , 4-5 空调工作模式 制冷模式 , 6-7 空调工作状态 开关
*   1: 0-3 蒸发风机档位  4-5 空调工作模式 通风模式  6-7 空调工作模式 自动模式
*   2: 0-7 蒸发风扇估算转速百分比
*   3: 0-7 冷凝 风扇估算转速百分比
*   4: 0-7 压缩机运行状态百分比
*   5: 0-7 当前设定温度
*   6: 0-7 当前车内温度
*   7: 0-7 当前车外温度
* */

    public boolean freshMode      ;//新风模式是附属模式, 可以和 制冷,制热, 自动,通风模式同时开启

    public enum TaskMode{
        ClosingMode,    //关闭模式
        HeatingMode    , //制热模式
        CoolingMode    , //制冷模式
        VentilationMode, // 通风模式
        AutoMode        //自动模式
    }
    public byte[]  bytes = new byte[8];// 发给mcu 的报文
    public TaskMode mode;// 空调工作模式： 新风/制冷/制热/通风/自动
    public boolean powerStatus; //空调工作状态: 开关
    public int  fanLevel;//蒸发风机档位

    public float  currentSetTemper;// 当前设定温度

    public float currentInCarTemper;// 当前车内温度

    public float currentOutCarTemper;// 当前车外温度

    public AirContainer(){
        Log.d(TAG, "AirContainer");
    }
    public AirContainer(byte[] data){
        Log.d(TAG, "AirContainer");
        if( (data[0] &0B00000001) >0){// startBit 0 lengthBit 2 新风模式
            freshMode = true;
        }
        if( (data[0] &0B00000100) >0 ){ //startBit2 lenghtBit 2 制热模式
            mode = TaskMode.HeatingMode;
        }
        if( (data[0] &0B00010000 ) >0 ){// startBit 4 lengthBit 2 制冷模式
            mode = TaskMode.CoolingMode;
        }
        if( (data[0] &0B01000000 )>0){  // startBit 6  lengthBit 2 空调工作状态 开关
            powerStatus = true;
        }else{
            powerStatus = false;
        }

        fanLevel=data[1] & 0B00001111;// startBit 8 lengthBit 4 //0000 0档, 0001 1档, 0010 2档, 0011 3档, 0100 4档, 0101 5档, 0110 6档, 0111 7档

        if( (data[1] &0B00110000) ==0B00010000){ //startBit 12  lengthBit 2// 0x00 关闭, 0x01 工作, 0x10 保留, 0x11 无效
            mode = TaskMode.VentilationMode;
        }
        if( (data[1] &0B11000000) ==0B01000000 ){ //startBit 14 lengthBit 2// 0x01 工作
            mode = TaskMode.AutoMode;
        }
        currentSetTemper = (data[5]-40)/2; //  15-32 之间 ，小于 15 修正为15  ，大于32 修正为32, 偏移量 -40
        currentInCarTemper= (data[6] -40)/2; //
        currentOutCarTemper = (data[7]-40)/2; //
    }
}
