package com.unionpower.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;


import com.unionpower.myapplication.fragment.MainFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.widget.Toolbar;
import android.app.ActionBar;

public class MainActivity extends Activity {

    private static final String[] REQUIRED_STORAGE_PERMISSIONS = new String[]{
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (checkSelfPermission( Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions( REQUIRED_STORAGE_PERMISSIONS,
                    0);
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
    public void goBack(View view){
        onBackPressed();
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

    public void popBackFragment(){
        final FragmentManager fragmentManager = getFragmentManager();
        int backStackCount = fragmentManager.getBackStackEntryCount();
        if (backStackCount > 0) {
            fragmentManager.popBackStack("TAG",  FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

}