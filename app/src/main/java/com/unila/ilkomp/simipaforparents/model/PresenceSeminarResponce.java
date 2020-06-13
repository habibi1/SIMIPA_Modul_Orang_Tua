package com.unila.ilkomp.simipaforparents.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PresenceSeminarResponce {

    @SerializedName("records")
    @Expose
    private List<PresenceSeminarRecord> records = null;

    public List<PresenceSeminarRecord> getRecords() {
        return records;
    }

    public void setPresenceSeminar(List<PresenceSeminarRecord> records) {
        this.records = records;
    }
}
