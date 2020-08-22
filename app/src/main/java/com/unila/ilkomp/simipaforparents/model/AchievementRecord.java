package com.unila.ilkomp.simipaforparents.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AchievementRecord {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("npm")
    @Expose
    private String npm;
    @SerializedName("Nama_Kegiatan")
    @Expose
    private String namaKegiatan;
    @SerializedName("Kategori")
    @Expose
    private String kategori;
    @SerializedName("Tingkat")
    @Expose
    private String tingkat;
    @SerializedName("Prestasi")
    @Expose
    private String prestasi;
    @SerializedName("Tahun_Prestasi")
    @Expose
    private String tahunPrestasi;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Sertifikat")
    @Expose
    private String sertifikat;
    @SerializedName("Created")
    @Expose
    private String created;
    @SerializedName("Penyelenggara")
    @Expose
    private String penyelenggara;
    @SerializedName("Jabatan")
    @Expose
    private String jabatan;

    public AchievementRecord(String npm, String namaKegiatan, String tingkat, String prestasi, String tahunPrestasi, String penyelenggara, String jabatan) {
        this.npm = npm;
        this.namaKegiatan = namaKegiatan;
        this.tingkat = tingkat;
        this.prestasi = prestasi;
        this.tahunPrestasi = tahunPrestasi;
        this.penyelenggara = penyelenggara;
        this.jabatan = jabatan;
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

    public String getNamaKegiatan() {
        return namaKegiatan;
    }

    public void setNamaKegiatan(String namaKegiatan) {
        this.namaKegiatan = namaKegiatan;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getTingkat() {
        return tingkat;
    }

    public void setTingkat(String tingkat) {
        this.tingkat = tingkat;
    }

    public String getPrestasi() {
        return prestasi;
    }

    public void setPrestasi(String prestasi) {
        this.prestasi = prestasi;
    }

    public String getTahunPrestasi() {
        return tahunPrestasi;
    }

    public void setTahunPrestasi(String tahunPrestasi) {
        this.tahunPrestasi = tahunPrestasi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSertifikat() {
        return sertifikat;
    }

    public void setSertifikat(String sertifikat) {
        this.sertifikat = sertifikat;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getPenyelenggara() {
        return penyelenggara;
    }

    public void setPenyelenggara(String penyelenggara) {
        this.penyelenggara = penyelenggara;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }
}
