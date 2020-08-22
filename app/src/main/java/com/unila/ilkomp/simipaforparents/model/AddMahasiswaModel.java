package com.unila.ilkomp.simipaforparents.model;

import com.google.gson.annotations.SerializedName;

public class AddMahasiswaModel {

    @SerializedName("npm")
    private String npm;
    @SerializedName("no_hp")
    private String no_hp;
    @SerializedName("jwt")
    private String jwt;

    public AddMahasiswaModel(String npm, String no_hp, String jwt) {
        this.npm = npm;
        this.no_hp = no_hp;
        this.jwt = jwt;
    }

    public String getNpm() {
        return npm;
    }

    public void setNpm(String npm) {
        this.npm = npm;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
