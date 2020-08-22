package com.unila.ilkomp.simipaforparents.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScholarshipRecord {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("npm")
    @Expose
    private String npm;
    @SerializedName("semester")
    @Expose
    private String semester;
    @SerializedName("Tahun_Beasiswa")
    @Expose
    private String tahunBeasiswa;
    @SerializedName("jenis_beasiswa")
    @Expose
    private String jenisBeasiswa;
    @SerializedName("nama_beasiswa")
    @Expose
    private String namaBeasiswa;
    @SerializedName("no_usulan")
    @Expose
    private String noUsulan;
    @SerializedName("status_ajukan")
    @Expose
    private String statusAjukan;
    @SerializedName("keterangan")
    @Expose
    private String keterangan;

    public ScholarshipRecord(String id, String npm, String semester, String tahunBeasiswa, String jenisBeasiswa, String namaBeasiswa, String noUsulan, String statusAjukan, String keterangan) {
        this.id = id;
        this.npm = npm;
        this.semester = semester;
        this.tahunBeasiswa = tahunBeasiswa;
        this.jenisBeasiswa = jenisBeasiswa;
        this.namaBeasiswa = namaBeasiswa;
        this.noUsulan = noUsulan;
        this.statusAjukan = statusAjukan;
        this.keterangan = keterangan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNpm() {
        return npm;
    }

    public void setNpm(String npm) {
        this.npm = npm;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getTahunBeasiswa() {
        return tahunBeasiswa;
    }

    public void setTahunBeasiswa(String tahunBeasiswa) {
        this.tahunBeasiswa = tahunBeasiswa;
    }

    public String getJenisBeasiswa() {
        return jenisBeasiswa;
    }

    public void setJenisBeasiswa(String jenisBeasiswa) {
        this.jenisBeasiswa = jenisBeasiswa;
    }

    public String getNamaBeasiswa() {
        return namaBeasiswa;
    }

    public void setNamaBeasiswa(String namaBeasiswa) {
        this.namaBeasiswa = namaBeasiswa;
    }

    public String getNoUsulan() {
        return noUsulan;
    }

    public void setNoUsulan(String noUsulan) {
        this.noUsulan = noUsulan;
    }

    public String getStatusAjukan() {
        return statusAjukan;
    }

    public void setStatusAjukan(String statusAjukan) {
        this.statusAjukan = statusAjukan;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
