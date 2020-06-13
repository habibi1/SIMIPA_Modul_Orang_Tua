package com.unila.ilkomp.simipaforparents.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static com.unila.ilkomp.simipaforparents.util.TimeUtil.getDateDDMMMMYYYY;

public class CountSeminarRecord {

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
    @SerializedName("Pembimbing_1")
    @Expose
    private String pembimbing1;
    @SerializedName("Pembimbing_2")
    @Expose
    private String pembimbing2;
    @SerializedName("Pembimbing_3")
    @Expose
    private Object pembimbing3;
    @SerializedName("Pembahas_1")
    @Expose
    private String pembahas1;
    @SerializedName("Pembahas_2")
    @Expose
    private String pembahas2;
    @SerializedName("Pembahas_3")
    @Expose
    private Object pembahas3;
    @SerializedName("Peserta")
    @Expose
    private String peserta;
    @SerializedName("Id_Rekap")
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
        return getDateDDMMMMYYYY(tanggal);
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

    public String getPembimbing1() {
        return pembimbing1;
    }

    public void setPembimbing1(String pembimbing1) {
        this.pembimbing1 = pembimbing1;
    }

    public String getPembimbing2() {
        return pembimbing2;
    }

    public void setPembimbing2(String pembimbing2) {
        this.pembimbing2 = pembimbing2;
    }

    public Object getPembimbing3() {
        return pembimbing3;
    }

    public void setPembimbing3(Object pembimbing3) {
        this.pembimbing3 = pembimbing3;
    }

    public String getPembahas1() {
        return pembahas1;
    }

    public void setPembahas1(String pembahas1) {
        this.pembahas1 = pembahas1;
    }

    public String getPembahas2() {
        return pembahas2;
    }

    public void setPembahas2(String pembahas2) {
        this.pembahas2 = pembahas2;
    }

    public Object getPembahas3() {
        return pembahas3;
    }

    public void setPembahas3(Object pembahas3) {
        this.pembahas3 = pembahas3;
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
