package com.unionpower.mculibrary.bean;

import android.util.Log;

import com.unionpower.mculibrary.mcu.McuSubType.SUB_TYPE_CAR_SYSTEM;

public class CarSystemData {

    private final String TAG="CarSystemData.";
    /*
    * 0： Bit0:ACC状态 1 ON，0 OFF , Bit1:ON火状态  1 ON，0 OFF, Bit2:START状态  1 ON，0 OFF,Bit3-Bit4:档位 0 无效，1 D档，2  N档，3  R档, Bit5: 手刹状态 1 ON，0 OFF,Bit6:供电电压异常报警, 1 ON，0 OFF,Bit7:刹车气压低报警，1 ON，0 OFF
    * 1:
    * */
    byte[]  carDomainData = new byte[8];// 车身状态
    boolean accStatus;//    ACC
    boolean onFireStatus;// ON 火状态
    boolean startStatus; //START 状态

    boolean handBrake;// 手刹状态
    CarGear carGear;//档位

    boolean voltageAlarm;// 供电电压异常报警
    boolean lowBrakePressureAlarm;// 刹车低气压报警
    boolean  positionLight;// 位置灯
    boolean  highBeam;// 远光灯
    boolean  lowBean;// 近光灯
    boolean  leftTurnLight;// 左转向灯
    boolean  rightTurnLight;// 右转向灯
    boolean  doubleFlashLight;// 双闪灯
    boolean  frontFrogLight;// 前雾灯
    boolean  backFrogLight;// 后雾灯
    boolean  frontDoorStatus;// 前门状态  ON/OFF
    boolean  middleDoorStatus;// 中门状态
    boolean  backDoorStatus;// 后门状态
    boolean  leftLuggageDoor;// 左行李舱门
    boolean  rightLuggageDoor;// 右行李舱门
    boolean frontLeftBrakePadAlarm;// 前桥左蹄片报警
    boolean frontRightBrakePadAlarm;// 前桥右蹄片报警
    boolean backLeftBrakePadAlarm;// 后桥左蹄片报警
    boolean backRightBrakePadAlarm;// 后桥右蹄片报警


