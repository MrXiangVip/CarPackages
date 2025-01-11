package com.unionpower.mculibrary.bean;

public class VirtualSwitcher {

    /* 中控发往 MCU
    * 0: 0 行李开关,  1 前雾灯开关,  2 后雾灯开关,  3 路牌开关,  4 导乘屏开关 ，5 危险报警开关 , 6 顶灯1开关 ,7 顶灯2开关
    * 1: 0 司机顶灯开关 , 1 雨雪模式开关 , 2 运动模式开关 ， 3 滑行能量回收一档开关 , 4 滑行能量回收二挡开关 , 5 氛围灯开关 , 6 TV 开关 ,7 后视镜加热开关
    * 2: 0 司机风扇开关 , 1 阅读灯开关 , 2 换气扇1进气开关 ， 3 换气扇1 排气开关 ， 4 换气扇2 进气开关， 5 换气扇2 排气开关, 6 乘客区安全带开关, 7 过道灯开关
    * 3: 0 LDWS 开关  , 1 AEBS 开关  , 2 行人制动开关 , 3 空气净化器开关 , 4 阅读灯强制开关(总控)  ， 5 阅读灯选择开关(单个自由控制)
    * */
    public byte[]  bytes = new byte[8];

    public VirtualSwitcher(){

    }
    public  VirtualSwitcher( byte[] data){

    }
}
