package com.dashboard;

import java.util.List;

public class DeviceOutPutDataBase {

    public boolean IsSuccess;

    public String Reason;

    public List<DeviceInfo> Data;

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

    public List<DeviceInfo> getData() {
        return Data;
    }

    public void setData(List<DeviceInfo> data) {
        Data = data;
    }
}
