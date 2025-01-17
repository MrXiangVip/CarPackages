package com.unionpower.mculibrary.manager;

import static com.unionpower.mculibrary.mcu.McuSubType.SUB_TYPE_VIRTUAL_SWITCHER;
import static com.unionpower.mculibrary.mcu.McuType.TYPE_VIRTUAL_SWITCHER;
import static com.unionpower.mculibrary.mcu.McuType.VIRTUAL_SWITCH;

import android.util.Log;

import com.unionpower.mculibrary.listener.IMcuBaseListener;
import com.unionpower.mculibrary.listener.IVirtualSwitcherListener;
import com.unionpower.mculibrary.mcu.McuUtil;
import com.unionpower.mculibrary.bean.VirtualSwitcher;
import com.unionpower.server.McuManagerBase;

public class VirtualSwitcherManager extends McuManagerBase implements IMcuBaseListener {


    private static VirtualSwitcherManager mInstance;
    private static IVirtualSwitcherListener listener;
    private String TAG ="VirtualSwitcherManager";
    private static VirtualSwitcher virtualSwitcher = new VirtualSwitcher();
    private VirtualSwitcherManager( ){
        super();

    }
    public static  VirtualSwitcherManager getInstance( ){
        if( mInstance == null ){
            mInstance = new VirtualSwitcherManager();
        }
        return  mInstance;
    }
    @Override
    public void onMcuDataCbk(byte[] data) {
        Log.d(TAG, "报文长度 "+data.length+" 报文体: "+ McuUtil.bytesToHexString(data));
        if( listener != null ){
            VirtualSwitcher virtualSwitcher = new VirtualSwitcher(data);
            listener.onVirtualSwitcherCallback( virtualSwitcher );
        }
    }
    public void setVirtualSwitchListener( IVirtualSwitcherListener mListener){
        mInstance.addCallbackListener(VIRTUAL_SWITCH, this );
        listener = mListener;
    }
    public void removeVirtualSwitchListener( ){
        listener = null ;
        mInstance.removeCallbackListener( VIRTUAL_SWITCH);
    }

    /*
    *  函数功能： 开关行李舱灯
    * */
    public void toggleLuggageCompartmentLight( boolean flag ){ //startBit 0 , lenghtBit 1
        if(flag){
            virtualSwitcher.bytes[0] = McuUtil.bitTo1(virtualSwitcher.bytes[0],0);
        }else{
            virtualSwitcher.bytes[0] = McuUtil.bitTo0(virtualSwitcher.bytes[0],0);
        }

        mInstance.sendUnPackedBytesToMcu(TYPE_VIRTUAL_SWITCHER, SUB_TYPE_VIRTUAL_SWITCHER, virtualSwitcher.bytes);
    }

    /*
    *  函数功能:  开关 前雾灯
    * */
    public void toggleFrontFrogLight( boolean flag ){ //startBit 1 , lengthBit 1
        if( flag ){
            virtualSwitcher.bytes[0] |= 0B00000010;
        }else{
            virtualSwitcher.bytes[0] &= 0B11111101;
        }
        mInstance.sendUnPackedBytesToMcu( TYPE_VIRTUAL_SWITCHER, SUB_TYPE_VIRTUAL_SWITCHER, virtualSwitcher.bytes);
    }

    /*
     *  函数功能:  开关 后雾灯
     * */
    public void toggleBackFrogLight( boolean flag ){ //startBit 2 , lengthBit 1
        if( flag ){
            virtualSwitcher.bytes[0] |= 0B00000100;
        }else{
            virtualSwitcher.bytes[0] &= 0B11111011;
        }
        mInstance.sendUnPackedBytesToMcu( TYPE_VIRTUAL_SWITCHER, SUB_TYPE_VIRTUAL_SWITCHER, virtualSwitcher.bytes);
    }

