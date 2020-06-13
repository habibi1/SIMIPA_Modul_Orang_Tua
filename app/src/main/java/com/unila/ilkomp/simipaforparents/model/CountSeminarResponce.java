package com.unila.ilkomp.simipaforparents.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountSeminarResponce {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("response_code")
    @Expose
    private Integer responseCode;
    @SerializedName("total_records")
    @Expose
    private Integer totalRecords;
    @SerializedName("Jumlah_Seminar_KP")
    @Expose
    private Integer jumlahSeminarKP;
    @SerializedName("Jumlah_Seminar_Usul")
    @Expose
    private Integer jumlahSeminarUsul;
    @SerializedName("Jumlah_Seminar_Hasil")
    @Expose
    private Integer jumlahSeminarHasil;
    @SerializedName("records")
    @Expose
    private List<CountSeminarRecord> records = null;

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

    public Integer getJumlahSeminarKP() {
        return jumlahSeminarKP;
    }

    public void setJumlahSeminarKP(Integer jumlahSeminarKP) {
        this.jumlahSeminarKP = jumlahSeminarKP;
    }

    public Integer getJumlahSeminarUsul() {
        return jumlahSeminarUsul;
    }

    public void setJumlahSeminarUsul(Integer jumlahSeminarUsul) {
        this.jumlahSeminarUsul = jumlahSeminarUsul;
    }

    public Integer getJumlahSeminarHasil() {
        return jumlahSeminarHasil;
    }

    public void setJumlahSeminarHasil(Integer jumlahSeminarHasil) {
        this.jumlahSeminarHasil = jumlahSeminarHasil;
    }

    public List<CountSeminarRecord> getSeminarRecords() {
        return records;
    }

    public void setRecords(List<CountSeminarRecord> records) {
        this.records = records;
    }

}
