package com.unila.ilkomp.simipaforparents.model;

import com.android.volley.toolbox.StringRequest;

public class Parent {

    private String no_telepon;

    public Parent(String no_telepon) {
        this.no_telepon = no_telepon;
    }

    public String getNo_telepon() {
        return no_telepon;
    }

    public void setNo_telepon(String no_telepon) {
        this.no_telepon = no_telepon;
    }
}
