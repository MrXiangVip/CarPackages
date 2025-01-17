package com.unionpower.myapplication.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.unionpower.mculibrary.bean.VirtualSwitcher;
import com.unionpower.mculibrary.listener.IVirtualSwitcherListener;
import com.unionpower.mculibrary.manager.VirtualSwitcherManager;
import com.unionpower.mculibrary.mcu.McuSubType;
import com.unionpower.mculibrary.mcu.McuType;
import com.unionpower.mculibrary.mcu.McuUtil;
import com.unionpower.myapplication.R;

public class VirtualSwitcherFragment extends Fragment implements View.OnClickListener{

    VirtualSwitcherManager  virtualSwitcherManager;
    private String TAG="VirtualSwitcher";
    Button luggageCompartmentLight, frontFrogLight, backFrogLight, roadSign, guideScreen;
    Button hazardWarning, topLightOne, topLightTwo, driverCeilingLight, rainSnowMode;
    Button sportMode ,coastingEnergyRecoveryLevel1, coastingEnergyRecoveryLevel2, ambientLight, tv;
    Button  getContent;
    TextView  contentFrame;
    boolean  switcher;
    IVirtualSwitcherListener  listener= new IVirtualSwitcherListener() {
        @Override
        public void onVirtualSwitcherCallback(VirtualSwitcher virtualSwitcher) {
            Log.d(TAG, virtualSwitcher.toString());
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_virtual_switcher, container, false);
        return  view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        virtualSwitcherManager =VirtualSwitcherManager.getInstance();
        virtualSwitcherManager.setVirtualSwitchListener( listener);

        luggageCompartmentLight = view.findViewById( R.id.luggageCompartmentLight );
        luggageCompartmentLight.setOnClickListener( this );
        frontFrogLight = view.findViewById( R.id.frontFrogLight );
        frontFrogLight.setOnClickListener( this );
        backFrogLight = view.findViewById( R.id.backFrogLight );
        backFrogLight.setOnClickListener( this );
        roadSign = view.findViewById(R.id.roadSign);
        roadSign.setOnClickListener( this );
        guideScreen = view.findViewById( R.id.guideScreen );
        guideScreen.setOnClickListener( this );

        hazardWarning = view.findViewById( R.id.hazardWarning );
        hazardWarning.setOnClickListener( this );
        topLightOne = view.findViewById( R.id.topLightOne );
        topLightOne.setOnClickListener( this );
        topLightTwo = view.findViewById( R.id.topLightTwo );
        topLightTwo.setOnClickListener( this );
        driverCeilingLight = view.findViewById(R.id.driverCeilingLight);
        driverCeilingLight.setOnClickListener( this );
        rainSnowMode = view.findViewById( R.id.rainSnowMode );
        rainSnowMode.setOnClickListener( this );

        sportMode = view.findViewById( R.id.sportMode);
        sportMode.setOnClickListener( this );
        coastingEnergyRecoveryLevel1 = view.findViewById( R.id.coastingEnergyRecoveryLevel1);
        coastingEnergyRecoveryLevel1.setOnClickListener( this );
        coastingEnergyRecoveryLevel2 = view.findViewById( R.id.coastingEnergyRecoveryLevel2 );
        coastingEnergyRecoveryLevel2.setOnClickListener( this );
        ambientLight = view.findViewById( R.id.ambientLight );
        ambientLight.setOnClickListener( this );
        tv = view.findViewById( R.id.tv );
        tv.setOnClickListener( this );

        getContent = view.findViewById( R.id.getContent );
        getContent.setOnClickListener( this );
        contentFrame = view.findViewById( R.id.contentFrame);
    }