    public enum CarGear{
        Gear0,  //0档
        GearD,  //D档
        GearN,  //N档
        GearR   //R档
    }
    public CarSystemData(byte[] data, SUB_TYPE_CAR_SYSTEM subType){
        Log.d(TAG, "subType "+subType);
        if( subType == null ){ return;}
        if( subType == SUB_TYPE_CAR_SYSTEM.CAR_DOMAIN ){
            if( (data[0] &0B00000001 )>0){ //0 :Bit0:ACC状态 1 ON，0 OFF ,
                accStatus = true;
            }else{
                accStatus = false;
            }

            if( (data[0]&0B00000010)>1){    //0: Bit1:ON火状态  1 ON，0 OFF
                onFireStatus = true;
            }else{
                onFireStatus = false;
            }
            if( (data[0] &0B00000100) >1){ //0: Bit2:START状态  1 ON，0 OFF
                startStatus = true;
            }else{
                startStatus = false;
            }

            if( (data[0]& 0B00011000) ==0){ // 0: Bit3-4   0 无效 ,1 D档 ,2 N档, 3 R档
                carGear = CarGear.Gear0;
            }else if( (data[0] &0B00011000) ==0B00001000){
                carGear = CarGear.GearD;
            } else if ( (data[0] &0B00011000) ==0B00010000) {
                carGear = CarGear.GearN;
            } else if ( (data[0] &0B00011000) ==0B00011000) {
                carGear = CarGear.GearR;
            }

            if( (data[0] &0B00100000) >0){ //0: Bit5 手刹状态
                handBrake = true;
            }else {
                handBrake = false;
            }
            if( (data[0] &0B01000000) >0 ){ //0: Bit6 供电电压异常报警
                voltageAlarm = true;
            }else {
                voltageAlarm = false;
            }
            if( (data[0] &0B10000000) >0){ //0 :Bit 7 刹车气压低 报警
                lowBrakePressureAlarm = true;
            }else{
                lowBrakePressureAlarm = false;
            }


            if( (data[1] &0B00000001)>0){// 1:Bit0  位置灯 1 on  0 off
                positionLight = true;
            }else {
                positionLight = false;
            }

            if( (data[1] &0B00000010) >0){// 1: Bit1  近光灯 1 on  0 off
                lowBean = true;
            }else{
                lowBean = false;
            }
            if( (data[1] &0B00000100)>0){// 1: Bit2  远光灯  1 on  O off
                highBeam = true;
            }else{
                highBeam = false;
            }

            if( (data[1] &0B00001000)>0 ){// 1: BIT3 左转向灯
                leftTurnLight = true;
            }else{
                leftTurnLight = false;
            }
            if( (data[1]&0B00010000) >0){// 1：BIT4 右转向灯
                rightTurnLight = true;
            }else{
                rightTurnLight = false;
            }

            if( (data[1]&0B00100000)>0){ // 1:BIT5 双闪灯
                doubleFlashLight = true;
            }else{
                doubleFlashLight = false;
            }

            if( (data[1]&0B01000000) >0){ // 1：Bit 6  前雾灯
                frontFrogLight = true;
            }else{
                frontFrogLight = false;
            }
            if( (data[1]&0B10000000)>0){ //1 :Bit7 后雾灯
                backFrogLight = true;
            }else{
                backFrogLight = false;
            }

            if( (data[2]&0B00000001) >0){ //2： Bit0 前门状态
                frontDoorStatus = true;
            }else{
                frontDoorStatus =false;
            }
            if( (data[2]&0B00000010) >0){ //2 bit1 中门状态
                middleDoorStatus = true;
            }else{
                middleDoorStatus =false;
            }

            if( (data[2]&0B00000100) >0){//2  bit2 后门状态
                backDoorStatus = true;
            }else{
                backDoorStatus = false;
            }

            if( (data[2] &0B00001000)>0){// 2 bit3 左行李舱门
                 leftLuggageDoor = true;
            }else{
                leftLuggageDoor = false;
            }
            if( (data[2] &0B00010000)>0){// 2 bit4 右行李舱门
                rightLuggageDoor = true;
            }else{
                rightLuggageDoor = false;
            }

            if( (data[3] &0B00000001) >0){ //3 bit0 前桥左蹄片报警
                frontLeftBrakePadAlarm = true;
            }else {
                frontLeftBrakePadAlarm = false;
            }
            if( (data[3] &0B00000010)>0){ //3 bit1 前桥右蹄片报警
                frontRightBrakePadAlarm = true;
            }else{
                frontRightBrakePadAlarm = false;
            }
            if( (data[3] &0B00000100)>0){ //3 bit1 前桥右蹄片报警
                backLeftBrakePadAlarm = true;
            }else{
                backLeftBrakePadAlarm = false;
            }
            if( (data[3] &0B00000010)>0){ //3 bit1 前桥右蹄片报警
                backRightBrakePadAlarm = true;
            }else{
                backRightBrakePadAlarm = false;
            }

        }else if (subType == SUB_TYPE_CAR_SYSTEM.CAR_EXTERN_DOMAIN)  {
            
        }else if( subType == SUB_TYPE_CAR_SYSTEM.CAR_FRONT_DOMAIN){

        }else if ( subType == SUB_TYPE_CAR_SYSTEM.CAR_BACK_DOMAIN){

        }

/*
*   车身外域
* */
        byte[] carExternalDomainBytes=new byte[10];
        int carSpeed;       //车速
        int engineSpeed;    //发动机转速
        int engineWaterTemper;// 发动机水温
        int oilPressure;      //机油压力
        int averageFuelConsume;// 平均油耗
        int instFualConsume;// 瞬时油耗
        int subTotalFuelConsume;// 小计油耗
        int totalMileage;// 总里程
        int subTotalMilegae;// 小计里程

/*
*   车身前域
* */



    }
}
