package com.tianmai.systemlibrary.fragments;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.content.Context.WIFI_SERVICE;
import static android.net.NetworkKey.TYPE_WIFI;

import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.SoftApConfiguration;
import android.net.wifi.WifiClient;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.android.internal.util.ConcurrentUtils;
import com.tianmai.systemlibrary.R;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import android.net.TetheringManager;
import android.widget.Toast;

public class WifiApFragment extends Fragment {

    TextView  version, wifiApInfo, connectDevice;
    Switch   wifiApToggle;
    WifiManager  wifiManager;
    private TetheringManager mTetheringManager;

    private HashMap<String , String>  apMap= new HashMap();
    private String TAG="WifiAp.";
    private WifiManager.SoftApCallback mSoftApCallback = new WifiManager.SoftApCallback() {
        @Override
        public void onStateChanged(int state, int failureReason) {
            handleWifiApStateChanged(state);
        }

        public void onConnectedClientsChanged(List<WifiClient> clients) {
            Log.d(TAG, "client size "+clients.size());
//            connectDevice.setText( "连接的设备信息 :"+"\n"+getConnectedDeviceIpAndMac().toString());
            StringBuilder  builder = new StringBuilder();
            apMap = getConnectedDeviceIpAndMac();
            for( WifiClient  client:clients){
                builder.append( "连接的设备信息 :"+  client.toString()+"\n");
                builder.append( "连接的设备信息 mac:"+  client.getMacAddress()+"\n");
                builder.append( "连接的设备信息 ip:"+  apMap.get( client.getMacAddress().toString())+"\n");
            }
            connectDevice.setText( builder );
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View  view = inflater.inflate( R.layout.fragment_ap, container, false);
        return  view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        wifiManager = (WifiManager) getContext().getSystemService(Context.WIFI_SERVICE);
        wifiManager.registerSoftApCallback(getContext().getMainExecutor(), mSoftApCallback);
        mTetheringManager = getContext().getSystemService(TetheringManager.class);
//        initWifiTetherSoftApManager();

        version = view.findViewById(R.id.versionInfo);
        version.setText( getVersion( ));

        wifiApInfo = view.findViewById( R.id.wifiApInfo);

        connectDevice = view.findViewById( R.id.conntectDevice);

        wifiApToggle = view.findViewById( R.id.wifiApToggle );
        wifiApToggle.setChecked( wifiManager.isWifiApEnabled() );
        wifiApToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                wifiManager.setWifiApEnabled( isChecked );
                if (isChecked) {
                    startTethering();
                } else {
                    stopTethering();
                }
            }
        });

        wifiApInfo.setText( "热点信息 "+"\n"+getWifiApInfo());

//        connectDevice.setText( "连接的设备信息 :"+"\n"+getConnectedDeviceIpAndMac().toString());

    }
    private void startTethering() {
        mTetheringManager.startTethering(TetheringManager.TETHERING_WIFI,
                ConcurrentUtils.DIRECT_EXECUTOR,
                new TetheringManager.StartTetheringCallback() {
                    public void onTetheringFailed(final int result) {
//                        mWifiTetheringAvailabilityListener.onWifiTetheringUnavailable();
//                        mWifiTetheringAvailabilityListener.enablePreference();
                    }
                });
    }

    private void stopTethering() {
        mTetheringManager.stopTethering(TetheringManager.TETHERING_WIFI);
    }

    void handleWifiApStateChanged(int state) {
        Log.d(TAG, "handleWifiApStateChanged");
        switch (state) {
            case WifiManager.WIFI_AP_STATE_ENABLING:

                break;
            case WifiManager.WIFI_AP_STATE_ENABLED:
                Toast.makeText(getContext(), "打开热点", Toast.LENGTH_LONG).show();
                break;

            case WifiManager.WIFI_AP_STATE_DISABLING:
                break;
            case WifiManager.WIFI_AP_STATE_DISABLED:
                Toast.makeText(getContext(), "关掉热点", Toast.LENGTH_LONG).show();

                break;
            default:
                break;
        }
    }


    private String getVersion( ){
        String board = Build.BOARD;//android.os.Build.BOARD：获取设备基板名称
        String id = android.os.Build.ID;//:设备版本号。
        String product = android.os.Build.PRODUCT;//：整个产品的名称
        long time = android.os.Build.TIME;//：时间
        return  "基板名称 :"+board +" \n 设备版本号:"+id+" \n 产品的名称:" +product +"\n 时间:"+time;
//        https://blog.csdn.net/jin_pan/article/details/81197871
    }



    private  String getWifiApInfo( ){
        StringBuilder apInfo= new StringBuilder();
        SoftApConfiguration config = wifiManager.getSoftApConfiguration();
        String name =wifiManager.getSoftApConfiguration().getSsid();
        int mSecurityType = wifiManager.getSoftApConfiguration().getSecurityType();
        int band =  config.getBand();
        String passphrase = config.getPassphrase();
        apInfo.append( "热点的名字 : "+name +"\n");
        apInfo.append( "安全类型: "+  getSecuritySummary( mSecurityType)+ "\n");
        apInfo.append( "频段 : "+getBandSummary( band)+"\n");
        apInfo.append( "密码 : "+passphrase+"\n");
        return  apInfo.toString();
    }

    protected String getBandSummary(int mBand) {
        if (!is5GhzBandSupported()) {
            return getContext().getString(R.string.wifi_ap_choose_2G);
        }
        switch (mBand) {
            case SoftApConfiguration.BAND_2GHZ | SoftApConfiguration.BAND_5GHZ:
                return getContext().getString(R.string.wifi_ap_prefer_5G);
            case SoftApConfiguration.BAND_2GHZ:
                return  getContext().getString(R.string.wifi_ap_choose_2G);
            case SoftApConfiguration.BAND_5GHZ:
                return getContext().getString(R.string.wifi_ap_prefer_5G);
            default:
                Log.e(TAG, "Unknown band: " + mBand);
                return getContext().getString(R.string.wifi_ap_prefer_5G);
        }
    }

    private String getSecuritySummary( int type){
        String[] res =getContext().getResources().getStringArray(R.array.wifi_tether_security);

        return   res[type];
    }
    private boolean is5GhzBandSupported() {
        String countryCode = wifiManager.getCountryCode();
        return wifiManager.is5GHzBandSupported() && countryCode != null;
    }

    private HashMap  getConnectedDeviceIpAndMac( ){
        HashMap<String , String>  map = new HashMap();
        try (BufferedReader reader = new BufferedReader(new FileReader("/proc/net/arp"))) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split("[ ]+");
                if (tokens.length < 6) {
                    continue;
                }
                // ARP column format is
                // Address HWType HWAddress Flags Mask IFace
                String ip = tokens[0];
                String mac = tokens[3];
                map.put(mac,ip);
            }
        }catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return  map;
    }

}
