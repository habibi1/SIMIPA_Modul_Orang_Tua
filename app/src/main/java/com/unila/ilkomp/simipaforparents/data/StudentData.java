package com.unila.ilkomp.simipaforparents.data;

import com.unila.ilkomp.simipaforparents.R;
import com.unila.ilkomp.simipaforparents.model.Student;

public class StudentData {

    private static String NPM="161705XXXX";
    private static String name="Nama";
    private static String department="Ilmu Komputer";
    private static int photo = R.drawable.logo_gradient_background;

    public void getListData(){

        Student student = new Student();

        student.setNPM(NPM);
        student.setName(name);
        student.setDepartment(department);
        student.setPhoto(photo);

    }
}
