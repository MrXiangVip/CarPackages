package com.unionpower.mculibrary.manager;

import static com.unionpower.mculibrary.mcu.McuType.DEFROST;

import android.util.Log;

import com.unionpower.mculibrary.listener.IDefrostListener;
import com.unionpower.mculibrary.bean.Defrost;
import com.unionpower.mculibrary.listener.IMcuBaseListener;
import com.unionpower.mculibrary.mcu.McuSubType;
import com.unionpower.mculibrary.mcu.McuType;
import com.unionpower.mculibrary.mcu.McuUtil;
import com.unionpower.server.McuManagerBase;

/* 除霜 */
public class DefrostManager extends McuManagerBase  implements IMcuBaseListener {


    private static final String TAG = "DefrostManager.";
    private static DefrostManager mInstance;
    private  static IDefrostListener listener;
    private static Defrost   defrost = new Defrost();
    private DefrostManager( ){
        super();
    }

    public static  DefrostManager getInstance( ){
        if( mInstance == null ){
            mInstance = new DefrostManager();
        }
        return  mInstance;
    }

    public void setDefrostListener(IDefrostListener mListener){
        listener = mListener;
        mInstance.addCallbackListener( DEFROST, this );
    }
    public void removeDefrostListener( ){
        listener = null;
        mInstance.removeCallbackListener( DEFROST );
    }
    /*
    *  函数功能： 除霜电源开关  startBit 16, lengthBit 2, value:0x00 表示关闭,value:0x01 表示打开
    *  参数：
    *  返回值：
    * */
    public void toggleDefrostPower( boolean flag ){
        defrost.bytes[2] &= 0B11111100;//    电源开关 startBit 16 lengthBit 2, 先把第三个字节前两bit清0
        if( flag ){
            defrost.bytes[2] |= 0x01;// 0x01 表示打开
        }
        mInstance.sendUnPackedBytesToMcu(McuType.TYPE_AIR_CONTAINER, McuSubType.SUB_TYPE_DEFROST, defrost.bytes );
    }

    /*
    *  函数功能： 除霜设定风速  startBit 2, lengthBit 4, value :0x0000 关闭 , 0x0001 1档, 0x0010 2档, 0x0011 3档, 0x0100 4档
    *  参数： int 0-4  挡位
    * */
    public void setDefrostFanLevel( int level ){
        if( level <0 || level>4 ){ return ;}
        defrost.bytes[0] &=0B11000011;// 先把 中间4位清0, 原来的位不变
        if( level ==0){
            defrost.bytes[0] |=0B00000000;// 0x0000 关闭
        }else if( level ==1){
            defrost.bytes[0] |=0B00000100;//0x0001 1档
        }else if( level ==2){
            defrost.bytes[0] |=0B00001000;//0x0010 2档
        }else if( level ==3){
            defrost.bytes[0] |=0B00001100;//0x0011 3档
        }else if( level ==4){
            defrost.bytes[0] |=0B00010000;//0x0100 4档
        }
        mInstance.sendUnPackedBytesToMcu( McuType.TYPE_AIR_CONTAINER, McuSubType.SUB_TYPE_DEFROST, defrost.bytes );
    }


    /*
    * 函数功能: 司机散热器开关 startBit 6, lengthBit 2 . value :0x00 关闭, 0x01 开启
    *
    * */
    public void toggleDriverRadiator( boolean flag ){
        defrost.bytes[0] &=0B00111111;// 先把 6,7位清0, 原来的位不变
        if( flag ){
            defrost.bytes[0] |= 0B01000000;//0x01 开启
        }else{
            defrost.bytes[0] |= 0B00000000;//0x00 关闭
        }
        mInstance.sendUnPackedBytesToMcu( McuType.TYPE_AIR_CONTAINER, McuSubType.SUB_TYPE_DEFROST, defrost.bytes );
    }

    /*
    * 函数功能: 客舱散热器开关 startBit 8, lengthBit 2. value: 0x00 关闭, 0x01 开启
    * */
    public void togglePassengerRadiator( boolean flag ){
        defrost.bytes[1] &= 0B11111100;   //先把8,9 位清零，原来的位不变
        if( flag ){
            defrost.bytes[1] |= 0B00000001;// 0x01 开启
        }else{
            defrost.bytes[1] |= 0B00000000;// 0x01 开启
        }
        mInstance.sendUnPackedBytesToMcu( McuType.TYPE_AIR_CONTAINER, McuSubType.SUB_TYPE_DEFROST, defrost.bytes );
    }

    /*
    * 函数功能: 乘客1区散热器风机档位 startBit 10, lengthBit 2. value: 0x00 关闭, 0x01 1档, 0x10 二档, 0x11 无效
    * 参数: int  取值范围 0-2
    * */
    public void setPassengerOneRadiatorLevel( int level) {
        if( level<0 ||level >2){ return;}
        defrost.bytes[1] &= 0B11110011; //先把10, 11 位清零, 原来的位不变
        if( level == 0 ){
            defrost.bytes[1] |= 0B00000000;// 0x00 关闭
        }else if( level == 1){
            defrost.bytes[1] |= 0B00000100;// 0x01  1档
        }else if( level ==2 ){
            defrost.bytes[1] |= 0B00001000;// 0x10  二档
        }
        mInstance.sendUnPackedBytesToMcu( McuType.TYPE_AIR_CONTAINER, McuSubType.SUB_TYPE_DEFROST, defrost.bytes );
    }

