package com.unionpower.mculibrary.mcu;

public interface McuType {

    public static final int  TYPE_CAR_SYSTEM =0x01 ; //系统数据
    public static final int   TYPE_AIR_CONTAINER=0x0A; // 空调数据
    public static final int     TYPE_DIRECTION_CONTROL =0x0B;// 方控数据
    public static final int     TYPE_TIRE_PRESSURE =0x0C;// 胎压数据
    public static final int     TYPE_RADAR_DATA =0x0D;// 雷达数据
    public static final int     TYPE_FAULT_CODE =0x0E;// 故障码
    public static final int     TYPE_VIRTUAL_SWITCHER =0x0F;// 虚拟开关
    public static final int    TYPE_MCU_UPDATE=0x7a     ;//mcu 升级



    public static String  AIR_CONTAINER ="air_container";
    public static String  DEFROST ="defrost";
    public static String  DIRECTION_CONTROL ="direction_control";
    public static String  FAULT_CODE ="fault_code";
    public static String  RADAR_DATA ="radar_data";
    public static String  CAR_SYSTEM ="system";
    public static String  TIRE_PRESSURE ="tire_pressure";
    public static String  VIRTUAL_SWITCH ="virtual_switcher";
    public static String  IO_STATUS ="io_status";
    public static String  MCU_UPDATE ="mcu_update";
    public static String  BYTE_TRANSMIT  ="byte_transmit";

}