    /*
    * 函数功能: 开关路牌
    * */
    public void toggleRoadSign( boolean flag ){ //startBit 3 , lengthBit 1
        if( flag ){
            virtualSwitcher.bytes[0] = McuUtil.bitTo1( virtualSwitcher.bytes[0], 3);
        }else{
            virtualSwitcher.bytes[0] = McuUtil.bitTo0( virtualSwitcher.bytes[0], 3);
        }
        mInstance.sendUnPackedBytesToMcu( TYPE_VIRTUAL_SWITCHER, SUB_TYPE_VIRTUAL_SWITCHER, virtualSwitcher.bytes);
    }
    /*
    * 函数功能: 导乘屏开关
    * */
    public void toggleGuideScreen( boolean flag ){ //startBit 4 ,lengthBit 1
        if( flag ){
            virtualSwitcher.bytes[0] = McuUtil.bitTo1( virtualSwitcher.bytes[0], 4);
        }else{
            virtualSwitcher.bytes[0] = McuUtil.bitTo0( virtualSwitcher.bytes[0], 4);
        }
        mInstance.sendUnPackedBytesToMcu( TYPE_VIRTUAL_SWITCHER, SUB_TYPE_VIRTUAL_SWITCHER, virtualSwitcher.bytes);
    }

    /*
    * 函数功能: 开关 危险报警
    * */
    public void toggleHazardWarning( boolean flag ){ //startBit 5 , lengthBit 1
        if( flag ){
            virtualSwitcher.bytes[0] = McuUtil.bitTo1( virtualSwitcher.bytes[0], 5);
        }else {
            virtualSwitcher.bytes[0] = McuUtil.bitTo0( virtualSwitcher.bytes[0], 5);
        }
        mInstance.sendUnPackedBytesToMcu( TYPE_VIRTUAL_SWITCHER, SUB_TYPE_VIRTUAL_SWITCHER, virtualSwitcher.bytes);
    }

