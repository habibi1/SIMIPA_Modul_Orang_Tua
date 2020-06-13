package com.unila.ilkomp.simipaforparents.model;

import com.google.gson.annotations.SerializedName;

public class Student {

    @SerializedName("Npm")
    private String NPM;
    @SerializedName("Display Name")
    private String name;
    @SerializedName("Jurusan")
    private String department;
    @SerializedName("Foto")
    private int photo;
    @SerializedName("Email")
    private String email;
    @SerializedName("Jenis Kelamin")
    private String jenisKelamin;
    @SerializedName("No Ponsel")
    private String noTelepon;
    @SerializedName("Agama")
    private String agama;
    @SerializedName("Tempat Lahir")
    private String tempatLahir;
    @SerializedName("Tanggal Lahir")
    private String tanggalLahir;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }

    public String getAgama() {
        return agama;
    }

    public void setAgama(String agama) {
        this.agama = agama;
    }

    public String getTempatLahir() {
        return tempatLahir;
    }

    public void setTempatLahir(String tempatLahir) {
        this.tempatLahir = tempatLahir;
    }

    public String getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(String tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getNPM() {
        return NPM;
    }

    public void setNPM(String NPM) {
        this.NPM = NPM;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
