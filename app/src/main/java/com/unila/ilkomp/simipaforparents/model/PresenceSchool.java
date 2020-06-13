package com.unila.ilkomp.simipaforparents.model;

public class PresenceSchool {

    private String namaMataKuliah;
    private String hadir;
    private String tidakHadir;
    private String perbandingan;
    private String persentase;

    public String getNamaMataKuliah() {
        return namaMataKuliah;
    }

    public void setNamaMataKuliah(String namaMataKuliah) {
        this.namaMataKuliah = namaMataKuliah;
    }

    public String getHadir() {
        return hadir;
    }

    public void setHadir(String hadir) {
        this.hadir = hadir;
    }

    public String getTidakHadir() {
        return tidakHadir;
    }

    public void setTidakHadir(String tidakHadir) {
        this.tidakHadir = tidakHadir;
    }

    public String getPerbandingan() {
        return perbandingan;
    }

    public void setPerbandingan(String perbandingan) {
        this.perbandingan = perbandingan;
    }

    public String getPersentase() {
        return persentase;
    }

    public void setPersentase(String persentase) {
        this.persentase = persentase;
    }
}