    /*
    * 函数功能: 顶灯1开关
    * */
    public void toggleTopLightOne( boolean  flag ){ //startBit 6 ,lengthBit 1
        if( flag ){
            virtualSwitcher.bytes[0] = McuUtil.bitTo1( virtualSwitcher.bytes[0], 6);
        }else{
            virtualSwitcher.bytes[0] = McuUtil.bitTo0( virtualSwitcher.bytes[0], 6);
        }
        mInstance.sendUnPackedBytesToMcu( TYPE_VIRTUAL_SWITCHER, SUB_TYPE_VIRTUAL_SWITCHER, virtualSwitcher.bytes);
    }
    /*
     * 函数功能: 顶灯2开关
     * */
    public void toggleTopLightTwo( boolean  flag ){ //startBit 7 ,lengthBit 1
        if( flag ){
            virtualSwitcher.bytes[0] = McuUtil.bitTo1( virtualSwitcher.bytes[0], 7);
        }else{
            virtualSwitcher.bytes[0] = McuUtil.bitTo0( virtualSwitcher.bytes[0], 7);
        }
        mInstance.sendUnPackedBytesToMcu( TYPE_VIRTUAL_SWITCHER, SUB_TYPE_VIRTUAL_SWITCHER, virtualSwitcher.bytes);
    }
    /*
     *  函数功能：开关司机顶灯
     * */
    public void toggleDriverCeilingLight( boolean flag ){ //startBit 8, lengthBit 1
        if(flag){
            virtualSwitcher.bytes[1] = McuUtil.bitTo1(virtualSwitcher.bytes[1],0);
        }else{
            virtualSwitcher.bytes[1] = McuUtil.bitTo0(virtualSwitcher.bytes[1],0);
        }
        mInstance.sendUnPackedBytesToMcu(TYPE_VIRTUAL_SWITCHER, SUB_TYPE_VIRTUAL_SWITCHER, virtualSwitcher.bytes);
    }
    /*
    * 函数功能: 开关 雨雪模式
    * */
    public  void toggleRainSnowMode( boolean flag ){    //startBit 9 ,lengthBit 1
        if( flag ){
            virtualSwitcher.bytes[1] = McuUtil.bitTo1(virtualSwitcher.bytes[1],1);
        }else{
            virtualSwitcher.bytes[1] = McuUtil.bitTo0(virtualSwitcher.bytes[1],1);
        }
        mInstance.sendUnPackedBytesToMcu(TYPE_VIRTUAL_SWITCHER, SUB_TYPE_VIRTUAL_SWITCHER, virtualSwitcher.bytes);
    }
    /*
    * 函数功能: 运动模式开关
    * */
    public void toggleSportMode( boolean flag ){ //startBit 10, lenghtBit 1
        if( flag ){
            virtualSwitcher.bytes[1] = McuUtil.bitTo1(virtualSwitcher.bytes[1],2);
        }else{
            virtualSwitcher.bytes[1] = McuUtil.bitTo0(virtualSwitcher.bytes[1],2);
        }
        mInstance.sendUnPackedBytesToMcu(TYPE_VIRTUAL_SWITCHER, SUB_TYPE_VIRTUAL_SWITCHER, virtualSwitcher.bytes);
    }
    /*
    * 函数功能： 滑行能量回收一档开关
    * */
    public void toggleCoastingEnergyRecoveryLevel1( boolean flag){ //startBit 11, lenghtBit 1
        if( flag ){
            virtualSwitcher.bytes[1] = McuUtil.bitTo1(virtualSwitcher.bytes[1],3);
        }else{
            virtualSwitcher.bytes[1] = McuUtil.bitTo0(virtualSwitcher.bytes[1],3);
        }
        mInstance.sendUnPackedBytesToMcu(TYPE_VIRTUAL_SWITCHER, SUB_TYPE_VIRTUAL_SWITCHER, virtualSwitcher.bytes);
    }
    /*
    * 函数功能： 滑行能量回收二档开关
    * */
    public void toggleCoastingEnergyRecoveryLevel2( boolean flag){ //startBit 12, lenghtBit 1
        if( flag ){
            virtualSwitcher.bytes[1] = McuUtil.bitTo1(virtualSwitcher.bytes[1],4);
        }else{
            virtualSwitcher.bytes[1] = McuUtil.bitTo0(virtualSwitcher.bytes[1],4);
        }
        mInstance.sendUnPackedBytesToMcu(TYPE_VIRTUAL_SWITCHER, SUB_TYPE_VIRTUAL_SWITCHER, virtualSwitcher.bytes);
    }
    /*
     *  函数功能: 开关 氛围灯
     * */
    public void toggleAmbientLight( boolean flag ){ //startBit 13  , lengthBit 1
        if(flag){
            virtualSwitcher.bytes[1] = McuUtil.bitTo1(virtualSwitcher.bytes[1],5);
        }else{
            virtualSwitcher.bytes[1] = McuUtil.bitTo0(virtualSwitcher.bytes[1],5);
        }
        mInstance.sendUnPackedBytesToMcu(TYPE_VIRTUAL_SWITCHER, SUB_TYPE_VIRTUAL_SWITCHER, virtualSwitcher.bytes);
    }
    /*
     * 函数功能: 开关电视
     * */
    public void toggleTV( boolean flag ){ //startBit 14, lengthBit 1
        if(flag){
            virtualSwitcher.bytes[1] = McuUtil.bitTo1(virtualSwitcher.bytes[1],6);
        }else{
            virtualSwitcher.bytes[1] = McuUtil.bitTo0(virtualSwitcher.bytes[1],6);
        }
        mInstance.sendUnPackedBytesToMcu(TYPE_VIRTUAL_SWITCHER, SUB_TYPE_VIRTUAL_SWITCHER, virtualSwitcher.bytes);
    }
    /*
    *  函数功能: 开关 后视镜加热
    * */
    public void  toggleRearviewMirrorHeating( boolean flag ){ //startBit 15 , lengthBit 1
        if(flag){
            virtualSwitcher.bytes[1] = McuUtil.bitTo1(virtualSwitcher.bytes[1],7);
        }else{
            virtualSwitcher.bytes[1] = McuUtil.bitTo0(virtualSwitcher.bytes[1],7);
        }
        mInstance.sendUnPackedBytesToMcu(TYPE_VIRTUAL_SWITCHER, SUB_TYPE_VIRTUAL_SWITCHER, virtualSwitcher.bytes);
    }
    /*
     *  函数功能: 开关司机风扇
     * */
    public  void toggleDriverFan( boolean flag ){ //startBit 16 ,lengthBit 1
        if(flag){
            virtualSwitcher.bytes[2] = McuUtil.bitTo1(virtualSwitcher.bytes[2],0);
        }else{
            virtualSwitcher.bytes[2] &= McuUtil.bitTo0(virtualSwitcher.bytes[2],0);;
        }
        mInstance.sendUnPackedBytesToMcu(TYPE_VIRTUAL_SWITCHER, SUB_TYPE_VIRTUAL_SWITCHER, virtualSwitcher.bytes);
    }
    /*
     * 函数功能： 开关 阅读灯
     */
    public void toggleReadingLight( boolean flag ){ //startBit 17, lengthBit 1
        if(flag){
            virtualSwitcher.bytes[2] = McuUtil.bitTo1(virtualSwitcher.bytes[2],1);
        }else{
            virtualSwitcher.bytes[2] = McuUtil.bitTo0(virtualSwitcher.bytes[2],1);
        }
        mInstance.sendUnPackedBytesToMcu(TYPE_VIRTUAL_SWITCHER, SUB_TYPE_VIRTUAL_SWITCHER, virtualSwitcher.bytes);
    }

