package com.tianmai.systemlibrary.utils;

import android.net.wifi.WifiConfiguration;

import androidx.annotation.NonNull;

public class WifiUtils {
    public static WifiConfiguration getWifiConfig(String password, String ssid) {
        WifiConfiguration wifiConfig = new WifiConfiguration();
        wifiConfig.setSecurityParams(WifiConfiguration.SECURITY_TYPE_PSK);
        wifiConfig.preSharedKey = '"' + password + '"';
        wifiConfig.SSID="\"" + ssid + "\"";
        return  wifiConfig;
    }
}
