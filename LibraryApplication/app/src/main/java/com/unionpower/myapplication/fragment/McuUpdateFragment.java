package com.unionpower.myapplication.fragment;

import static com.unionpower.myapplication.utils.Utils.USB4PATH;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.unionpower.mculibrary.listener.IMcuUpdateListener;
import com.unionpower.mculibrary.manager.McuUpdateManager;
import com.unionpower.myapplication.R;
import com.unionpower.myapplication.utils.Utils;

import java.io.File;

public class McuUpdateFragment extends Fragment implements View.OnClickListener{

    McuUpdateManager mcuUpdateManager;
    private String TAG="McuUpdateFragment";
    Button      mcuUpdate;
    TextView    updateStatus;
    File        mcuFile;
    String  mcuFilePath = "/mnt/media_rw/"+USB4PATH+"/update/STM32.bin";
    IMcuUpdateListener listener = new IMcuUpdateListener(){

        @Override
        public void onMcuUpdateStart(boolean flag) {
            Log.d(TAG, "开始升级 "+flag);
            updateStatus.setText( " 开始升级 "+flag);
        }

        @Override
        public void onMcuUpdating(int sumLength, int currentLength) {
            Log.d(TAG, "升级中 "+sumLength+"/"+currentLength);
            updateStatus.setText( "升级中 "+sumLength+"/"+currentLength);

        }

        @Override
        public void onMcuUpdated(boolean flag) {
            Log.d(TAG, "升级完成 "+flag);
            updateStatus.setText( "升级完成 "+flag);
        }
    };

    public View onCreateView(LayoutInflater inflater , ViewGroup container, Bundle savedInstance){
        View view = inflater.inflate(R.layout.fragment_mcu_update, container, false);
        return  view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated( view, savedInstanceState);
        mcuUpdateManager = mcuUpdateManager.getInstance();
        mcuUpdateManager.setMcuUpdateListener( listener );
        mcuUpdate = view.findViewById( R.id.update_mcu);
        mcuUpdate.setOnClickListener( this );
        updateStatus = view.findViewById( R.id.update_status);

    }


    @Override
    public void onClick(View v) {
        mcuFilePath = "/mnt/media_rw/"+ Utils.USB4PATH+"/update/STM32.bin";
        Log.d(TAG, "mcu file path:"+mcuFilePath);
        if( v.getId() == R.id.update_mcu){
            mcuFile = new File( mcuFilePath);
            if( !mcuFile.exists() ){
                updateStatus.setText( mcuFilePath+" 文件不存在!");
            }
            mcuUpdateManager.startMcuUpdate( mcuFile);
        }
    }
}
