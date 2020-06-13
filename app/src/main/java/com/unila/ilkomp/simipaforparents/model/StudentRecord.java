package com.unila.ilkomp.simipaforparents.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentRecord {
    @SerializedName("NPM")
    @Expose
    private String nPM;
    @SerializedName("Name_Student")
    @Expose
    private String nameStudent;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Foto")
    @Expose
    private String foto;
    @SerializedName("Jurusan")
    @Expose
    private String jurusan;

    public String getNPM() {
        return nPM;
    }

    public void setNPM(String nPM) {
        this.nPM = nPM;
    }

    public String getNameStudent() {
        return nameStudent;
    }

    public void setNameStudent(String nameStudent) {
        this.nameStudent = nameStudent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setFoto(String foto){
        this.foto = foto;
    }

    public String getFoto(){
        return foto;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }
}