    /*
    * 函数功能:换气扇1 进气开关
    * */
    public  void toggleVentilationFan1Intake( boolean flag ){ //startBit 18, lengthBit 1
        if( flag ){
            virtualSwitcher.bytes[2] = McuUtil.bitTo1(virtualSwitcher.bytes[2],2);
        }else{
            virtualSwitcher.bytes[2] = McuUtil.bitTo0(virtualSwitcher.bytes[2],2);
        }
        mInstance.sendUnPackedBytesToMcu(TYPE_VIRTUAL_SWITCHER, SUB_TYPE_VIRTUAL_SWITCHER, virtualSwitcher.bytes);
    }
    /*
     * 函数功能:换气扇1 排气开关
     * */
    public  void toggleVentilationFan1Exhaust( boolean flag ){ //startBit 19, lengthBit 1
        if( flag ){
            virtualSwitcher.bytes[2] = McuUtil.bitTo1(virtualSwitcher.bytes[2],3);
        }else{
            virtualSwitcher.bytes[2] = McuUtil.bitTo0(virtualSwitcher.bytes[2],3);
        }
        mInstance.sendUnPackedBytesToMcu(TYPE_VIRTUAL_SWITCHER, SUB_TYPE_VIRTUAL_SWITCHER, virtualSwitcher.bytes);
    }

    /*
     * 函数功能:换气扇2 进气开关
     * */
    public  void toggleVentilationFan2Intake( boolean flag ){ //startBit 20, lengthBit 1
        if( flag ){
            virtualSwitcher.bytes[2] = McuUtil.bitTo1(virtualSwitcher.bytes[2],4);
        }else{
            virtualSwitcher.bytes[2] = McuUtil.bitTo0(virtualSwitcher.bytes[2],4);
        }
        mInstance.sendUnPackedBytesToMcu(TYPE_VIRTUAL_SWITCHER, SUB_TYPE_VIRTUAL_SWITCHER, virtualSwitcher.bytes);
    }
    /*
     * 函数功能:换气扇2 排气开关
     * */
    public  void toggleVentilationFan2Exhaust( boolean flag ){ //startBit 21, lengthBit 1
        if( flag ){
            virtualSwitcher.bytes[2] = McuUtil.bitTo1(virtualSwitcher.bytes[2],5);
        }else{
            virtualSwitcher.bytes[2] = McuUtil.bitTo0(virtualSwitcher.bytes[2],5);
        }
        mInstance.sendUnPackedBytesToMcu(TYPE_VIRTUAL_SWITCHER, SUB_TYPE_VIRTUAL_SWITCHER, virtualSwitcher.bytes);
    }
    /*
    * 函数功能: 乘客区安全带开关
    * */
    public void togglePassengerSeatBelt( boolean flag ){ //startBit 22, lengthBit 1
        if( flag ){
            virtualSwitcher.bytes[2] = McuUtil.bitTo1(virtualSwitcher.bytes[2],6);
        }else{
            virtualSwitcher.bytes[2] = McuUtil.bitTo0(virtualSwitcher.bytes[2],6);
        }
        mInstance.sendUnPackedBytesToMcu(TYPE_VIRTUAL_SWITCHER, SUB_TYPE_VIRTUAL_SWITCHER, virtualSwitcher.bytes);
    }
    /*
    * 函数功能: 过道灯开关
    * */
    public void toggleAisleLight( boolean flag ){ //startBit 23, lengthBit 1
        if( flag ){
            virtualSwitcher.bytes[2] = McuUtil.bitTo1(virtualSwitcher.bytes[2],7);
        }else{
            virtualSwitcher.bytes[2] = McuUtil.bitTo0(virtualSwitcher.bytes[2],7);
        }
        mInstance.sendUnPackedBytesToMcu(TYPE_VIRTUAL_SWITCHER, SUB_TYPE_VIRTUAL_SWITCHER, virtualSwitcher.bytes);
    }
    /*
     *  函数功能:  开关 LDWS (车道偏离预警系统 Lane Departure Warning System)
     * */
    public void toggleLDWS( boolean flag ){ //startBit 24, lengthBit 1
        if(flag){
            virtualSwitcher.bytes[3] = McuUtil.bitTo1(virtualSwitcher.bytes[3],0);
        }else{
            virtualSwitcher.bytes[3] = McuUtil.bitTo0(virtualSwitcher.bytes[3],0);
        }
        mInstance.sendUnPackedBytesToMcu(TYPE_VIRTUAL_SWITCHER, SUB_TYPE_VIRTUAL_SWITCHER, virtualSwitcher.bytes);
    }

