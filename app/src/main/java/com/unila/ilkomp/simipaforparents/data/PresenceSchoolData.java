package com.unila.ilkomp.simipaforparents.data;

import com.unila.ilkomp.simipaforparents.model.PresenceSchool;

import java.util.ArrayList;

public class PresenceSchoolData {

    private static String[] namaMataKuliah = {
            "Mobile",
            "TBA",
            "AI",
            "DDP",
            "Android"
    };

    private static String[] hadir = {
            "11",
            "12",
            "9",
            "8",
            "1"
    };

    private static String[] tidakHadir = {
            "5",
            "11",
            "1",
            "32",
            "2"
    };

    private static String[] perbandingan = {
            "5/16",
            "11/16",
            "1/2",
            "32/12",
            "22/1"
    };

    private static String[] persentase = {
            "1%",
            "11%",
            "15%",
            "32%",
            "100%"
    };

    public static ArrayList<PresenceSchool> getListData() {
        ArrayList<PresenceSchool> list = new ArrayList<>();
        for (int position = 0; position < namaMataKuliah.length; position++) {
            PresenceSchool presenceSchool = new PresenceSchool();
            presenceSchool.setNamaMataKuliah(namaMataKuliah[position]);
            presenceSchool.setHadir(hadir[position]);
            presenceSchool.setTidakHadir(tidakHadir[position]);
            presenceSchool.setPerbandingan(perbandingan[position]);
            presenceSchool.setPersentase(persentase[position]);
            list.add(presenceSchool);
        }
        return list;
    }
}
