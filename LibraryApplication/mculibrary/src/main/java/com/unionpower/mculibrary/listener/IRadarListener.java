package com.unionpower.mculibrary.listener;

import com.unionpower.mculibrary.bean.Radar;

public interface IRadarListener {
//    从回调这里获取 雷达数据
    public void onRadarCallback(Radar radar);
}
