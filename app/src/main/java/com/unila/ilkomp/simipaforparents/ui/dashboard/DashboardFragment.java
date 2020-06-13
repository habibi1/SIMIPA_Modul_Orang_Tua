package com.unila.ilkomp.simipaforparents.ui.dashboard;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.unila.ilkomp.simipaforparents.AchievementActivity;
import com.unila.ilkomp.simipaforparents.ImportantDateActivity;
import com.unila.ilkomp.simipaforparents.PresenceActivity;
import com.unila.ilkomp.simipaforparents.R;
import com.unila.ilkomp.simipaforparents.SPPActivity;
import com.unila.ilkomp.simipaforparents.ScheduleActivity;
import com.unila.ilkomp.simipaforparents.ScholarshipActivity;
import com.unila.ilkomp.simipaforparents.SharedPrefManager;
import com.unila.ilkomp.simipaforparents.StudyProgressActivity;
import com.unila.ilkomp.simipaforparents.model.ProfileStudentRecord;
import com.unila.ilkomp.simipaforparents.model.ProfileStudentResponce;
import com.unila.ilkomp.simipaforparents.retrofit.ApiService;
import com.unila.ilkomp.simipaforparents.retrofit.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

import static com.unila.ilkomp.simipaforparents.util.ControlUtil.elapseClick;

public class DashboardFragment extends Fragment implements View.OnClickListener {

    public CardView btn_schedule, btn_presence, btn_study_progress, btn_achievement,
                    btn_spp, btn_scholarship, btn_important_date, btn_profile_student;

    private DashboardViewModel dashboardViewModel;

    private long mLastClickTime = 0;

    SwipeRefreshLayout swipeRefresh;

    private TextView students_name;
    private TextView students_NPM;
    private TextView students_department;
    private TextView parent_name;
    private ImageView students_photo;

    private TextView dialogNamaMahasiswa, dialogNpmMahasiswa, dialogJurusanMahasiswa, dialogTempatLahir,
                    dialogTanggalLahir, dialogJenisKelamin, dialogAgama, dialogTelepon, dialogEmail, dialogClose,
                    dialogDataEmpty;

    ProgressBar dialogProgresBar;

    private ImageView dialogPhoto;
    View viewProfileStudent;
    Dialog viewDialogProfileStudent;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        viewDialogProfileStudent = new Dialog(Objects.requireNonNull(getContext()));
        viewDialogProfileStudent.setContentView(R.layout.dialog_profile_student);

        viewProfileStudent = viewDialogProfileStudent.findViewById(R.id.view_profile_student);
        dialogDataEmpty = viewDialogProfileStudent.findViewById(R.id.tv_empty_data);
        dialogProgresBar = viewDialogProfileStudent.findViewById(R.id.progressBar);
        dialogNamaMahasiswa = viewDialogProfileStudent.findViewById(R.id.tv_nama_mahasiswa);
        dialogNpmMahasiswa = viewDialogProfileStudent.findViewById(R.id.tv_npm_mahasiswa);
        dialogJurusanMahasiswa = viewDialogProfileStudent.findViewById(R.id.tv_jurusan);
        dialogTempatLahir = viewDialogProfileStudent.findViewById(R.id.tv_tempat_lahir);
        dialogTanggalLahir = viewDialogProfileStudent.findViewById(R.id.tv_tanggal_lahir);
        dialogJenisKelamin = viewDialogProfileStudent.findViewById(R.id.tv_jenis_kelamin);
        dialogAgama = viewDialogProfileStudent.findViewById(R.id.tv_agama);
        dialogTelepon = viewDialogProfileStudent.findViewById(R.id.tv_nomor_telepon);
        dialogEmail = viewDialogProfileStudent.findViewById(R.id.tv_email);
        dialogClose = viewDialogProfileStudent.findViewById(R.id.txtclose);
        dialogPhoto = viewDialogProfileStudent.findViewById(R.id.image_student);

        students_name = root.findViewById(R.id.students_name);
        students_NPM = root.findViewById(R.id.students_NPM);
        students_department = root.findViewById(R.id.students_department);
        students_photo = root.findViewById(R.id.students_photo);
        parent_name = root. findViewById(R.id.parents_name);

        btn_profile_student = root.findViewById(R.id.btn_profile_student);
        btn_schedule = root.findViewById(R.id.btn_schedule);
        btn_presence = root.findViewById(R.id.btn_presence);
        btn_study_progress = root.findViewById(R.id.btn_study_progress);
        btn_achievement = root.findViewById(R.id.btn_achievement);
        btn_spp = root.findViewById(R.id.btn_spp);
        btn_scholarship = root.findViewById(R.id.btn_scholarship);
        btn_important_date = root.findViewById(R.id.btn_important_date);

        btn_profile_student.setOnClickListener(this);
        btn_schedule.setOnClickListener(this);
        btn_presence.setOnClickListener(this);
        btn_study_progress.setOnClickListener(this);
        btn_achievement.setOnClickListener(this);
        btn_spp.setOnClickListener(this);
        btn_scholarship.setOnClickListener(this);
        btn_important_date.setOnClickListener(this);
        dialogClose.setOnClickListener(this);

