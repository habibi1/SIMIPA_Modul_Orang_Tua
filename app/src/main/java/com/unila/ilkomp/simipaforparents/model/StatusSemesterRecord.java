package com.unila.ilkomp.simipaforparents.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatusSemesterRecord {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("periode")
    @Expose
    private String periode;
    @SerializedName("semester")
    @Expose
    private String semester;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("sks")
    @Expose
    private String sks;
    @SerializedName("ips")
    @Expose
    private String ips;

    public StatusSemesterRecord(String id, String periode, String semester, String status, String sks, String ips) {
        this.id = id;
        this.periode = periode;
        this.semester = semester;
        this.status = status;
        this.sks = sks;
        this.ips = ips;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPeriode() {
        return periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSks() {
        return sks;
    }

    public void setSks(String sks) {
        this.sks = sks;
    }

    public String getIps() {
        return ips;
    }

    public void setIps(String ips) {
        this.ips = ips;
    }
}
