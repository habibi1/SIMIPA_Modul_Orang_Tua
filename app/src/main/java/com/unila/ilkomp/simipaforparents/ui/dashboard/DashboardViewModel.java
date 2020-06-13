package com.unila.ilkomp.simipaforparents.ui.dashboard;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.unila.ilkomp.simipaforparents.R;
import com.unila.ilkomp.simipaforparents.model.Student;
import com.unila.ilkomp.simipaforparents.repository.StudentRepository;

import java.util.List;

public class DashboardViewModel extends AndroidViewModel {

    private MutableLiveData<String> student_NPM;
    private MutableLiveData<String> student_name;
    private MutableLiveData<String> student_department;
    private MutableLiveData<Integer> student_photo;

    private StudentRepository studentRepository;

    public DashboardViewModel(@NonNull Application application) {
        super(application);

        studentRepository = new StudentRepository(application);
/*        student_NPM = new MutableLiveData<>();
        student_name = new MutableLiveData<>();
        student_department = new MutableLiveData<>();
        student_photo = new MutableLiveData<>();

        student_NPM.setValue("161705XXXX");
        student_name.setValue("Nama");
        student_department.setValue("S1 Ilmu Komputer");
        student_photo.setValue(R.drawable.logo_gradient_background);*/
    }

    public LiveData<List<Student>> getAllData(){
        return studentRepository.getMutableLiveData();
    }

/*    public LiveData<String> getStudentNPM() {
        return student_NPM;
    }
    public LiveData<String> getStudentName() {
        return student_name;
    }
    public LiveData<String> getStudentDepartment() {
        return student_department;
    }
    public LiveData<Integer> getStudentPhoto() {
        return student_photo;
    }*/
}