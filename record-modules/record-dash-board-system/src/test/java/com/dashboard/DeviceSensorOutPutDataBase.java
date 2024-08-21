package com.dashboard;

import java.util.List;

public class DeviceSensorOutPutDataBase {

    public boolean IsSuccess;

    public String Reason;

    public List<DeviceSensor> Data;

    public boolean isSuccess() {
        return IsSuccess;
    }

    public void setSuccess(boolean success) {
        IsSuccess = success;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public List<DeviceSensor> getData() {
        return Data;
    }

    public void setData(List<DeviceSensor> data) {
        Data = data;
    }
}
