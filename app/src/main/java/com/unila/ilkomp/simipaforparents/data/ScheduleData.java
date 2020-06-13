package com.unila.ilkomp.simipaforparents.data;

import com.unila.ilkomp.simipaforparents.model.Schedule;
import com.unila.ilkomp.simipaforparents.model.Student;

import java.util.ArrayList;

public class ScheduleData {

    private static String[] jam = {
            "07.30-09.10",
            "09.20-11.00",
            "11.10-12.50",
            "13.30-15.00",
            "15.40-17.00"
    };

    private static String[] nama_schedule = {
            "Mobile",
            "TBA",
            "AI",
            "DDP",
            "Android"
    };

    private static String[] jenis = {
            "Kuliah",
            "Praktikum",
            "Kuliah",
            "Kuliah",
            "Kuliah"
    };

    public static ArrayList<Schedule> getListData() {
        ArrayList<Schedule> list = new ArrayList<>();
        for (int position = 0; position < jam.length; position++) {
            Schedule schedule = new Schedule();
            schedule.setJam(jam[position]);
            schedule.setNamaMatkul(nama_schedule[position]);
            list.add(schedule);
        }
        return list;
    }
}
