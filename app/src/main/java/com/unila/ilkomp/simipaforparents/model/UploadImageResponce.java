package com.unila.ilkomp.simipaforparents.model;

import com.google.gson.annotations.SerializedName;

public class UploadImageResponce {

    // variable name should be same as in the json response from php
    @SerializedName("success")
    boolean success;
    @SerializedName("message")
    String message;
    String getMessage() {
        return message;
    }
    public boolean getSuccess() {
        return success;
    }

}
