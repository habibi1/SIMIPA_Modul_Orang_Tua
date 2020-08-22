package com.unila.ilkomp.simipaforparents.model;

import com.google.gson.annotations.SerializedName;

public class LogoutModel {

    @SerializedName("user")
    private String npm;
    @SerializedName("imei")
    private String imei;

    public LogoutModel(String npm, String imei) {
        this.npm = npm;
        this.imei = imei;
    }

    public String getNpm() {
        return npm;
    }

    public void setNpm(String npm) {
        this.npm = npm;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
}