        btn_important_date.setMinimumWidth(btn_schedule.getWidth());


        getData();

        return root;
    }

    private void getData() {

        students_NPM.setText(SharedPrefManager.getNPMChoosed(getContext()));
        students_name.setText(SharedPrefManager.getNameStudentChoosed(getContext()));
        students_department.setText(SharedPrefManager.getDepartmentStudentChoosed(getContext()));
        parent_name.setText(SharedPrefManager.getNameLoggedInUser(getContext()));

        Glide.with(getActivity())
                .load(SharedPrefManager.getImageStudentChoosed(getActivity()))
                .placeholder(R.drawable.ic_person_white)
                .into(students_photo);

        Picasso.get().load(SharedPrefManager.getImageStudentChoosed(getActivity())).into(dialogPhoto);

        viewProfileStudent.setVisibility(View.GONE);
        dialogDataEmpty.setVisibility(View.GONE);
        dialogProgresBar.setVisibility(View.VISIBLE);

        ApiService apiInterface = Client.getClient().create(ApiService.class);

        Call<ProfileStudentResponce> call = apiInterface.profilStudent(SharedPrefManager.getNPMChoosed(getContext()));
        call.enqueue(new Callback<ProfileStudentResponce>() {

            @Override
            public void onResponse(Call<ProfileStudentResponce> call, retrofit2.Response<ProfileStudentResponce> response) {

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getTotalRecords() == 1){

                        viewProfileStudent.setVisibility(View.VISIBLE);
                        dialogDataEmpty.setVisibility(View.GONE);
                        dialogProgresBar.setVisibility(View.GONE);

                        List<ProfileStudentRecord> recordProfil = new ArrayList<>(response.body().getRecords());

                        dialogNamaMahasiswa.setText(recordProfil.get(0).getDisplayName());
                        dialogNpmMahasiswa.setText(recordProfil.get(0).getNpm());
                        dialogJurusanMahasiswa.setText(recordProfil.get(0).getJurusan());
                        dialogTempatLahir.setText(recordProfil.get(0).getTempatLahir());
                        dialogTanggalLahir.setText(recordProfil.get(0).getTanggalLahir());
                        dialogAgama.setText(recordProfil.get(0).getAgama());
                        dialogTelepon.setText(recordProfil.get(0).getNoPonsel());
                        dialogEmail.setText(recordProfil.get(0).getEmail());
                        dialogJenisKelamin.setText(recordProfil.get(0).getJenisKelamin());

                    } else {
                        viewProfileStudent.setVisibility(View.GONE);
                        dialogDataEmpty.setVisibility(View.VISIBLE);
                        dialogDataEmpty.setText(getString(R.string.data_kosong));
                        dialogProgresBar.setVisibility(View.GONE);
                    }
                } else {
                    viewProfileStudent.setVisibility(View.GONE);
                    dialogDataEmpty.setVisibility(View.VISIBLE);
                    dialogDataEmpty.setText(getString(R.string.terjadi_kesalahan));
                    dialogProgresBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ProfileStudentResponce> call, Throwable t) {
                viewProfileStudent.setVisibility(View.GONE);
                dialogDataEmpty.setVisibility(View.VISIBLE);
                dialogDataEmpty.setText(getString(R.string.server_error));
                dialogProgresBar.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onClick(View v) {

        elapseClick();

        switch (v.getId()){
            case R.id.btn_profile_student:
                viewDialogProfileStudent.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                viewDialogProfileStudent.getWindow().setGravity(Gravity.CENTER);
                viewDialogProfileStudent.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                viewDialogProfileStudent.show();
                break;
            case R.id.txtclose:
                viewDialogProfileStudent.dismiss();
                break;
            case R.id.btn_schedule:
                Intent intentSchedule = new Intent(getActivity(), ScheduleActivity.class);
                startActivity(intentSchedule);
                break;
            case R.id.btn_presence:
                Intent intentPresence = new Intent(getActivity(), PresenceActivity.class);
                startActivity(intentPresence);
                break;
            case R.id.btn_study_progress:
                Intent intentStudyProgress = new Intent(getActivity(), StudyProgressActivity.class);
                startActivity(intentStudyProgress);
                break;
            case R.id.btn_achievement:
                Intent intentAchievement = new Intent(getActivity(), AchievementActivity.class);
                startActivity(intentAchievement);
                break;
            case R.id.btn_spp:
                Intent intentThesis = new Intent(getActivity(), SPPActivity.class);
                startActivity(intentThesis);
                break;
            case R.id.btn_scholarship:
                Intent intentScholarship = new Intent(getActivity(), ScholarshipActivity.class);
                startActivity(intentScholarship);
                break;
            case R.id.btn_important_date:
                Intent intentImportantDate = new Intent(getActivity(), ImportantDateActivity.class);
                startActivity(intentImportantDate);
                break;
        }
    }
}