    @Override
    public void onClick(View v) {
        if( v.getId() == R.id.luggageCompartmentLight ){
            switcher =!switcher;
            virtualSwitcherManager.toggleLuggageCompartmentLight( switcher);
        }else if ( v.getId() == R.id.frontFrogLight ){
            switcher =!switcher;
            virtualSwitcherManager.toggleFrontFrogLight( switcher );
        }else if( v.getId() == R.id.backFrogLight ){
            switcher =!switcher;
            virtualSwitcherManager.toggleBackFrogLight( switcher );
        }else if( v.getId() ==R.id.roadSign){
            switcher =!switcher;
            virtualSwitcherManager.toggleRoadSign( switcher );
        } else if ( v.getId() ==R.id.guideScreen) {
            switcher =!switcher;
            virtualSwitcherManager.toggleGuideScreen( switcher );
        }

        if( v.getId() == R.id.hazardWarning ){
            switcher =!switcher;
            virtualSwitcherManager.toggleHazardWarning( switcher);
        }else if( v.getId() ==R.id.topLightOne ){
            switcher =!switcher;
            virtualSwitcherManager.toggleTopLightOne( switcher );
        }else if( v.getId() == R.id.topLightTwo ){
            switcher =!switcher;
            virtualSwitcherManager.toggleTopLightTwo( switcher );
        }else if( v.getId() == R.id.driverCeilingLight ){
            switcher =!switcher;
            virtualSwitcherManager.toggleDriverCeilingLight( switcher);
        }else if( v.getId() ==R.id.rainSnowMode ){
            switcher =!switcher;
            virtualSwitcherManager.toggleRainSnowMode( switcher );
        }

        if( v.getId() == R.id.sportMode ){
            switcher=!switcher;
            virtualSwitcherManager.toggleSportMode( switcher);
        }else if ( v.getId() == R.id.coastingEnergyRecoveryLevel1 ){
            switcher =!switcher;
            virtualSwitcherManager.toggleCoastingEnergyRecoveryLevel1( switcher);
        }else if( v.getId() ==R.id.coastingEnergyRecoveryLevel2 ){
            switcher =!switcher;
            virtualSwitcherManager.toggleCoastingEnergyRecoveryLevel2( switcher );
        }else if( v.getId() == R.id.ambientLight ){
            switcher=!switcher;
            virtualSwitcherManager.toggleAmbientLight( switcher);
        }else if( v.getId() == R.id.tv){
            switcher =!switcher;
            virtualSwitcherManager.toggleTV( switcher );
        }

        if( v.getId() == R.id.rearviewMirrorHeating ) {
            switcher =!switcher;
            virtualSwitcherManager.toggleRearviewMirrorHeating( switcher );
        }else if( v.getId() == R.id.driverFan ){
            switcher =!switcher;
            virtualSwitcherManager.toggleDriverFan( switcher );
        }else if( v.getId() == R.id.readingLight ){
            switcher =!switcher;
            virtualSwitcherManager.toggleDriverFan( switcher );
        }else if( v.getId() == R.id.ventilationFan1Intake ){
            switcher =!switcher;
            virtualSwitcherManager.toggleVentilationFan1Intake( switcher);
        }else if( v.getId() ==R.id.ventilationFan1Exhaust){
            switcher =!switcher;
            virtualSwitcherManager.toggleVentilationFan1Exhaust( switcher );
        }

        if( v.getId()== R.id.ventilationFan2Intake){
            switcher =!switcher;
            virtualSwitcherManager.toggleVentilationFan1Intake( switcher);
        }else if( v.getId() == R.id.ventilationFan2Exhaust ){
            switcher =!switcher;
            virtualSwitcherManager.toggleVentilationFan1Exhaust( switcher);
        }else if( v.getId() == R.id.passengerSeatBelt){
            switcher =!switcher;
            virtualSwitcherManager.togglePassengerSeatBelt( switcher);
        }else if( v.getId() == R.id.aisleLight){
            switcher =!switcher;
            virtualSwitcherManager.toggleAisleLight( switcher);
        }else if( v.getId() == R.id.ldws ){
            switcher =!switcher;
            virtualSwitcherManager.toggleLDWS( switcher );
        }

        if(v.getId() == R.id.aebs ){
            switcher =!switcher;
            virtualSwitcherManager.toggleAEBS( switcher );
        }


        if( v.getId() == R.id.getContent ){
            byte[]  content = virtualSwitcherManager.getCachedMcuData(McuType.TYPE_VIRTUAL_SWITCHER, McuSubType.SUB_TYPE_VIRTUAL_SWITCHER);
            if( content !=null ){
                Log.d(TAG, "缓存  长度:"+content.length+" 内容: "+ McuUtil.bytesToHexString( content) );
                contentFrame.setText( McuUtil.bytesToHexString( content));
            }
        }
    }
}
