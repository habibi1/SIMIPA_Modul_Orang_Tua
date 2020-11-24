package com.unila.ilkomp.simipaforparents.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScholarshipRecord {

    @SerializedName("id_beasiswa_mhs")
    @Expose
    private String idBeasiswaMhs;
    @SerializedName("id_beasiswa_aktif")
    @Expose
    private String idBeasiswaAktif;
    @SerializedName("npm")
    @Expose
    private String npm;
    @SerializedName("nama_beasiswa")
    @Expose
    private String namaBeasiswa;
    @SerializedName("penyelenggara")
    @Expose
    private String penyelenggara;
    @SerializedName("tahun")
    @Expose
    private String tahun;
    @SerializedName("semester")
    @Expose
    private String semester;
    @SerializedName("keterangan")
    @Expose
    private String keterangan;
    @SerializedName("status")
    @Expose
    private String status;

    public String getIdBeasiswaMhs() {
        return idBeasiswaMhs;
    }

    public void setIdBeasiswaMhs(String idBeasiswaMhs) {
        this.idBeasiswaMhs = idBeasiswaMhs;
    }

    public String getIdBeasiswaAktif() {
        return idBeasiswaAktif;
    }

    public void setIdBeasiswaAktif(String idBeasiswaAktif) {
        this.idBeasiswaAktif = idBeasiswaAktif;
    }

    public String getNpm() {
        return npm;
    }

    public void setNpm(String npm) {
        this.npm = npm;
    }

    public String getNamaBeasiswa() {
        return namaBeasiswa;
    }

    public void setNamaBeasiswa(String namaBeasiswa) {
        this.namaBeasiswa = namaBeasiswa;
    }

    public String getPenyelenggara() {
        return penyelenggara;
    }

    public void setPenyelenggara(String penyelenggara) {
        this.penyelenggara = penyelenggara;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
