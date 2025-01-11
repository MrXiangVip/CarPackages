package com.tianmai.systemlibrary.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import static android.net.wifi.WifiManager.ActionListener;

import com.tianmai.systemlibrary.R;
import com.tianmai.systemlibrary.utils.WifiUtils;
import com.tianmai.systemlibrary.widget.WifiItem;

import java.lang.reflect.Method;

@SuppressLint("ValidFragment")
public class WifiDialogFragment extends DialogFragment implements View.OnClickListener {
    protected int mDialogLayoutRes;
    private String TAG="WifiDialog.";

    private WifiItem wifiItem;
    private TextView mTitle;
    private EditText  editText;
    private Button positive, nagetive;
    private WifiManager  wifiManager;
    private WifiConfiguration   configuration;

    public WifiDialogFragment(WifiItem  item ){
        wifiItem = item;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView( R.layout.dialog_wifi);
        mTitle =dialog.findViewById( R.id.title);
        mTitle.setText( wifiItem.ssid );

        nagetive = dialog.findViewById( R.id.nagetive);
        nagetive.setOnClickListener( this );
        positive = dialog.findViewById( R.id.positive);
        positive.setOnClickListener( this );

        editText =dialog.findViewById( R.id.password );
        return  dialog;
    }


    @Override
    public void onClick(View v) {
        if ( v.getId() == R.id.nagetive ){
            dismiss();
        }else if( v.getId() == R.id.positive ){
            wifiManager = (WifiManager) getContext().getSystemService(Context.WIFI_SERVICE);
            String password = editText.getText().toString();
            WifiConfiguration config = WifiUtils.getWifiConfig( password, wifiItem.ssid);
            wifiManager.connect(config,mConnectionListener);// system api
//            wifiManager.reconnect();
//            connectByConfig( wifiManager, config);
//            List<WifiConfiguration> configs = wifiManager.getMatchingWifiConfig(scanResult);
        }
    }
    private final WifiManager.ActionListener mConnectionListener = new WifiManager.ActionListener(){
        public void onSuccess() {
            Log.d(TAG, "connected to network");
        }
        public void onFailure(int reason) {
            Log.d(TAG, "failed "+reason);
        }
    };
    public static void connectByConfig(WifiManager manager, WifiConfiguration config) {
        if (manager == null) {
            return;
        }
        try {
            Method connect = manager.getClass().getDeclaredMethod("connect", WifiConfiguration.class, Class.forName("android.net.wifi.WifiManager$ActionListener"));
            if (connect != null) {
                connect.setAccessible(true);
                connect.invoke(manager, config, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
