package com.unionpower.mculibrary.mcu;

public interface McuSubType {
    public enum SUB_TYPE_CAR_SYSTEM {
        HAND_SHAKE(0x01), //系统数据 子类型 握手
        MCU_VERSION (0x02), // 子类型 mcu 版本信息
        MCU_ACC(0x03),      // acc
        PUSH_TIME(0x09),    // 推送时间
        CAR_DOMAIN(0x0a),   // 车身域
        CAR_EXTERN_DOMAIN   (0x0b),// 车身外域
        CAR_FRONT_DOMAIN(0x0c), // 车身前域
        CAR_BACK_DOMAIN(0x0d), // 车身后域
        CAR_360(0x0e);  //

        SUB_TYPE_CAR_SYSTEM(int value){
            type = value;
        }
        int  type = 0;
        public int getValue( ){
            return  type;
        }
    };

    public  static SUB_TYPE_CAR_SYSTEM intToSubCarSystemEnum( int value ){
        for( SUB_TYPE_CAR_SYSTEM carSystem : SUB_TYPE_CAR_SYSTEM.values()){
            if( carSystem.getValue() ==value ){
                return  carSystem;
            }
        }
        return  null;
    }
    public static final int SUB_TYPE_AIR_CONTAINER = 0x01;// 空调数据
    public static final int SUB_TYPE_DEFROST = 0x02;// 除霜数据
    public static final int SUB_TYPE_VIRTUAL_SWITCHER=0x01;// 虚拟开关子码
    public static final int SUB_TYPE_DIRECTION_CONTROL=0x01;// 中通方控数据子码

    public static final int SUB_TYPE_FAULT_CODE =0x01;// 故障码子码
    public static final  int SUB_TYPE_RADAR_DATA=0x01;// 雷达子码
    public static final  int SUB_TYPE_TIRE_PRESSURE=0x01;// 胎压子码

    public static final int SUB_TYPE_MCU_UPDATE_REQ=0x01;//升级命令
    public static final int SUB_TYPE_MCU_UPDATE_DATA=0x02;//升级数据
    public static final int SUB_TYPE_MCU_UPDATE_RESPONSE=0x03;//MCU回复数据



};
