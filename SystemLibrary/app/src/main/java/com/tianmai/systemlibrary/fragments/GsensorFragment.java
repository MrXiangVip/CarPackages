package com.tianmai.systemlibrary.fragments;

import android.app.Fragment;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.tianmai.systemlibrary.R;

public class GsensorFragment extends Fragment  {
    private SensorManager sensorManager;
    private Sensor accSensor;
    private Sensor gyroscopeSensor;
    private TextView mTvAccelerometer, mGyroSensor;
    private String TAG="GsensorFragment.";

    SensorEventListener gyroscopeListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            // 处理陀螺仪数据
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            StringBuffer buffer = new StringBuffer();

            // 进行相关操作，比如更新界面或执行相应逻辑
            buffer.append("陀螺仪 x：").append(String.format("%.2f", x)).append("\n");
            buffer.append("陀螺仪 y：").append(String.format("%.2f", y)).append("\n");
            buffer.append("陀螺仪 Z:").append(String.format("%.2f", z)).append("\n");
            if (x>20&&y>20){
                Toast.makeText(getContext(),"欢迎",Toast.LENGTH_SHORT).show();
            }
            mGyroSensor.setText(buffer);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // 当传感器精度发生变化时触发
        }
    };
    SensorEventListener accListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            // 处理陀螺仪数据
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            StringBuffer buffer = new StringBuffer();

            // 进行相关操作，比如更新界面或执行相应逻辑
            buffer.append("X方向的加速度为：").append(String.format("%.2f", x)).append("\n");
            buffer.append("Y方向的加速度为：").append(String.format("%.2f", y)).append("\n");
            buffer.append("Z方向的加速度为：").append(String.format("%.2f", z)).append("\n");
            if (x>20&&y>20){
                Toast.makeText(getContext(),"欢迎",Toast.LENGTH_SHORT).show();
            }
            mTvAccelerometer.setText(buffer);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // 当传感器精度发生变化时触发
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_gsensor, container, false);
        return  view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTvAccelerometer = view.findViewById( R.id.accSensor );
        mGyroSensor = view.findViewById(R.id.gyroSensor);
        // 获取传感器管理者对象
        sensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        // 获取加速度传感器对象
        accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //  陀螺仪传感器
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (sensorManager!=null){
            // 添加监听器
            sensorManager.registerListener(accListener, accSensor, SensorManager.SENSOR_DELAY_NORMAL);
            sensorManager.registerListener( gyroscopeListener, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

    }


}
