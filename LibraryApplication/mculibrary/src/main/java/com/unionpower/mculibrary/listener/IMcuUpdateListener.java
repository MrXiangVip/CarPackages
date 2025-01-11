package com.unionpower.mculibrary.listener;

public interface IMcuUpdateListener {
    public void onMcuUpdateStart( boolean  flag);
    public void onMcuUpdating( int sumLength, int currentLength );

    public void onMcuUpdated( boolean flag);
}
