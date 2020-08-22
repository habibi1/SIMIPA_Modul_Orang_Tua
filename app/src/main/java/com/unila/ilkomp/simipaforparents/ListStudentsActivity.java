package com.unila.ilkomp.simipaforparents;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.unila.ilkomp.simipaforparents.adapter.ListStudentAdapter;
import com.unila.ilkomp.simipaforparents.model.StudentRecord;
import com.unila.ilkomp.simipaforparents.model.StudentsResponce;
import com.unila.ilkomp.simipaforparents.retrofit.ApiService;
import com.unila.ilkomp.simipaforparents.retrofit.Client;
import com.unila.ilkomp.simipaforparents.ui.profile.ProfileViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class ListStudentsActivity extends AppCompatActivity implements View.OnClickListener {

    private ProfileViewModel notificationsViewModel;
    ListStudentAdapter listStudentAdapter;
    RecyclerView recyclerView;
    Button addStudent;
    private long mLastClickTime = 0;
    ProgressBar progressBar;
    LinearLayout dataEmpty;
    TextView dataStatus;

    List<StudentRecord> studentRecordsApproved = new ArrayList<>();
    List<StudentRecord> studentRecordsRequest = new ArrayList<>();
    List<StudentRecord> studentRecordsReject = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_students);


        //make translucent statusBar on kitkat devices
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        //make fully Android Transparent Status bar
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        notificationsViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);

        addStudent = findViewById(R.id.add_student);
        progressBar = findViewById(R.id.progressBar);
        dataEmpty = findViewById(R.id.data_empty);
        dataStatus = findViewById(R.id.text_data_status);

        recyclerView = findViewById(R.id.list_students);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getData();

        addStudent.setOnClickListener(this);
    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    public void onClick(View v) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        switch (v.getId()){
            case R.id.add_student:
                Intent intentSchedule = new Intent(this, AddMahasiswaActivity.class);
                startActivity(intentSchedule);
                break;
        }
    }

    private void getData() {

        recyclerView.setVisibility(View.GONE);
        dataEmpty.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        ApiService apiInterface = Client.getClient().create(ApiService.class);

        Call<StudentsResponce> call = apiInterface.listMahasiswa(SharedPrefManager.getPhoneNumberLoggedInUser(getBaseContext()));
        call.enqueue(new Callback<StudentsResponce>() {

            @Override
            public void onResponse(@NonNull Call<StudentsResponce> call, @NonNull retrofit2.Response<StudentsResponce> response) {

                if (response.isSuccessful()) {

                    if (response.body().getTotalRecords() > 0) {

                        recyclerView.setVisibility(View.VISIBLE);
                        dataEmpty.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);

                        assert response.body() != null;
                        for (StudentRecord studentRecord : response.body().getStudentRecords()) {

                            if (studentRecord.getStatus().contains("Belum")) {
                                studentRecordsRequest.add(studentRecord);
                            } else if (studentRecord.getStatus().contains("Ditolak")) {
                                studentRecordsReject.add(studentRecord);
                            } else {
                                studentRecordsApproved.add(studentRecord);
                            }
                        }

                        studentRecordsApproved.addAll(studentRecordsRequest);

                        listStudentAdapter = new ListStudentAdapter(getApplicationContext());
                        listStudentAdapter.setListStudents(studentRecordsApproved);
                        listStudentAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(listStudentAdapter);

                        listStudentAdapter.setOnItemClickCallback(new ListStudentAdapter.OnItemClickCallback() {
                            @Override
                            public void onItemClicked(StudentRecord data) {
                                showSelected(data);
                            }
                        });
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        dataEmpty.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }

                } else {
                    recyclerView.setVisibility(View.GONE);
                    dataEmpty.setVisibility(View.VISIBLE);
                    dataStatus.setText(getString(R.string.terjadi_kesalahan));
                    progressBar.setVisibility(View.GONE);
                }
             }

             @Override
             public void onFailure(Call<StudentsResponce> call, Throwable t) {
                 recyclerView.setVisibility(View.GONE);
                 dataEmpty.setVisibility(View.VISIBLE);
                 dataStatus.setText(getString(R.string.server_error));
                 progressBar.setVisibility(View.GONE);
                 Log.d("c", t.getMessage());
             }
        });
    }

    private void showSelected(StudentRecord data) {
        Intent intent = new Intent(this, HomeActivity.class);

        String images;
        if(data.getFoto() == null)
            images = "empty";
        else
            images = data.getFoto();

        SharedPrefManager.setNPMChoosed(this, data.getNPM());
        SharedPrefManager.setNameStudentChoosed(this, data.getNameStudent());
        SharedPrefManager.setDepartmentStudentChoosed(this, data.getJurusan());
        SharedPrefManager.setImageStudentChoosed(this, images);

        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}



