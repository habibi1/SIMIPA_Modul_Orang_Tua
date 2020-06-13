package com.unila.ilkomp.simipaforparents.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class StudentWrapper {

    @SerializedName("records")
    private List<Student> mData;

    public List<Student> getmData() {
        return mData;
    }

    public void setmData(List<Student> mData) {
        this.mData = mData;
    }
}
