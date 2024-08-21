package com.dashboard;

public class DeviceInfo {

    /// 设备Id
    public String Id;

    /// DTU号
    public String DTUCode;

    /// 设备编号
    public int DeviceCode;

    /// 设备名称
    public String DeviceName;

    /// 设备类型 0=调光型，1=开关型
    public String DeviceModel;

    /// 网络类型 0=MQTT，1=TCP
    public String NetworkModel;

    /// 是否火警
    public boolean Fire;

    /// 温度
    public double Temp;

    /// 光感值
    public double LightValue;

    /// 最新数据上传时间
    public String UpdateTime;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

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

    public String getDeviceName() {
        return DeviceName;
    }

    public void setDeviceName(String deviceName) {
        DeviceName = deviceName;
    }

    public String getDeviceModel() {
        return DeviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        DeviceModel = deviceModel;
    }

    public String getNetworkModel() {
        return NetworkModel;
    }

    public void setNetworkModel(String networkModel) {
        NetworkModel = networkModel;
    }

    public boolean isFire() {
        return Fire;
    }

    public void setFire(boolean fire) {
        Fire = fire;
    }

    public double getTemp() {
        return Temp;
    }

    public void setTemp(double temp) {
        Temp = temp;
    }

    public double getLightValue() {
        return LightValue;
    }

    public void setLightValue(double lightValue) {
        LightValue = lightValue;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String updateTime) {
        UpdateTime = updateTime;
    }
}
