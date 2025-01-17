package com.unionpower.mculibrary.bean;

import android.util.Log;

import com.unionpower.mculibrary.mcu.McuUtil;

import java.util.ArrayList;
import java.util.List;

public class FaultCode {
    private final String TAG="FaultCode";

    /*
    *  0： 0-7 故障码种类
    *  1:  0-7   0x01 发动机  0x02 变速箱   0x03 制动系统
    *  2:  0-7  故障数量
    *  3-5: 故障 SPN
    *  6 : 故障 FMI
    *   ...
    * */
    public byte[]  bytes = new byte[8];
    public List<FaultCode> mList = new ArrayList<>();
    private int faultType;
    public  FaultCode( byte[] data){
        Log.d(TAG, " FaultCode");

        int mainCodeNumber = data[0];
        int totalLength = data.length;
        byte[] faultCodebyte = new byte[totalLength -1]; // 故障数据总的数组长度
        if(mainCodeNumber == 0){

        }else if(mainCodeNumber == 1){
            //int fCode = data[1]
            FaultCode faultCodeBean= parseFaultCode(faultCodebyte,0);
            mList.add(faultCodeBean);
        }else if(mainCodeNumber == 2){
            FaultCode faultCodeBean1= parseFaultCode(faultCodebyte,0);
            mList.add(faultCodeBean1);

            int offset1 =  data[0] * 4; // 第一种障码的字节长度
            FaultCode faultCodeBean2= parseFaultCode(faultCodebyte,offset1);
        }else if(mainCodeNumber == 3){
            FaultCode faultCodeBean1= parseFaultCode(faultCodebyte,0);
            mList.add(faultCodeBean1);

            int offset1 =  data[0] * 4 + 1; // 第一种障码的字节长度, +1是包括第一位故障码数量
            FaultCode faultCodeBean2= parseFaultCode(faultCodebyte,offset1);
            mList.add(faultCodeBean2);
            int offset2 = data[offset1] *4 + 2; // 第一种障码的字节长度 +2是包括第一位故障码数量
            FaultCode faultCodeBean3= parseFaultCode(faultCodebyte,offset1);
            mList.add(faultCodeBean3);
        }
    }

    private FaultCode parseFaultCode(byte[] data, int start){
        List<FaultCodeDataBean> mList = new ArrayList<>();
        FaultCode faultCodeBean = new FaultCode();
        faultCodeBean.setFaultType(data[0]);
        // int startType = 1;
        int subTypeCodeNumber  = data[0];

        if(data.length < subTypeCodeNumber*4 + start){
            return null;
        }
        for(int i=0; i<subTypeCodeNumber; i++){
            FaultCodeDataBean faultCodeDataBean = new FaultCodeDataBean();
            byte[] spnByte = new byte[4];  //fmi码故障 占三个字节
            spnByte[0] = data[start + i*4 + 1];
            spnByte[1] = data[start + i*4 + 2];
            spnByte[2] = data[start + i*4 + 3];
            int fmiNumber = McuUtil.byteArrayToIntLittleEndian(spnByte,0);
            faultCodeDataBean.setFmiNumber(fmiNumber);
            faultCodeDataBean.setSpnNumber(data[start] + i*4 + 4);
            mList.add(faultCodeDataBean);
        }
        return  faultCodeBean;
    }

    public FaultCode(){
        Log.d(TAG, " FaultCode");
    }
    public int getFaultType() {
        return faultType;
    }

    public void setFaultType(int faultType) {
        this.faultType = faultType;
    }
}
