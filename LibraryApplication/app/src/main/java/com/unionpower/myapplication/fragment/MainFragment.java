package com.unionpower.myapplication.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.unionpower.mculibrary.listener.IByteTransmitListener;
import com.unionpower.mculibrary.manager.ByteTransmitManager;
import com.unionpower.mculibrary.mcu.McuType;
import com.unionpower.mculibrary.mcu.McuUtil;
import com.unionpower.myapplication.MainActivity;
import com.unionpower.myapplication.R;

public class MainFragment extends Fragment implements View.OnClickListener{

    Button  sendMessage, system, air_container, direction_controll, tire_pressure, radar, fault_code, virtual_switcher, update_mcu;
    TextView contentHead, contentTail;
    EditText    content, subType;
    Spinner mainType;
    ByteTransmitManager byteTransmitManager;
    private String TAG ="MainActivity";
    String   sMainType;
    static StringBuilder        sendRecord=new StringBuilder("先选择 主报文类型, 再输入子类型码和报文体, 点击按钮发送字节到MCU!");
    static StringBuilder        receiveRecord = new StringBuilder(" 接收到的报文：");
    IByteTransmitListener byteTransmitListener = new IByteTransmitListener() {
        @Override
        public void onTransmitCallback(byte[] data) {
            Log.d(TAG, "报文长度 "+data.length+" 报文体: "+ McuUtil.bytesToHexString(data));
            StringBuilder hexStringBuilder = new StringBuilder();
            for (byte b : data) {
                // 将每个字节转换为8位二进制字符串表示形式（利用位运算和格式化输出）
                String binary = String.format("%2s", Integer.toHexString(b & 0xFF)).replace(' ', '0');
                hexStringBuilder.append(binary);
            }
            String result = hexStringBuilder.toString();
            receiveRecord.append("\n " +result);
            contentTail.setText( receiveRecord );

        }
    };


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return  view;
    }

    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sendMessage = view.findViewById( R.id.sendMessage );
        sendMessage.setOnClickListener( this );
        system = view.findViewById( R.id.system );
        system.setOnClickListener( this );
        air_container = view.findViewById( R.id.air_container);
        air_container.setOnClickListener( this);
        direction_controll = view.findViewById( R.id.direction_controller );
        direction_controll.setOnClickListener( this );
        tire_pressure = view.findViewById( R.id.tire_pressure );
        tire_pressure.setOnClickListener( this );
        radar = view.findViewById( R.id.radar );
        radar.setOnClickListener( this );
        fault_code = view.findViewById( R.id.fault_code );
        fault_code.setOnClickListener( this );
        virtual_switcher = view.findViewById( R.id.virtual_switcher );
        virtual_switcher.setOnClickListener( this );
        update_mcu = view.findViewById( R.id.update_mcu);
        update_mcu.setOnClickListener( this );

        contentHead = view.findViewById( R.id.contentHead); contentHead.setText( sendRecord);
        contentTail = view.findViewById( R.id.contentTail);
        content = view.findViewById( R.id.editFrame);
        subType = view.findViewById( R.id.subType );

        mainType = view.findViewById( R.id.mainType);
        mainType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sMainType = parent.getItemAtPosition( position ).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        byteTransmitManager = ByteTransmitManager.getInstance();
        byteTransmitManager.setByteTransmitListener(byteTransmitListener );
    }

    @Override
    public void onClick(View v) {
        if( v.getId() == R.id.sendMessage ){
            if( sMainType.contains("系统") ){
                String ssub = subType.getText().toString();
                String scontent = content.getText().toString();

                ByteTransmitManager.getInstance().sendBytesToMcu(McuType.TYPE_CAR_SYSTEM, Integer.parseInt(ssub), convertStringToBytes(scontent) );
                sendRecord.append( "\n 发送了->"+ McuType.TYPE_CAR_SYSTEM +" "+ssub+" "+ scontent );
                contentHead.setText( sendRecord );
            }
        }else if( v.getId() == R.id.system ){
            ((MainActivity)getActivity()).pushBackFragment( new CarSystemFragment());
        }else if( v.getId() == R.id.air_container ){
            ((MainActivity)getActivity()).pushBackFragment( new AirContainerFragment());
        }else if( v.getId() == R.id.direction_controller ){
            ((MainActivity)getActivity()).pushBackFragment( new DirectionControllFragment());
        }else if( v.getId() == R.id.radar ){
            ((MainActivity)getActivity()).pushBackFragment( new RadarFragment());
        }else if( v.getId() == R.id.tire_pressure ){
            ((MainActivity)getActivity()).pushBackFragment( new TirePressureFragment());
        }else if( v.getId() == R.id.fault_code ){
            ((MainActivity)getActivity()).pushBackFragment( new FaultCodeFragment());
        }else if( v.getId() == R.id.virtual_switcher ){
            ((MainActivity)getActivity()).pushBackFragment( new VirtualSwitcherFragment());
        }else if( v.getId() == R.id.update_mcu ){
            ((MainActivity)getActivity()).pushBackFragment( new McuUpdateFragment());
        }


    }


    public byte[]  convertStringToBytes(String binaryStr){
        byte[] byteArray = new byte[binaryStr.length() / 2];
        for (int i = 0; i < byteArray.length; i += 2) {
            String subBinary = binaryStr.substring(i, i + 2);
            try {
                int decimalValue = Integer.parseInt(subBinary, 2);
                byteArray[i / 2] = (byte) decimalValue;
            } catch (NumberFormatException e) {
                System.out.println("子字符串格式不符合二进制规范");
            }
        }
        return  byteArray;
    }
}
