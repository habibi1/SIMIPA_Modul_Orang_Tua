package com.unila.ilkomp.simipaforparents.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.unila.ilkomp.simipaforparents.ListStudentsActivity;
import com.unila.ilkomp.simipaforparents.LoginActivity;
import com.unila.ilkomp.simipaforparents.R;
import com.unila.ilkomp.simipaforparents.SharedPrefManager;
import com.unila.ilkomp.simipaforparents.adapter.ListStudentDeleteAdapter;
import com.unila.ilkomp.simipaforparents.model.StudentsResponce;
import com.unila.ilkomp.simipaforparents.retrofit.ApiService;
import com.unila.ilkomp.simipaforparents.retrofit.Client;

import retrofit2.Call;
import retrofit2.Callback;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private long mLastClickTime =0;
    private RecyclerView recyclerView;
    TextView dataEmpty;
    ProgressBar progressBar;
    ListStudentDeleteAdapter listStudentAdapter;
    private ProfileViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        dataEmpty = root.findViewById(R.id.data_empty);
        progressBar = root.findViewById(R.id.progressBar);
        recyclerView = root.findViewById(R.id.rv_delete_student);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        getData();

        TextView parentsName = root.findViewById(R.id.parents_name);
        TextView parentsPhone = root.findViewById(R.id.parents_phone);
        CardView cvLogout = root.findViewById(R.id.cv_logout);
        ImageView parentPhoto = root .findViewById(R.id.circleImageProfileParent);

        cvLogout.setOnClickListener(this);

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
                    if (response.body().getTotalRecords() > 0 ){
                        listStudentAdapter = new ListStudentDeleteAdapter(getContext(), ProfileFragment.this);
                        listStudentAdapter.setListStudents(response.body().getStudentRecords());
                        listStudentAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(listStudentAdapter);
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        dataEmpty.setVisibility(View.VISIBLE);
                        dataEmpty.setText(getResources().getString(R.string.data_kosong));
                        progressBar.setVisibility(View.GONE);
                    }
                } else {
                    recyclerView.setVisibility(View.GONE);
                    dataEmpty.setVisibility(View.VISIBLE);
                    dataEmpty.setText(getResources().getString(R.string.terjadi_kesalahan));
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<StudentsResponce> call, Throwable t) {
                recyclerView.setVisibility(View.GONE);
                dataEmpty.setVisibility(View.VISIBLE);
                dataEmpty.setText(getResources().getString(R.string.server_error));
                progressBar.setVisibility(View.GONE);
                Log.d("c", t.getMessage());
            }
        });
    }

    @Override
    public void onClick(View v) {

        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        switch (v.getId()){
            case R.id.cv_logout:

                SharedPrefManager.setLoggedInStatus(getActivity().getBaseContext(),false);
                Intent intent = new Intent(getActivity().getBaseContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finishAffinity();
        }
    }

    public void backToListStudent(){
        Intent intent = new Intent(getActivity().getBaseContext(), ListStudentsActivity.class);
        startActivity(intent);
        getActivity().finishAffinity();
    }

}