    /*
    * 函数功能： 开关 AEBS
    * */
    public void toggleAEBS( boolean flag ){ //startBit 25, lengthBit 1
        if(flag){
            virtualSwitcher.bytes[3] = McuUtil.bitTo1(virtualSwitcher.bytes[3],1);
        }else{
            virtualSwitcher.bytes[3] = McuUtil.bitTo0(virtualSwitcher.bytes[3],1);
        }
        mInstance.sendUnPackedBytesToMcu(TYPE_VIRTUAL_SWITCHER, SUB_TYPE_VIRTUAL_SWITCHER, virtualSwitcher.bytes);
    }


    /*
    *  函数功能: 开关 ARS/ESC (主动后轮转向系统(Active Rear - Steering System), 电子稳定控制系统 (Electronic Stability Control))
    * */
    public void toggleASRAndESC( boolean flag ){
        byte[] command = new byte[8];
        mInstance.sendPackedBytesToMcu( command );
    }


    /*
    *  函数功能： 开关 电动遮阳帘
    * */
    public void toggleElectricSunshadeCurtain( boolean flag ){
        byte[] command = new byte[8];
        mInstance.sendPackedBytesToMcu( command );
    }


    /*
    *  函数功能: 开关 电气喇叭转换
    * */
    public void toggleHornType( boolean  flag){
        if(flag){
            virtualSwitcher.bytes[4] = McuUtil.bitTo1(virtualSwitcher.bytes[4],0);
        }else{
            virtualSwitcher.bytes[4] = McuUtil.bitTo0(virtualSwitcher.bytes[4],0);
        }
        mInstance.sendUnPackedBytesToMcu(TYPE_VIRTUAL_SWITCHER, SUB_TYPE_VIRTUAL_SWITCHER, virtualSwitcher.bytes);
    }

    /*
    *  函数功能: ECAS (电子控制空气悬架（Electronic - Controlled Air Suspension) 复位开关
    * */
    public  void toggleECASReset( boolean flag ){
        if(flag){
            virtualSwitcher.bytes[4] = McuUtil.bitTo1(virtualSwitcher.bytes[4],1);
        }else{
            virtualSwitcher.bytes[4] = McuUtil.bitTo0(virtualSwitcher.bytes[4],1);
        }
        mInstance.sendUnPackedBytesToMcu(TYPE_VIRTUAL_SWITCHER, SUB_TYPE_VIRTUAL_SWITCHER, virtualSwitcher.bytes);
    }

    /*
    *  函数功能: 开关 ECAS (电子控制空气悬架（Electronic - Controlled Air Suspension) 侧倾
    * */
    public  void toggleECASSideLean( boolean flag ){
        if(flag){
            virtualSwitcher.bytes[4] = McuUtil.bitTo1(virtualSwitcher.bytes[4],2);
        }else{
            virtualSwitcher.bytes[4] = McuUtil.bitTo0(virtualSwitcher.bytes[4],2);
        }
        mInstance.sendUnPackedBytesToMcu(TYPE_VIRTUAL_SWITCHER, SUB_TYPE_VIRTUAL_SWITCHER, virtualSwitcher.bytes);
    }

    /*
    * 函数功能 : 开关ECAS (电子控制空气悬架（Electronic - Controlled Air Suspension)
    * */
    public void toggleECAS( boolean flag ){
        if(flag){
            virtualSwitcher.bytes[4] = McuUtil.bitTo1(virtualSwitcher.bytes[4],1);
        }else{
            virtualSwitcher.bytes[4] = McuUtil.bitTo0(virtualSwitcher.bytes[4],1);
        }
        mInstance.sendUnPackedBytesToMcu(TYPE_VIRTUAL_SWITCHER, SUB_TYPE_VIRTUAL_SWITCHER, virtualSwitcher.bytes);
    }

    /*
    *  函数功能： 开关  DPF (柴油颗粒过滤器 Diesel Particulate Filter) 强制再生
    * */
    public void toggleDPFForcedRegeneration( boolean flag ){
        if(flag){
            virtualSwitcher.bytes[4] = McuUtil.bitTo1(virtualSwitcher.bytes[4],5);
        }else{
            virtualSwitcher.bytes[4] = McuUtil.bitTo0(virtualSwitcher.bytes[4],5);
        }
        mInstance.sendUnPackedBytesToMcu(TYPE_VIRTUAL_SWITCHER, SUB_TYPE_VIRTUAL_SWITCHER, virtualSwitcher.bytes);
    }

