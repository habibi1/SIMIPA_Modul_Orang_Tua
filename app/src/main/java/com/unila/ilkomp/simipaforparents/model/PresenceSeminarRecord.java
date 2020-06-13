package com.unila.ilkomp.simipaforparents.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PresenceSeminarRecord {
    @SerializedName("id_seminar")
    @Expose
    private String idSeminar;
    @SerializedName("NPM")
    @Expose
    private String nPM;
    @SerializedName("Nama")
    @Expose
    private String nama;
    @SerializedName("Judul")
    @Expose
    private String judul;
    @SerializedName("Jenis")
    @Expose
    private String jenis;
    @SerializedName("Tanggal")
    @Expose
    private String tanggal;
    @SerializedName("Waktu")
    @Expose
    private String waktu;
    @SerializedName("Ruang")
    @Expose
    private String ruang;
    @SerializedName("Pbg1")
    @Expose
    private String pbg1;
    @SerializedName("Nama Pbg1")
    @Expose
    private String namaPbg1;
    @SerializedName("Pbg2")
    @Expose
    private String pbg2;
    @SerializedName("Nama Pbg 2")
    @Expose
    private String namaPbg2;
    @SerializedName("Pbs1")
    @Expose
    private String pbs1;
    @SerializedName("Nama Pbs 1")
    @Expose
    private String namaPbs1;
    @SerializedName("Pbs2")
    @Expose
    private String pbs2;
    @SerializedName("Nama Pbs 2")
    @Expose
    private String namaPbs2;
    @SerializedName("Peserta")
    @Expose
    private String peserta;
    @SerializedName("Id Rekap")
    @Expose
    private String idRekap;

    public String getIdSeminar() {
        return idSeminar;
    }

    public void setIdSeminar(String idSeminar) {
        this.idSeminar = idSeminar;
    }

    public String getNPM() {
        return nPM;
    }

    public void setNPM(String nPM) {
        this.nPM = nPM;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getRuang() {
        return ruang;
    }

    public void setRuang(String ruang) {
        this.ruang = ruang;
    }

    public String getPbg1() {
        return pbg1;
    }

    public void setPbg1(String pbg1) {
        this.pbg1 = pbg1;
    }

    public String getNamaPbg1() {
        return namaPbg1;
    }

    public void setNamaPbg1(String namaPbg1) {
        this.namaPbg1 = namaPbg1;
    }

    public String getPbg2() {
        return pbg2;
    }

    public void setPbg2(String pbg2) {
        this.pbg2 = pbg2;
    }

    public String getNamaPbg2() {
        return namaPbg2;
    }

    public void setNamaPbg2(String namaPbg2) {
        this.namaPbg2 = namaPbg2;
    }

    public String getPbs1() {
        return pbs1;
    }

    public void setPbs1(String pbs1) {
        this.pbs1 = pbs1;
    }

    public String getNamaPbs1() {
        return namaPbs1;
    }

    public void setNamaPbs1(String namaPbs1) {
        this.namaPbs1 = namaPbs1;
    }

    public String getPbs2() {
        return pbs2;
    }

    public void setPbs2(String pbs2) {
        this.pbs2 = pbs2;
    }

    public String getNamaPbs2() {
        return namaPbs2;
    }

    public void setNamaPbs2(String namaPbs2) {
        this.namaPbs2 = namaPbs2;
    }

    public String getPeserta() {
        return peserta;
    }

    public void setPeserta(String peserta) {
        this.peserta = peserta;
    }

    public String getIdRekap() {
        return idRekap;
    }

    public void setIdRekap(String idRekap) {
        this.idRekap = idRekap;
    }
}
