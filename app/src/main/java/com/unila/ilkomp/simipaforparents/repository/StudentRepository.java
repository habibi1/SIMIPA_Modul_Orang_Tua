package com.unila.ilkomp.simipaforparents.repository;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.unila.ilkomp.simipaforparents.RetrofitInstance;
import com.unila.ilkomp.simipaforparents.api.StudentService;
import com.unila.ilkomp.simipaforparents.model.Student;
import com.unila.ilkomp.simipaforparents.model.StudentWrapper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class StudentRepository {
    private ArrayList<Student> students = new ArrayList<>();
    private MutableLiveData<List<Student>> mutableLiveData= new MutableLiveData<>();
    private Application application;

    public StudentRepository(Application application){
        this.application = application;
    }

    public MutableLiveData<List<Student>> getMutableLiveData(){
        StudentService studentService = RetrofitInstance.getStudentService();
        Call<StudentWrapper> call = studentService.getData();

        call.enqueue(new Callback<StudentWrapper>() {
            @Override
            public void onResponse(Call<StudentWrapper> call, Response<StudentWrapper> response) {
                StudentWrapper mStudentWrapper = response.body();
                if (mStudentWrapper != null && mStudentWrapper.getmData() != null){
                    students = (ArrayList<Student>) mStudentWrapper.getmData();
                    mutableLiveData.setValue(students);
                }
            }

            @Override
            public void onFailure(Call<StudentWrapper> call, Throwable t) {

            }
        });
        return mutableLiveData;
    }
}
