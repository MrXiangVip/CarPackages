package com.unionpower.mculibrary.listener;

import com.unionpower.mculibrary.bean.CarSystemData;

public interface ICarSystemListener {
    public void onSystemCallback( CarSystemData systemData , int subType);
}
