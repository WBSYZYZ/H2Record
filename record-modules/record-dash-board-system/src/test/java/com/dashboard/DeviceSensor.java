package com.dashboard;

public class DeviceSensor {

    /// DTU号
    public String DTUCode;

    /// 设备编号
    public int DeviceCode;

    /// 开关编号
    public int SensorCode;

    /// 开关名称
    public String SensorName;

    /// 是否有电流
    public boolean HasA;

    /// 电流
    public double A;

    /// 当前开关的状态 true=开 false=关
    public boolean Status;

    public String getDTUCode() {
        return DTUCode;
    }

    public void setDTUCode(String DTUCode) {
        this.DTUCode = DTUCode;
    }

    public int getDeviceCode() {
        return DeviceCode;
    }

    public void setDeviceCode(int deviceCode) {
        DeviceCode = deviceCode;
    }

    public int getSensorCode() {
        return SensorCode;
    }

    public void setSensorCode(int sensorCode) {
        SensorCode = sensorCode;
    }

    public String getSensorName() {
        return SensorName;
    }

    public void setSensorName(String sensorName) {
        SensorName = sensorName;
    }

    public boolean isHasA() {
        return HasA;
    }

    public void setHasA(boolean hasA) {
        HasA = hasA;
    }

    public double getA() {
        return A;
    }

    public void setA(double a) {
        A = a;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }
}
