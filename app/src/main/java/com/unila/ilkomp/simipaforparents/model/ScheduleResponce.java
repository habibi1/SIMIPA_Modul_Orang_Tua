package com.unila.ilkomp.simipaforparents.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ScheduleResponce {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("response_code")
    @Expose
    private Integer responseCode;
    @SerializedName("total_records")
    @Expose
    private Integer totalRecords;
    @SerializedName("records")
    @Expose
    private List<ScheduleRecord> records;

    public ScheduleResponce(ScheduleResponce body) {
        this.error = body.error;
        this.responseCode = body.responseCode;
        this.totalRecords = body.totalRecords;
        this.records = body.records;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public Integer getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Integer totalRecords) {
        this.totalRecords = totalRecords;
    }

    public List<ScheduleRecord> getSchedule() {
        return records;
    }

    public void setRecords(List<ScheduleRecord> records) {
        this.records = records;
    }

}
