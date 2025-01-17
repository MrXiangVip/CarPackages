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

import com.unionpower.mculibrary.bean.DirectionControl;
import com.unionpower.mculibrary.listener.IDirectionControlListener;
import com.unionpower.mculibrary.manager.DirectionControlManager;
import com.unionpower.mculibrary.mcu.McuSubType;
import com.unionpower.mculibrary.mcu.McuType;
import com.unionpower.mculibrary.mcu.McuUtil;
import com.unionpower.myapplication.R;

public class DirectionControllFragment extends Fragment implements View.OnClickListener{


    /*
    *  方控暂时没有 从 中控 下发的场景， 只有从 can 发往中控
    * */
    private String TAG="DirectionControllFragment";
    IDirectionControlListener   listener = new IDirectionControlListener() {
        @Override
        public void onDirectionControlCallback(DirectionControl directionControl) {
            Log.d(TAG, directionControl.toString());
        }
    };
    DirectionControlManager  directionControlManager;
    Button  getContent;
    TextView     contentFrame;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_direction_control, container, false);
        return  view;

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        directionControlManager = DirectionControlManager.getInstance();
        directionControlManager.setDirectionControlListener( listener );

        contentFrame = view.findViewById( R.id.contentFrame);
        getContent = view.findViewById( R.id.getContent );
        getContent.setOnClickListener( this );
    }


    @Override
    public void onClick(View v) {
        if( v.getId() == R.id.getContent ){
            byte[]  content = directionControlManager.getCachedMcuData(McuType.TYPE_DIRECTION_CONTROL, McuSubType.SUB_TYPE_DIRECTION_CONTROL);
            if( content !=null ){
                Log.d(TAG, "缓存  长度:"+content.length+" 内容: "+ McuUtil.bytesToHexString( content) );
                contentFrame.setText( McuUtil.bytesToHexString( content));
            }
        }
    }
}
