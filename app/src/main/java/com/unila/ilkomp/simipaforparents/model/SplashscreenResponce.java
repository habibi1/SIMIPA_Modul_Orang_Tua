package com.unila.ilkomp.simipaforparents.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SplashscreenResponce {
    @SerializedName("records")
    @Expose
    private List<SplashscreenRecord> records = null;

    public List<SplashscreenRecord> getRecords() {
        return records;
    }

    public void setRecords(List<SplashscreenRecord> records) {
        this.records = records;
    }

}
