package com.unila.ilkomp.simipaforparents.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddMahasiswaResponce {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("response_code")
    @Expose
    private Integer responseCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("Total_records")
    @Expose
    private Integer totalRecords;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("records")
    @Expose
    private List<AddMahasiswaRecord> records = null;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Integer totalRecords) {
        this.totalRecords = totalRecords;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<AddMahasiswaRecord> getRecords() {
        return records;
    }

    public void setRecords(List<AddMahasiswaRecord> records) {
        this.records = records;
    }
}
