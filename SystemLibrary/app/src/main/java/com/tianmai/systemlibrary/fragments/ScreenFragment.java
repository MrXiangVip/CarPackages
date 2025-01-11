package com.tianmai.systemlibrary.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tianmai.systemlibrary.MainActivity;
import com.tianmai.systemlibrary.R;

/* 屏幕检测  */
public class ScreenFragment extends Fragment {

    private int backgroundDrawable[] ={
            Color.RED,
            Color.BLUE,
            Color.GREEN,
            Color.WHITE,
            Color.BLACK
    };
    TextView screenBackGround;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            int index = msg.what;
            setBackgroundDrawableID( index);
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_screen_test, container,false);
        return view;
//        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        screenBackGround = (TextView)view.findViewById( R.id.screenBackground);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity)getActivity()).activeFullScreen( true);
    }

    public void setBackgroundDrawableID(int index){
        if( index< backgroundDrawable.length){
            screenBackGround.setBackgroundColor( backgroundDrawable[index]);
            index +=1;
            handler.sendEmptyMessageDelayed( index, 800);
        }else{
            ((MainActivity)getActivity()).activeFullScreen( false);
        }
    }
}
