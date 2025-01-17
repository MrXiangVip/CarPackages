package com.unionpower.myapplication.fragment;

import static com.unionpower.mculibrary.bean.AirContainer.TaskMode.HeatingMode;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.unionpower.mculibrary.bean.AirContainer;
import com.unionpower.mculibrary.bean.AirContainer.TaskMode;
import com.unionpower.mculibrary.bean.Defrost;
import com.unionpower.mculibrary.listener.IAirContainerListener;
import com.unionpower.mculibrary.listener.IDefrostListener;
import com.unionpower.mculibrary.manager.AirContainerManager;
import com.unionpower.mculibrary.manager.DefrostManager;
import com.unionpower.mculibrary.mcu.McuSubType;
import com.unionpower.mculibrary.mcu.McuType;
import com.unionpower.mculibrary.mcu.McuUtil;
import com.unionpower.myapplication.R;

public class AirContainerFragment extends Fragment  implements  View.OnClickListener{

    private static final String TAG = "AirContainer.";

    AirContainerManager  airContainerManager;
    DefrostManager         defrostManager;
    boolean  powerStatus , defrostPower= false;// 电源
    boolean  newWind = false;
    TaskMode   mode; //工作模式
    IAirContainerListener  listener = new IAirContainerListener() {

        @Override
        public void onAirConditionCallback(AirContainer container) {
            Log.d(TAG,  container.toString() );
        }
    };

    IDefrostListener defrostListener = new IDefrostListener() {
        @Override
        public void onDefrostCallback(Defrost defrost) {
            Log.d(TAG, defrost.toString());
        }
    };
    TextView contentFrame, contentFrame2;
    Button  getContent, getContent2;
    Button buttonTogglePower, buttonToggleNewWind, buttonSetMode, buttonSetAirConditionTemper, buttonSetAirConditionWindSpeed;
    Button buttonToggleDefrost;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_air_container, container, false);
        return  view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        airContainerManager = AirContainerManager.getInstance();
        airContainerManager.setAirContainerListener( listener );

        defrostManager = DefrostManager.getInstance();
        defrostManager.setDefrostListener( defrostListener);

        contentFrame = view.findViewById( R.id.contentFrame );
        getContent = view.findViewById( R.id.getContent );
        getContent.setOnClickListener( this );

        contentFrame2 = view.findViewById( R.id.contentFrame2 );
        getContent2 = view.findViewById(R.id.getContent2 );
        getContent2.setOnClickListener( this );

        buttonTogglePower = view.findViewById( R.id.toggleAirContainerPower);
        buttonTogglePower.setOnClickListener( this );

        buttonToggleNewWind = view.findViewById(R.id.toggleNewWindMode);
        buttonToggleNewWind.setOnClickListener( this );


        buttonSetMode    = view.findViewById(R.id.setMode);
        buttonSetMode.setOnClickListener( this );

        buttonSetAirConditionTemper   = view.findViewById(R.id.setAirCondtionTemper );
        buttonSetAirConditionTemper.setOnClickListener( this );

        buttonSetAirConditionWindSpeed =view.findViewById(R.id.setAirConditionWindSpeed);
        buttonSetAirConditionWindSpeed.setOnClickListener( this );



        buttonToggleDefrost = view.findViewById( R.id.toggleDefrostPower );
        buttonToggleDefrost.setOnClickListener( this );
    }


    @Override
    public void onClick(View v) {
        if( v.getId() == R.id.getContent ){
            byte[]  content = airContainerManager.getCachedMcuData(McuType.TYPE_AIR_CONTAINER, McuSubType.SUB_TYPE_AIR_CONTAINER);
            if( content !=null ){
                Log.d(TAG, "缓存  长度:"+content.length+" 内容: "+ McuUtil.bytesToHexString( content) );
                contentFrame.setText( McuUtil.bytesToHexString( content));
            }
        }else if ( v.getId() == R.id.getContent2 ){
            byte[]  content = airContainerManager.getCachedMcuData(McuType.TYPE_AIR_CONTAINER, McuSubType.SUB_TYPE_DEFROST);
            if( content !=null ){
                Log.d(TAG, "缓存  长度:"+content.length+" 内容: "+ McuUtil.bytesToHexString( content) );
                contentFrame2.setText( McuUtil.bytesToHexString( content));
            }
        }

        if( v.getId() == R.id.toggleAirContainerPower ){
            powerStatus = !powerStatus;
            airContainerManager.toggleAirConditionPower( powerStatus );
        } else if ( v.getId() == R.id.toggleNewWindMode ) {
            airContainerManager.toggleNewWindMode( true);

        }else if( v.getId() == R.id.setMode ){
            airContainerManager.setAirConditionMode( HeatingMode );
        }else if( v.getId() == R.id.setAirCondtionTemper ){
            airContainerManager.setAirCondtionTemper( 20);
        }else if( v.getId() ==R.id.setAirConditionWindSpeed ){
            airContainerManager.setAirConditionWindSpeed( 2);
        }

//
        if( v.getId() == R.id.toggleDefrostPower ){
            defrostPower = !defrostPower;
            defrostManager.toggleDefrostPower( defrostPower);
        } else if ( v.getId() == R.id.setDefrostFanLevel) {
            defrostManager.setDefrostFanLevel( 2 );
        }else if( v.getId() == R.id.setPassangerOneFanLevel){
            defrostManager.setPassengerOneRadiatorLevel( 2);
        }else if( v.getId() == R.id.setPassangerTwoFanLevel ){
            defrostManager.setPassengerTwoRadiatorLevel( 2);
        }else if( v.getId() == R.id.setDriverFanLevel){
            defrostManager.setDriverRadiatorLevel( 2);
        }else if( v.getId() == R.id.strongDefrost){
            defrostManager.toggleStrongDefrost( true);
        }else if( v.getId() == R.id.defrostAndCooling ){
            defrostManager.toggleDefrostAndCooling( true);
        }else if( v.getId() == R.id.setRecirculationMode ){
            defrostManager.setDefrostRecirculationMode( 0);
        }

    }
}
