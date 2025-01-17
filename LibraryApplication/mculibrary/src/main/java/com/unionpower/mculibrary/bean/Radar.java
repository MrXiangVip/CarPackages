package com.unionpower.mculibrary.bean;
import com.unionpower.mculibrary.mcu.McuUtil;

public class Radar {

    /*
    * 0-1: SBuildMajorVersion 主版本号
    * 2:   子版本号
    * 3:
    * 4-5: 0-6 年 ， 7-10 月， 11-15 日
    * 6-7： 工厂序号信息
    * */

    /*
    *  0-1:  12-15 BLRadarDisAlarmLevel
    *  2-3:  12-15 BMLRadarDisAlarmLevel
    *  4-5:  12-15 BMRRadarDisAlarmLevel
    *  6-7:  12-15 BRRadarDisAlarmLevel
    * */

    /*
    *  0-1:  12-15 FLRadarDisAlarmLevel
    *  2-3:  12-15 FMLRadarDisAlarmLevel
    *  4-5:  12-15 FMRRadarDisAlarmLevel
    *  6-7:  12-15 FRRadarDisAlarmLevel
    * */
    byte[]  bytes = new byte[8];
    private int year;
    private int month;
    private int day;

    private int majorVersion;
    private int minorVersion;

    private int lfLevel1;
    private int lfLevel2;
    private int rfLevel1;
    private int rfLevel2;


    private int lbLevel1;
    private int lbLevel2;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMajorVersion() {
        return majorVersion;
    }

    public void setMajorVersion(int majorVersion) {
        this.majorVersion = majorVersion;
    }

    public int getMinorVersion() {
        return minorVersion;
    }

    public void setMinorVersion(int minorVersion) {
        this.minorVersion = minorVersion;
    }

    public int getLfLevel1() {
        return lfLevel1;
    }

    public void setLfLevel1(int lfLevel1) {
        this.lfLevel1 = lfLevel1;
    }

    public int getLfLevel2() {
        return lfLevel2;
    }

    public void setLfLevel2(int lfLevel2) {
        this.lfLevel2 = lfLevel2;
    }

    public int getRfLevel1() {
        return rfLevel1;
    }

    public void setRfLevel1(int rfLevel1) {
        this.rfLevel1 = rfLevel1;
    }

    public int getRfLevel2() {
        return rfLevel2;
    }

    public void setRfLevel2(int rfLevel2) {
        this.rfLevel2 = rfLevel2;
    }

    public int getLbLevel1() {
        return lbLevel1;
    }

    public void setLbLevel1(int lbLevel1) {
        this.lbLevel1 = lbLevel1;
    }

    public int getLbLevel2() {
        return lbLevel2;
    }

    public void setLbLevel2(int lbLevel2) {
        this.lbLevel2 = lbLevel2;
    }

    public int getRbLevel1() {
        return rbLevel1;
    }

    public void setRbLevel1(int rbLevel1) {
        this.rbLevel1 = rbLevel1;
    }

    public int getRbLevel2() {
        return rbLevel2;
    }

    public void setRbLevel2(int rbLevel2) {
        this.rbLevel2 = rbLevel2;
    }

    private int rbLevel1;
    private int rbLevel2;
    public  Radar( byte[] data){
        //主版本号
        byte[] marjorByte = new byte[4];
        marjorByte[0] = data[0];
        marjorByte[1] = data[1];
        int marjorVer = McuUtil.byteArrayToIntLittleEndian(marjorByte, 0);
        setMajorVersion(marjorVer);
        //副版本号
        setMinorVersion(data[2]);

        //年
        int year =  data[4] & 0x7F + 2000;
        setYear(year);

        //月
        byte[] monthByte = new byte[4];
        monthByte[0] = (byte) (data[4] >> 7);
        monthByte[1] = (byte) (data[5] & 0x07);
        int month = McuUtil.byteArrayToIntLittleEndian(monthByte,0);
        setMonth(month);

        //日
        int day = data[4] >> 3 & 0xFF;
        setDay(day);

        //
        setLbLevel1(data[9] >> 4);
        setLbLevel2(data[11] >> 4);
        setRbLevel1(data[13] >> 4);
        setRbLevel2(data[15] >> 4);


        setLfLevel1(data[17] >> 4);
        setLfLevel2(data[19] >> 4);
        setRfLevel1(data[21] >> 4);
        setRfLevel2(data[23] >> 4);
    }
}