    /*
    *  函数功能: 乘客二区 散热器风机档位 startBit 12, lengthBit 2 . value :0x00 关闭， 0x01 1档, 0x10 二档
    * */
    public void setPassengerTwoRadiatorLevel( int level ){
        if( level<0 ||level >2){ return;}
        defrost.bytes[1] &= 0B11001111; //先把12, 13 位清零, 原来的位不变
        if( level == 0 ){
            defrost.bytes[1] |= 0B00000000;// 0x00 关闭
        }else if( level ==1 ){
            defrost.bytes[1] |= 0B00010000;// 0x01 1档
        }else if( level ==2 ){
            defrost.bytes[1] |= 0B00100000;// 0x10 1档
        }
        mInstance.sendUnPackedBytesToMcu( McuType.TYPE_AIR_CONTAINER, McuSubType.SUB_TYPE_DEFROST, defrost.bytes );
    }

    /*
    * 函数功能: 设置司机散热器风速档位 startBit 14, lengthBit 2. value :0x00 关闭, 0x01 1档, 0x10 二档  0x11无效
    * 参数: int 取值范围0-2
    * */
    public void setDriverRadiatorLevel(int level ){
        if( level <0 || level >2){return;}
        defrost.bytes[1] &= 0B00111111; //先把14, 15 位清零, 原来的位不变
        if( level == 0 ){
            defrost.bytes[1] |= 0B00000000;// 0x00 关闭
        }else if( level ==1){
            defrost.bytes[1] |= 0B01000000; // 0x01 1档
        }else if( level ==2){
            defrost.bytes[1] |=0B10000000; //0x10 2档
        }
        mInstance.sendUnPackedBytesToMcu( McuType.TYPE_AIR_CONTAINER, McuSubType.SUB_TYPE_DEFROST, defrost.bytes );
    }



    /*
    *  函数功能： 开关 加热主机  startBit 18, lengthBit 2.  value 0x00 关闭， 0x01 打开 ，0x10 保留
    * */
    public  void toggleHeatingHost( boolean flag){
        defrost.bytes[2] &= 0B11110011; //先把18, 19 位清零, 原来的位不变
        if( flag ){
            defrost.bytes[2] |= 0B00000100;// 0x01 打开
        }else{
            defrost.bytes[2] |= 0B00000000;// 0x00 关闭
        }
        mInstance.sendUnPackedBytesToMcu( McuType.TYPE_AIR_CONTAINER, McuSubType.SUB_TYPE_DEFROST, defrost.bytes);
    }

    /*
    * 函数功能： 开关强除霜模式  startBit  20,  lengthBit 2. value 0x00 关闭, 0x01 打开
    * */
    public void toggleStrongDefrost( boolean flag ){
        defrost.bytes[2] &= 0B11001111; //先把20, 21 位清零, 原来的位不变
        if( flag ){
            defrost.bytes[2] |= 0B00010000;// 0x01 打开
        }else{
            defrost.bytes[2] |= 0B00000000;// 0x00 关闭
        }
        mInstance.sendUnPackedBytesToMcu( McuType.TYPE_AIR_CONTAINER, McuSubType.SUB_TYPE_DEFROST, defrost.bytes);
    }

    /*
    *  函数功能: 开关 除霜制冷模式 startBit 22, lengthBit 2. value ：0x00 关闭， 0x01 打开
    * */
    public void toggleDefrostAndCooling( boolean flag ){
        defrost.bytes[2] &= 0B00111111; //先把22, 23 位清零, 原来的位不变
        if( flag ){
            defrost.bytes[2] |= 0B01000000;// 0x01 打开
        }else{
            defrost.bytes[2] |= 0B00000000;// 0x00 关闭
        }
        mInstance.sendUnPackedBytesToMcu( McuType.TYPE_AIR_CONTAINER, McuSubType.SUB_TYPE_DEFROST, defrost.bytes);
    }


    /*
    *  函数功能： 除霜内外循环模式 startBit 24, lengthBit 2. value :0x00 外循环, 0x01 内循环
    *  参数:  0 外循环  1 内循环
    * */
    public  void setDefrostRecirculationMode( int mode ){
        defrost.bytes[3] &= 0B11111100; //先把24, 25 位清零, 原来的位不变
        if( mode == 0){
            defrost.bytes[3] |= 0B00000000;// 0x00 外循环
        } else if (mode == 1) {
            defrost.bytes[3] |= 0B00000001;// 0x01 内循环
        }
        mInstance.sendUnPackedBytesToMcu( McuType.TYPE_AIR_CONTAINER, McuSubType.SUB_TYPE_DEFROST, defrost.bytes);
    }

    @Override
    public void onMcuDataCbk(byte[] data) {
        Log.d(TAG, "报文长度 "+data.length+" 报文体: "+ McuUtil.bytesToHexString(data));
        if( listener != null ){
            Defrost mDefrost = new Defrost(data);
            listener.onDefrostCallback(mDefrost);
        }
    }
}

