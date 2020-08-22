package com.unila.ilkomp.simipaforparents.ui.profile;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.unila.ilkomp.simipaforparents.ListStudentsActivity;
import com.unila.ilkomp.simipaforparents.LoginActivity;
import com.unila.ilkomp.simipaforparents.R;
import com.unila.ilkomp.simipaforparents.SharedPrefManager;
import com.unila.ilkomp.simipaforparents.adapter.ListStudentDeleteAdapter;
import com.unila.ilkomp.simipaforparents.model.LogoutModel;
import com.unila.ilkomp.simipaforparents.model.LogoutResponce;
import com.unila.ilkomp.simipaforparents.model.StudentRecord;
import com.unila.ilkomp.simipaforparents.model.StudentsResponce;
import com.unila.ilkomp.simipaforparents.retrofit.ApiService;
import com.unila.ilkomp.simipaforparents.retrofit.Client;
import com.unila.ilkomp.simipaforparents.util.DeviceIDUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private long mLastClickTime = 0;
    private RecyclerView recyclerView;
    TextView dataEmpty;
    ProgressBar progressBar;
    ListStudentDeleteAdapter listStudentAdapter;

    Dialog viewDialogLogout;
    CardView dialogCardviewLogout, dialogCardviewCancel;
    View dialogViewConfirmLogout, dialogViewLoadingLogout;
    TextView dialogTextConfirmLogout, dialogTextCancel;
    ImageView dialogIconConfirmLogout, dialogScaleConfirmLogout;
    LinearLayout dialogViewButton, dialogViewButtonLogout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        dataEmpty = root.findViewById(R.id.data_empty);
        progressBar = root.findViewById(R.id.progressBar);
        recyclerView = root.findViewById(R.id.rv_delete_student);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        viewDialogLogout = new Dialog(requireContext());
        viewDialogLogout.setContentView(R.layout.dialog_confirm_logout);

        dialogCardviewLogout = viewDialogLogout.findViewById(R.id.cv_logout);
        dialogCardviewCancel = viewDialogLogout.findViewById(R.id.cv_cancel);
        dialogTextConfirmLogout = viewDialogLogout.findViewById(R.id.text_confirm_logout);
        dialogIconConfirmLogout = viewDialogLogout.findViewById(R.id.icon_confirm_logout);
        dialogViewConfirmLogout = viewDialogLogout.findViewById(R.id.view_logout);
        dialogViewLoadingLogout = viewDialogLogout.findViewById(R.id.view_loading_logout);
        dialogScaleConfirmLogout = viewDialogLogout.findViewById(R.id.scale_vertical);
        dialogTextCancel = viewDialogLogout.findViewById(R.id.tv_cancel);
        dialogViewButton = viewDialogLogout.findViewById(R.id.button);
        dialogViewButtonLogout = viewDialogLogout.findViewById(R.id.view_button_logout);

        getData();

        TextView parentsName = root.findViewById(R.id.parents_name);
        TextView parentsPhone = root.findViewById(R.id.parents_phone);
        CardView cvLogout = root.findViewById(R.id.cv_logout_fragment);
        ImageView parentPhoto = root.findViewById(R.id.circleImageProfileParent);

        cvLogout.setOnClickListener(this);
        dialogCardviewLogout.setOnClickListener(this);
        dialogCardviewCancel.setOnClickListener(this);

        //Toast.makeText(getActivity(), SharedPrefManager.getImageParent(getActivity()), Toast.LENGTH_LONG).show();

        Glide.with(getActivity())
                .load(SharedPrefManager.getImageParent(getActivity()))
                .placeholder(R.drawable.ic_person_white)
                .into(parentPhoto);

        parentsName.setText(SharedPrefManager.getNameLoggedInUser(getContext()));
        parentsPhone.setText(SharedPrefManager.getPhoneNumberLoggedInUser(getContext()));

        return root;
    }

    public void getData() {

        recyclerView.setVisibility(View.GONE);
        dataEmpty.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        ApiService apiInterface = Client.getClient().create(ApiService.class);

        Call<StudentsResponce> call = apiInterface.listMahasiswa(SharedPrefManager.getPhoneNumberLoggedInUser(getContext()));
        call.enqueue(new Callback<StudentsResponce>() {

            @Override
            public void onResponse(Call<StudentsResponce> call, retrofit2.Response<StudentsResponce> response) {

                recyclerView.setVisibility(View.VISIBLE);
                dataEmpty.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getTotalRecords() > 0 ) {

                        List<StudentRecord> studentRecordApprovedList = new ArrayList<>();
                        List<StudentRecord> studentRecordRequestList = new ArrayList<>();
                        List<StudentRecord> studentRecordRejectList = new ArrayList<>();

                        for (StudentRecord studentRecord : response.body().getStudentRecords()) {
                            if (studentRecord.getStatus().contains("Belum")) {
                                studentRecordRequestList.add(studentRecord);
                            } else if (studentRecord.getStatus().contains("Ditolak")) {
                                studentRecordRejectList.add(studentRecord);
                            } else {
                                studentRecordApprovedList.add(studentRecord);
                            }
                        }

                        studentRecordApprovedList.addAll(studentRecordRequestList);

                        listStudentAdapter = new ListStudentDeleteAdapter(getContext(), ProfileFragment.this);
                        listStudentAdapter.setListStudents(studentRecordApprovedList);
                        listStudentAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(listStudentAdapter);
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        dataEmpty.setVisibility(View.VISIBLE);

                        if (isAdded())
                            dataEmpty.setText(getResources().getString(R.string.data_kosong));

                        progressBar.setVisibility(View.GONE);
                    }
                } else {
                    recyclerView.setVisibility(View.GONE);
                    dataEmpty.setVisibility(View.VISIBLE);

                    if (isAdded())
                        dataEmpty.setText(getResources().getString(R.string.terjadi_kesalahan));

                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<StudentsResponce> call, Throwable t) {
                recyclerView.setVisibility(View.GONE);
                dataEmpty.setVisibility(View.VISIBLE);

                if (isAdded())
                    dataEmpty.setText(getResources().getString(R.string.server_error));

                progressBar.setVisibility(View.GONE);
                Log.d("c", t.getMessage());
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.cv_logout_fragment:
                Objects.requireNonNull(viewDialogLogout.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                viewDialogLogout.getWindow().setGravity(Gravity.BOTTOM);
                viewDialogLogout.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                viewDialogLogout.setCanceledOnTouchOutside(false);
                viewDialogLogout.getWindow().setWindowAnimations(R.style.DialogAnimation_up_down);
                viewDialogLogout.show();
                break;
            case R.id.cv_cancel:
                viewDialogLogout.dismiss();
                break;
            case R.id.cv_logout:
                Logout(SharedPrefManager.getPhoneNumberLoggedInUser(getContext()), DeviceIDUtil.getUniqueIMEIId(getContext()));
                break;
        }
    }

    private void Logout(String phoneNumberLoggedInUser, String uniqueIMEIId) {

        dialogViewLoadingLogout.setVisibility(View.GONE);
        dialogViewLoadingLogout.setVisibility(View.VISIBLE);

        LogoutModel logoutModel = new LogoutModel(phoneNumberLoggedInUser, uniqueIMEIId);

        ApiService apiService = Client.getClient().create(ApiService.class);

        Call<LogoutResponce> logout = apiService.logout(logoutModel);

        logout.enqueue(new Callback<LogoutResponce>() {
            @Override
            public void onResponse(@NonNull Call<LogoutResponce> call, @NonNull retrofit2.Response<LogoutResponce> response) {

                if (response.isSuccessful()) {

                    assert response.body() != null;
                    if (response.body().getResponseCode() == 200) {

                        viewDialogLogout.show();
                        SharedPrefManager.setLoggedInStatus(getActivity().getBaseContext(), false);
                        Intent intent = new Intent(getActivity().getBaseContext(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    } else {

                        viewDialogLogout.dismiss();

                        Toast.makeText(getContext(), getContext().getResources().getString(R.string.gagal_dihapus), Toast.LENGTH_SHORT).show();

                    }
                } else {

                    viewDialogLogout.dismiss();

                    Toast.makeText(getContext(), getContext().getResources().getString(R.string.terjadi_kesalahan), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<LogoutResponce> call, @NonNull Throwable t) {

                viewDialogLogout.dismiss();

                Toast.makeText(getContext(), getContext().getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void backToListStudent() {
        Intent intent = new Intent(getActivity().getBaseContext(), ListStudentsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (viewDialogLogout != null) {
            viewDialogLogout.dismiss();
            viewDialogLogout = null;
        }
    }

}