    /*
    *  函数功能： 开关  DPF 再生禁止
    * */
    public void toggleDPFRegenerationPermission( boolean flag){
        if(flag){
            virtualSwitcher.bytes[4] = McuUtil.bitTo1(virtualSwitcher.bytes[4],6);
        }else{
            virtualSwitcher.bytes[4] = McuUtil.bitTo0(virtualSwitcher.bytes[4],6);
        }
        mInstance.sendUnPackedBytesToMcu(TYPE_VIRTUAL_SWITCHER, SUB_TYPE_VIRTUAL_SWITCHER, virtualSwitcher.bytes);
    }
    /*
    * 函数功能： 司机散热器开关
    * */
    public void toggleDriverRadiator( boolean flag ){
        if(flag){
            virtualSwitcher.bytes[4] = McuUtil.bitTo1(virtualSwitcher.bytes[4],7);
        }else{
            virtualSwitcher.bytes[4] = McuUtil.bitTo0(virtualSwitcher.bytes[4],7);
        }
        mInstance.sendUnPackedBytesToMcu(TYPE_VIRTUAL_SWITCHER, SUB_TYPE_VIRTUAL_SWITCHER, virtualSwitcher.bytes);
    }

    /*
    *  函数功能: 前换气扇开关
    * */
    public void toggleFrontVentilationFan( boolean  flag ){
        byte[] command = new byte[8];
        mInstance.sendPackedBytesToMcu( command );
    }

    /*
    * 函数功能: 后换气扇开关
    * */
    public void toggleRearVentilationFan( boolean flag ){
        byte[]  command = new byte[8];
        mInstance.sendPackedBytesToMcu( command );
    }

    /*
    * 函数功能：发送机燃油加热开关
    * */
    public void toggleEngineFuelHeater( boolean flag ){
        if(flag){
            virtualSwitcher.bytes[4] = McuUtil.bitTo1(virtualSwitcher.bytes[5],0);
        }else{
            virtualSwitcher.bytes[4] = McuUtil.bitTo0(virtualSwitcher.bytes[5],0);
        }
        mInstance.sendUnPackedBytesToMcu(TYPE_VIRTUAL_SWITCHER, SUB_TYPE_VIRTUAL_SWITCHER, virtualSwitcher.bytes);
    }

    /*
    *  函数功能: 暖风燃油加热开关
    * */
    public void toggleHeaterFuelHeating( boolean flag ){
        if(flag){
            virtualSwitcher.bytes[4] = McuUtil.bitTo1(virtualSwitcher.bytes[5],1);
        }else{
            virtualSwitcher.bytes[4] = McuUtil.bitTo0(virtualSwitcher.bytes[5],1);
        }
        mInstance.sendUnPackedBytesToMcu(TYPE_VIRTUAL_SWITCHER, SUB_TYPE_VIRTUAL_SWITCHER, virtualSwitcher.bytes);
    }

    /*
    *  函数功能: 缓速器脚控接触开关
    * */
    public void toggleFootControlRetarderSwitch( boolean flag ){
        if(flag){
            virtualSwitcher.bytes[4] = McuUtil.bitTo1(virtualSwitcher.bytes[5],2);
        }else{
            virtualSwitcher.bytes[4] = McuUtil.bitTo0(virtualSwitcher.bytes[5],2);
        }
        mInstance.sendUnPackedBytesToMcu(TYPE_VIRTUAL_SWITCHER, SUB_TYPE_VIRTUAL_SWITCHER, virtualSwitcher.bytes);
    }

    /*
    * 函数功能： 自动雨刮 开关
    * */
    public void toggleAutoWipers( boolean flag){
        if(flag){
            virtualSwitcher.bytes[4] = McuUtil.bitTo1(virtualSwitcher.bytes[5],4);
        }else{
            virtualSwitcher.bytes[4] = McuUtil.bitTo0(virtualSwitcher.bytes[5],4);
        }
        mInstance.sendUnPackedBytesToMcu(TYPE_VIRTUAL_SWITCHER, SUB_TYPE_VIRTUAL_SWITCHER, virtualSwitcher.bytes);

    }



}

