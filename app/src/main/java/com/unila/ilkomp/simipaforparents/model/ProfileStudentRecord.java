package com.unila.ilkomp.simipaforparents.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static com.unila.ilkomp.simipaforparents.util.TimeUtil.getDateDDMMMMYYYY;

public class ProfileStudentRecord {

    @SerializedName("Npm")
    @Expose
    private String npm;
    @SerializedName("Display_Name")
    @Expose
    private String displayName;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Jenis_Kelamin")
    @Expose
    private String jenisKelamin;
    @SerializedName("No_Ponsel")
    @Expose
    private String noPonsel;
    @SerializedName("Agama")
    @Expose
    private String agama;
    @SerializedName("Tempat_Lahir")
    @Expose
    private String tempatLahir;
    @SerializedName("Tanggal_Lahir")
    @Expose
    private String tanggalLahir;
    @SerializedName("Foto")
    @Expose
    private String foto;
    @SerializedName("Jurusan")
    @Expose
    private String jurusan;
    @SerializedName("Dosen_PA")
    @Expose
    private String dosenPA;

    public String getNpm() {
        return npm;
    }

    public void setNpm(String npm) {
        this.npm = npm;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

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

    public String getNoPonsel() {
        return noPonsel;
    }

    public void setNoPonsel(String noPonsel) {
        this.noPonsel = noPonsel;
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
        return getDateDDMMMMYYYY(tanggalLahir);
    }

    public void setTanggalLahir(String tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public String getDosenPA() {
        return dosenPA;
    }

    public void setDosenPA(String dosenPA) {
        this.dosenPA = dosenPA;
    }

}
