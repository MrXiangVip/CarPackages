package com.unionpower.myapplication.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.unionpower.myapplication.utils.Utils;

public class UDiskMountReceiver extends BroadcastReceiver {
    private String TAG="UDiskMountReceiver.";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        String path = intent.getData().getPath();
        Log.d(TAG, " action "+action+" "+path);
        Utils.USB4PATH = path.substring( 9, path.length());
    }
}
