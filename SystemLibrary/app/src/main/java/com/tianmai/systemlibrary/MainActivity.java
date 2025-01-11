package com.tianmai.systemlibrary;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toolbar;

public class MainActivity extends Activity {
    private static final String[] REQUIRED_STORAGE_PERMISSIONS = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (checkSelfPermission( Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions( REQUIRED_STORAGE_PERMISSIONS,
                    0);
        }

        if( checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) !=PackageManager.PERMISSION_GRANTED ){
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_WIFI_STATE} ,
                    1);
        }
        setContentView(R.layout.activity_main);
        initActionBar();
        showFragment( new MainFragment());
    }

    public void initActionBar( ){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle(R.string.app_name);
        }
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void showFragment(Fragment fragment) {
        final FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_content, fragment);
        transaction.commit();
    }
    public void pushBackFragment(Fragment fragment ){
        final FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_content, fragment);
        transaction.addToBackStack( "TAG");
        transaction.commit();
    }
    public void pushBackFragment( String fragmentName, Bundle args ){
        Fragment fragment = Fragment.instantiate(this, fragmentName, args);

        final FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_content, fragment);
        transaction.addToBackStack( "TAG");
        transaction.commit();
    }
    public void popOutFragment(){
        final FragmentManager fragmentManager = getFragmentManager();
        int backStackCount = fragmentManager.getBackStackEntryCount();
        if (backStackCount > 0) {
            fragmentManager.popBackStack("TAG",  FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    public void activeFullScreen( boolean enable){
        if( enable ){
            WindowManager.LayoutParams  lp = getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes( lp);
            getWindow().addFlags( WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }else {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes( lp);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }
}