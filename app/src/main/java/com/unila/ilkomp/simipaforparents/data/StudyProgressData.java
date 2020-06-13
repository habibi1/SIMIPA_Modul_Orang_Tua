package com.unila.ilkomp.simipaforparents.data;

import com.unila.ilkomp.simipaforparents.model.Schedule;
import com.unila.ilkomp.simipaforparents.model.StudyProgress;

import java.util.ArrayList;

public class StudyProgressData {

    private static String[] semester = {
            "Semester1",
            "Semester2",
            "Semester3",
            "Semester4",
            "Semester5"
    };

    public static ArrayList<StudyProgress> getListData() {
        ArrayList<StudyProgress> list = new ArrayList<>();
        for (int position = 0; position < semester.length; position++) {
            StudyProgress studyProgress = new StudyProgress();
            studyProgress.setSemester(semester[position]);
            list.add(studyProgress);
        }
        return list;
    }

}
