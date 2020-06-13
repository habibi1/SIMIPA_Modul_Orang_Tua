package com.unila.ilkomp.simipaforparents.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SplashscreenRecord {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("event")
    @Expose
    private String event;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("vector")
    @Expose
    private String vector;
    @SerializedName("flag")
    @Expose
    private String flag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVector() {
        return vector;
    }

    public void setVector(String vector) {
        this.vector = vector;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
