package com.unila.ilkomp.simipaforparents.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static com.unila.ilkomp.simipaforparents.util.TimeUtil.getDateDDMMMMYYYY;

public class PresenceBimbinganRecord {

    @SerializedName("Jenis_Seminar")
    @Expose
    private String jenisSeminar;
    @SerializedName("Judul")
    @Expose
    private String judul;
    @SerializedName("Berita_Acara")
    @Expose
    private String beritaAcara;
    @SerializedName("Ruangan")
    @Expose
    private String ruangan;
    @SerializedName("Tanggal")
    @Expose
    private String tanggal;
    @SerializedName("Waktu")
    @Expose
    private String waktu;

    public String getJenisSeminar() {
        return jenisSeminar;
    }

    public void setJenisSeminar(String jenisSeminar) {
        this.jenisSeminar = jenisSeminar;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getBeritaAcara() {
        return beritaAcara;
    }

    public void setBeritaAcara(String beritaAcara) {
        this.beritaAcara = beritaAcara;
    }

    public String getRuangan() {
        return ruangan;
    }

    public void setRuangan(String ruangan) {
        this.ruangan = ruangan;
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

}
