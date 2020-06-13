package com.unila.ilkomp.simipaforparents.data;

import com.unila.ilkomp.simipaforparents.model.Achievement;
import com.unila.ilkomp.simipaforparents.model.StudyProgress;

import java.util.ArrayList;

public class AchievementData {


    private static String[] namaAcara = {
            "Semester1",
            "Semester2",
            "Semester3",
            "Semester4",
            "Semester5"
    };

    public static ArrayList<Achievement> getListData() {
        ArrayList<Achievement> list = new ArrayList<>();
        for (int position = 0; position < namaAcara.length; position++) {
            Achievement achievement = new Achievement();
            achievement.setNamaAcara(namaAcara[position]);
            list.add(achievement);
        }
        return list;
    }

}
