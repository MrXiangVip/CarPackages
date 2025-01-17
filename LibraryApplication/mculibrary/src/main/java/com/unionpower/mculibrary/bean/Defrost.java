package com.unionpower.mculibrary.bean;

import android.util.Log;

public class Defrost {
    private final String TAG="Defrost";

    /*
    * 中控屏发出-> 暖风除霜系统接收
    * 0: 0-1 除霜制热开关  2-5 除霜风机档位 , 6-7 司机散热器开关 ,
    * 1: 0-1 客舱散热器开关  2-3 客舱1区散热器风机档位  4-5 客舱2区散热器风机档位  6-7 司机散热器风机档位
    * 2: 0-1 电源开关状态   2-3 加热主机开关状态    4-5 强除霜模式  6-7 除霜制冷模式
    * 3: 0-1 除霜内外循环模式
    * */
    public static byte[] bytes = new byte[8];


    public int defrostFanLevel;//除霜风机档位
    public int passengerOneRadiatorLevel;// 客舱1区 散热器风机档位
    public int passengerTwoRadiatorLevel;// 客舱2区 散热器风机档位
    public int driverRadiatorLevel;// 司机散热器档位
    public boolean  powerToggle;// 电源开关
    public boolean      heatingPowerToggle;// 加热主机开关
    public boolean          strongDefrost;// 强除霜模式
    public boolean          defrostCooling;// 除霜制冷模式
    public int          defrostRecirculation;// 除霜内外循环模式


    public Defrost(){
        Log.d(TAG, "defrost");
    }
    public Defrost( byte[] data){
        Log.d(TAG, " defrost");
        if( (data[0] &0B00111100)==0){  //startBit 2 , lengthBit 4. 0x0000 关闭
            defrostFanLevel =0;
        }else if ( (data[0] &0B00111100) ==0B00000100){// 0x0001 1 档
            defrostFanLevel =1;
        }else if( (data[0] &0B00111100) ==0B00001000){ //0x0010 2档
            defrostFanLevel =2;
        }else if( (data[0] &0B00111100) ==0B00001100){ //0x0011 3档
            defrostFanLevel =3;
        }else if( (data[0] &0B00111100) ==0B00010000){ //0x0100 4档
            defrostFanLevel =4;
        }

        if( (data[1] &0B00001100) ==0 ){ //startBit10 , lengthBit 2 .0x00 关闭
            passengerOneRadiatorLevel =0;
        }else if ( (data[1] &0B00001100) ==0B00000100 ){ //0x01  1档
            passengerOneRadiatorLevel = 1;
        }else if( (data[1] &0B00001100) ==0B00001000){  //0x10 2档
            passengerOneRadiatorLevel =2;
        }else if( (data[1] &0B00001100) ==0B00001100){ //0x11 无效
            passengerOneRadiatorLevel =0xFF;
        }

        if( (data[1] &0B00110000) ==0 ){ //startBit12 , lengthBit 2 .0x00 关闭
            passengerTwoRadiatorLevel =0;
        }else if ( (data[1] &0B00110000) ==0B00010000 ){ //0x01  1档
            passengerTwoRadiatorLevel = 1;
        }else if( (data[1] &0B00110000) ==0B00100000){  //0x10 2档
            passengerTwoRadiatorLevel =2;
        }else if( (data[1] &0B00110000) ==0B00110000){ //0x11 无效
            passengerTwoRadiatorLevel =0xFF;
        }

        if( (data[1] &0B11000000) ==0 ){ //startBit14 , lengthBit 2 .0x00  司机散热器 风机档位
            driverRadiatorLevel =0;
        }else if ( (data[1] &0B11000000) ==0B01000000 ){ //0x01  1档
            driverRadiatorLevel = 1;
        }else if( (data[1] &0B11000000) ==0B01000000){  //0x10 2档
            driverRadiatorLevel =2;
        }else if( (data[1] &0B11000000) ==0B11000000){ //0x11 无效
            driverRadiatorLevel =0xFF;
        }

        if( (data[2] &0B00000011)== 0){ //startBit16 , lengthBit 2 电源开关状态
            powerToggle = false;
        }else if( (data[2] &0B00000011)==1){// 0x01 打开
            powerToggle =true;
        }

        if( (data[2] &0B00001100) ==0){ //startBit 18 ,lengthBit 2  加热主机开关
            heatingPowerToggle = false;
        }else if( (data[2]&0B00001100) == 0B00000100){ //0x01 打开
            heatingPowerToggle = true;
        }

        if( (data[2] &0B00110000)==0){ //startBit 20 , lengthBit 2 强除霜开关
            strongDefrost = false;
        }else if( (data[2] &0B00110000) ==0B00010000){
            strongDefrost = true;
        }

        if( (data[2] &0B11000000) ==0){ //startBit22 ,lengthBit 2  除霜制冷模式
            defrostCooling = false;
        }else if( (data[2] &0B11000000) ==0B01000000){
            defrostCooling = true;
        }

        if( (data[3] &0B00000011) ==0){ // startBit 24  lengthBit 2. 内外循环模式
            defrostRecirculation = 0;// 外循环
        }else if( (data[3] &0B00000011) ==1){
            defrostRecirculation = 1;// 内循环
        }
    }
}
