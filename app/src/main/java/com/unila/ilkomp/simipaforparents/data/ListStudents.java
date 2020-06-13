package com.unila.ilkomp.simipaforparents.data;

import com.unila.ilkomp.simipaforparents.model.KalenderAkademik;
import com.unila.ilkomp.simipaforparents.model.Student;

import java.util.ArrayList;

public class ListStudents {

    private static String[] studentsName = {
            "Mahasiswa1",
            "Mahasiswa2"
    };

    private static String[] studentsNPM = {
            "1617051089",
            "1617061124"
    };

    public static ArrayList<Student> getListData() {
        ArrayList<Student> list = new ArrayList<>();
        for (int position = 0; position < studentsName.length; position++) {
            Student student = new Student();
            student.setName(studentsName[position]);
            student.setNPM(studentsNPM[position]);
            list.add(student);
        }
        return list;
    }

}
