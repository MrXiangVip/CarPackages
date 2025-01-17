package com.unionpower.myapplication.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.unionpower.mculibrary.bean.CarSystemData;
import com.unionpower.mculibrary.listener.ICarSystemListener;
import com.unionpower.mculibrary.manager.CarSystemManager;
import com.unionpower.mculibrary.mcu.McuSubType.SUB_TYPE_CAR_SYSTEM;
import com.unionpower.mculibrary.mcu.McuType;
import com.unionpower.mculibrary.mcu.McuUtil;
import com.unionpower.myapplication.R;

import java.io.File;
import java.io.UnsupportedEncodingException;

public class CarSystemFragment extends Fragment implements View.OnClickListener{

    public  String TAG = "CarSystem.";
    Button  handShake,syncAndroidTimeToMcu, fetchMcuVersion;
    Button  getMcuVersion, getAcc,getSysTime,  getCarDomain, getCarExternalDomain, getCarFrontDomain, getCarBackDomain;
    Button  surroundView;
    TextView contentFrame;
    File  mcuFile;// 待升级的mcu文件
    ICarSystemListener systemListener = new ICarSystemListener() {
        @Override
        public void onSystemCallback(CarSystemData systemData, int subType ) {
            Log.d(TAG, systemData.toString());
        }
    };
    CarSystemManager carSystemManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_system_data, container, false);
        return  view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        carSystemManager = CarSystemManager.getInstance();
        carSystemManager.setSystemManagerListener( systemListener);

        contentFrame = view.findViewById( R.id.contentFrame );

        handShake = view.findViewById( R.id.handShake);
        handShake.setOnClickListener( this );

        syncAndroidTimeToMcu = view.findViewById( R.id.syncAndroidTimeToMcu);
        syncAndroidTimeToMcu.setOnClickListener( this);
        fetchMcuVersion = view.findViewById(R.id.fetchMcuVersion);
        fetchMcuVersion.setOnClickListener( this );

        getMcuVersion = view.findViewById( R.id.getMcuVersion );
        getMcuVersion.setOnClickListener( this );
        getAcc = view.findViewById( R.id.getSystemAcc );
        getAcc.setOnClickListener( this );
        getSysTime = view.findViewById( R.id.getSystemTime );
        getSysTime.setOnClickListener( this );
        getCarDomain = view.findViewById( R.id.getCarDomain );
        getCarDomain.setOnClickListener( this );
        getCarExternalDomain = view.findViewById( R.id.getCarExternalDomain );
        getCarExternalDomain.setOnClickListener( this );
        getCarFrontDomain = view.findViewById( R.id.getCarFrontDomain );
        getCarFrontDomain.setOnClickListener( this );
        getCarBackDomain = view.findViewById( R.id.getCarBackDomain );
        getCarBackDomain.setOnClickListener( this );

        surroundView = view.findViewById(R.id.surroundView);//360快起
        surroundView.setOnClickListener( this );
    }



    @Override
    public void onClick(View v) {
        if( v.getId() == R.id.handShake ){
            carSystemManager.sendHandshakeSignal();
        }else if( v.getId() ==R.id.syncAndroidTimeToMcu){
            carSystemManager.pushAndroidTimeToMcu();
        }else if( v.getId() ==R.id.fetchMcuVersion ){
            carSystemManager.fetchMcuVersion();
//            String version = SystemProperties.get("persist.product.mcu","00000000");

//            try {
//                byte[] byteArray = version.getBytes( version);
//                String jMcuVersion = new String( byteArray, "UTF-8");
//
//            } catch (UnsupportedEncodingException e) {
//                throw new RuntimeException(e);
//            }
        }else if( v.getId() == R.id.surroundView ){
            carSystemManager.request360QuickData();
        }


        if( v.getId() == R.id.getMcuVersion ){
            byte[] content =carSystemManager.getCachedMcuData(McuType.TYPE_CAR_SYSTEM, SUB_TYPE_CAR_SYSTEM.MCU_VERSION.getValue() );
            if( content != null ){
                contentFrame.setText(McuUtil.bytesToHexString( content ));
            }
        }else if( v.getId() == R.id.getSystemAcc ){
            byte[] content =carSystemManager.getCachedMcuData(McuType.TYPE_CAR_SYSTEM, SUB_TYPE_CAR_SYSTEM.MCU_ACC.getValue() );
            if( content != null ){
                contentFrame.setText(McuUtil.bytesToHexString( content ));
            }
        }else if(v.getId() ==R.id.getSystemTime){
            byte[] content =carSystemManager.getCachedMcuData(McuType.TYPE_CAR_SYSTEM, SUB_TYPE_CAR_SYSTEM.PUSH_TIME.getValue() );
            if( content != null ){
                contentFrame.setText(McuUtil.bytesToHexString( content ));
            }
        }else if( v.getId() == R.id.getCarDomain ){
            byte[] content =carSystemManager.getCachedMcuData(McuType.TYPE_CAR_SYSTEM, SUB_TYPE_CAR_SYSTEM.CAR_DOMAIN.getValue() );
            if( content != null ){
                contentFrame.setText(McuUtil.bytesToHexString( content ));
            }
        }else if( v.getId() == R.id.getCarExternalDomain ){
            byte[] content =carSystemManager.getCachedMcuData(McuType.TYPE_CAR_SYSTEM, SUB_TYPE_CAR_SYSTEM.CAR_EXTERN_DOMAIN.getValue() );
            if( content != null ){
                contentFrame.setText(McuUtil.bytesToHexString( content ));
            }
        }else if( v.getId() == R.id.getCarFrontDomain ){
            byte[] content =carSystemManager.getCachedMcuData(McuType.TYPE_CAR_SYSTEM, SUB_TYPE_CAR_SYSTEM.CAR_FRONT_DOMAIN.getValue() );
            if( content != null ){
                contentFrame.setText(McuUtil.bytesToHexString( content ));
            }
        }else if( v.getId() == R.id.getCarBackDomain ){
            byte[] content =carSystemManager.getCachedMcuData(McuType.TYPE_CAR_SYSTEM, SUB_TYPE_CAR_SYSTEM.CAR_BACK_DOMAIN.getValue() );
            if( content != null ){
                contentFrame.setText(McuUtil.bytesToHexString( content ));
            }
        }

    }
}
