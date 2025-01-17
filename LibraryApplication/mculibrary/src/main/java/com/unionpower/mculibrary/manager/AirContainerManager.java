package com.unionpower.mculibrary.manager;

import static com.unionpower.mculibrary.mcu.McuSubType.SUB_TYPE_AIR_CONTAINER;
import static com.unionpower.mculibrary.mcu.McuType.AIR_CONTAINER;
import static com.unionpower.mculibrary.mcu.McuType.TYPE_AIR_CONTAINER;

import android.util.Log;

import com.unionpower.mculibrary.listener.IAirContainerListener;
import com.unionpower.mculibrary.listener.IMcuBaseListener;
import com.unionpower.mculibrary.bean.AirContainer;
import com.unionpower.mculibrary.mcu.McuUtil;
import com.unionpower.server.McuManagerBase;
import com.unionpower.mculibrary.bean.AirContainer.TaskMode;


/* 空调 */
public class AirContainerManager extends McuManagerBase  implements IMcuBaseListener {

    private static final String TAG = "AirContainerManager.";
    private static AirContainerManager mInstance;
    private static AirContainer    airContainer = new AirContainer();

    private static AirContainer   receivedAirContainer;// 接收到的空调的报文
    private IAirContainerListener listener;
    private AirContainerManager( ){
        super();

    }
    public static AirContainerManager getInstance( ){
        if( mInstance == null ){
            mInstance = new AirContainerManager();
        }
        return  mInstance;
    }

    public void setAirContainerListener(IAirContainerListener mListener){
        mInstance.addCallbackListener( AIR_CONTAINER, this );
        listener = mListener;
    }
    public void removeAirContainerListener( ){
        listener = null;
        mInstance.removeCallbackListener( AIR_CONTAINER);
    }
    /*
    *  函数功能： 开关空调电源
    *  参数： boolean   打开空调：true    关闭空调： false
    *  返回值：
    * */
    public void  toggleAirConditionPower(boolean flag ){
        if( flag ){
            airContainer.bytes[0] |= 0B00000001;  //startBit 0 lengthBit 2   0x00 电源关闭  0x01 电源打开

        }else{
            airContainer.bytes[0] &=0B11111100;
        }
        mInstance.sendUnPackedBytesToMcu( TYPE_AIR_CONTAINER, SUB_TYPE_AIR_CONTAINER, airContainer.bytes );
    }

    /*
     *  函数功能： 新风模式开关
     *  参数： boolean   打开新风：true    关闭新风： false
     *  返回值：
     * */
    public void  toggleNewWindMode(boolean flag ){
        if( flag ){
            airContainer.bytes[0] |= 0B00000100;  // startBit 2  lengthBit 2  0x01 打开新风  0x00 关闭新风
        }else{
            airContainer.bytes[0] &= 0B11110011; //
        }
        mInstance.sendUnPackedBytesToMcu( TYPE_AIR_CONTAINER,SUB_TYPE_AIR_CONTAINER,airContainer.bytes);
    }
    /*
    * 函数功能： 设置空调的模式   关闭/⾃动/制冷/制热/通⻛, 默认值关闭
    * 参数：
    * 返回值：
    * */
    public  void setAirConditionMode( TaskMode mode){
        airContainer.bytes [0] &=0B00001111; //startBit 4, lengthBit 8 ,0x00 关闭
        airContainer.bytes[1] &=0B11110000;
        switch (mode){                  // 在切换模式前， 先切到关闭模式 再设置新模式
            case ClosingMode:
                break;
            case HeatingMode:
                airContainer.bytes[0] |= 0B00010000;  //startBit 4, lengthBit 2 , 0x01 工作  0x00 关闭
                break;
            case CoolingMode:
                airContainer.bytes[0] |= 0B01000000;  //startBit 6, lengthBit 2   , 0x01 工作 0x00 关闭
                break;
            case VentilationMode:
                airContainer.bytes[1] |= 0B00000001;  //startBit 8, lengthBit 2 , 0x01 工作 0x00 关闭
                break;
            case AutoMode:
                airContainer.bytes[1] |= 0B00000100;  //startBit 10, lengthBit 2, 0x01 工作 0x00 关闭
                break;
            default:
                break;
        }
        mInstance.sendUnPackedBytesToMcu(TYPE_AIR_CONTAINER, SUB_TYPE_AIR_CONTAINER, airContainer.bytes );
    }

    /*
    * 函数功能：设置空调温度：
    * 参数：
    * 返回值：
    * */
    public  void setAirCondtionTemper( float temper ){
        int tem = (int) (temper*2);
        if(tem < 15){
            tem = 15;
        }else if(tem > 32){
            tem = 32;
        }
        tem += 40;
        airContainer.bytes[5] = (byte) (tem&0xff);
        mInstance.sendUnPackedBytesToMcu(TYPE_AIR_CONTAINER, SUB_TYPE_AIR_CONTAINER,  airContainer.bytes);
    }

    /*
    * 函数功能： 设置空调风速
    * 参数:
    * 返回值：
    * */
    public void setAirConditionWindSpeed(int speed ){
        airContainer.bytes[2] = (byte)(speed&0xff); //startBit 16, lengthBit 4
        mInstance.sendUnPackedBytesToMcu(TYPE_AIR_CONTAINER, SUB_TYPE_AIR_CONTAINER, airContainer.bytes);
    }



    /* 将空调回传的字节 转成 类  ，回传给应用 */
    @Override
    public void onMcuDataCbk(byte[] data) {
        Log.d(TAG, "报文长度 "+data.length+" 报文体: "+ McuUtil.bytesToHexString(data));
        AirContainer container = new AirContainer( data);
        listener.onAirConditionCallback( container);
    }
}
