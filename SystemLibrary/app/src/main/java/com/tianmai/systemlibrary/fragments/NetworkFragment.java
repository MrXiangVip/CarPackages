package com.tianmai.systemlibrary.fragments;


import android.app.DialogFragment;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tianmai.systemlibrary.R;
import com.tianmai.systemlibrary.widget.WifiAdapter;
import com.tianmai.systemlibrary.widget.WifiItem;

import java.util.ArrayList;
import java.util.List;

public class NetworkFragment extends Fragment {
    private WifiManager wifiManager;
    private String TAG="WifiFragment";
    private Switch     wifiToggle;
    private RecyclerView  recyclerView;
    private WifiAdapter    wifiAdapter;
    private WifiReceiver    wifiReceiver= new WifiReceiver();
    StringBuilder   wifiSSID= new StringBuilder();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View  view = inflater.inflate( R.layout.fragment_wifi, container, false);
        return  view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        wifiToggle = view.findViewById( R.id.wifiToggle );
        recyclerView =view.findViewById( R.id.wifiRecyclerview );

        wifiManager = (WifiManager) getContext().getSystemService(Context.WIFI_SERVICE);
        // 初始化
        wifiToggle.setChecked( wifiManager.isWifiEnabled() );
        wifiToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                wifiManager.setWifiEnabled( isChecked );
                if( isChecked ){
                    // 扫描WiFi网络
                    wifiManager.startScan();
                    // 监听WiFi状态变化
                    IntentFilter filter = new IntentFilter();
                    filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
                    filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
                    getContext().registerReceiver(wifiReceiver, filter);
                }
            }
        });

        if( wifiManager.isWifiEnabled() ){
            // 扫描WiFi网络
            wifiManager.startScan();
            // 监听WiFi状态变化
            IntentFilter filter = new IntentFilter();
            filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
            filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
            getContext().registerReceiver(wifiReceiver, filter);
        }
    }

    class WifiReceiver extends  BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {

                List<ScanResult> scanResults = wifiManager.getScanResults();
                List<WifiItem>  wifiItems =new ArrayList<>();
                for (ScanResult result : scanResults) {
                    // 处理扫描结果
                    // 处理每个扫描结果
                    WifiItem  item= new WifiItem();
                    item.ssid = result.SSID;
                    item.bssid = result.BSSID;
                    item.rssi = result.level;
                    item.setOnItemClickedListener( i->onItemSelected(item));
                    wifiItems.add( item);
                }
                wifiAdapter= new WifiAdapter(wifiItems);
                recyclerView.setAdapter( wifiAdapter );
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
            }
        }
    }

    public void onItemSelected(WifiItem item ){
        Log.d(TAG, item.toString());
        DialogFragment   wifiDialogFragment = new WifiDialogFragment(item);
        wifiDialogFragment.show( getFragmentManager(), TAG);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
//        getContext().unregisterReceiver(wifiReceiver);
    }
}

// 参考： https://www.oryoy.com/news/android-bian-cheng-shi-xian-wifi-lian-jie-yu-kong-zhi-xiang-jie-cao-zuo-xi-tong-ji-wang-luo-guan-li.html