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

import com.unionpower.mculibrary.bean.FaultCode;
import com.unionpower.mculibrary.listener.IFaultCodeListener;
import com.unionpower.mculibrary.manager.FaultCodeManager;
import com.unionpower.mculibrary.mcu.McuSubType;
import com.unionpower.mculibrary.mcu.McuType;
import com.unionpower.mculibrary.mcu.McuUtil;
import com.unionpower.myapplication.R;

public class FaultCodeFragment extends Fragment implements View.OnClickListener{

    /*
    *  故障码 也只有上报的场景， 没有下发的场景
    * */
    FaultCodeManager  faultCodeManager ;

    IFaultCodeListener  listener= new IFaultCodeListener() {
        @Override
        public void onFaultCodeCallback(FaultCode faultCode) {
            Log.d(TAG, faultCode.toString());
        }
    };
    Button   getContent;
    TextView contentFrame;
    private String TAG="FaultCode";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fault_code, container, false);
        return  view;

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        faultCodeManager = FaultCodeManager.getInstance();
        faultCodeManager.setFaultCodeListener( listener );

        getContent = view.findViewById( R.id.getContent);
        getContent.setOnClickListener( this );
        contentFrame = view.findViewById( R.id.contentFrame);
    }



    @Override
    public void onClick(View v) {
        if( v.getId() == R.id.getContent){
            byte[]  content = faultCodeManager.getCachedMcuData(McuType.TYPE_FAULT_CODE, McuSubType.SUB_TYPE_FAULT_CODE);
            if( content !=null ){
                Log.d(TAG, "缓存  长度:"+content.length+" 内容: "+ McuUtil.bytesToHexString( content) );
                contentFrame.setText( McuUtil.bytesToHexString( content));
            }
        }
    }
}
