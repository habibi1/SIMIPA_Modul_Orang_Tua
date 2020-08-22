package com.unila.ilkomp.simipaforparents.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRecord {

    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("User_Login")
    @Expose
    private String userLogin;
    @SerializedName("Display_Name")
    @Expose
    private String displayName;
    @SerializedName("Foto")
    @Expose
    private String foto;
    @SerializedName("Token")
    @Expose
    private String token;
    @SerializedName("JWT")
    @Expose
    private String jWT;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getJWT() {
        return jWT;
    }

    public void setJWT(String jWT) {
        this.jWT = jWT;
    }

}
