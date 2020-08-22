package com.unila.ilkomp.simipaforparents;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.unila.ilkomp.simipaforparents.adapter.StatusSemesterAdapter;
import com.unila.ilkomp.simipaforparents.model.StatusSemesterRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StatusSemesterActivity extends AppCompatActivity {

    Toolbar toolbar;
    StatusSemesterAdapter statusSemesterAdapter;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    TextView dataEmpty;
    Context context;
    private ArrayList<StatusSemesterRecord> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_p_p);

        context = StatusSemesterActivity.this;

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView = findViewById(R.id.rv_spp);
        progressBar = findViewById(R.id.progress_bar);
        dataEmpty = findViewById(R.id.data_empty);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressBar.setVisibility(View.GONE);
        StatusSemesterRecord scheduleRecord1 = new StatusSemesterRecord("1", "2017 Ganjil", "1", "Aktif", "24", "2.14");
        StatusSemesterRecord scheduleRecord2 = new StatusSemesterRecord("2", "2017 Genap", "2", "Aktif", "2", "4.00");
        StatusSemesterRecord scheduleRecord3 = new StatusSemesterRecord("2", "2017 Pendek", "2", "Aktif", "23", "3.28");
        StatusSemesterRecord scheduleRecord4 = new StatusSemesterRecord("3", "2018 Ganjil", "3", "Aktif", "24", "3.63");
        StatusSemesterRecord scheduleRecord5 = new StatusSemesterRecord("4", "2018 Genap", "4", "Aktif", "24", "3.35");
        StatusSemesterRecord scheduleRecord6 = new StatusSemesterRecord("5", "2019 Ganjil", "5", "Aktif", "22", "3.52");
        StatusSemesterRecord scheduleRecord7 = new StatusSemesterRecord("6", "2019 Genap", "6", "Aktif", "22", ".3.86");
        List<StatusSemesterRecord> coba = new ArrayList<>();
        coba.add(scheduleRecord1);
        coba.add(scheduleRecord2);
        coba.add(scheduleRecord3);
        coba.add(scheduleRecord4);
        coba.add(scheduleRecord5);

        statusSemesterAdapter = new StatusSemesterAdapter(context);
        statusSemesterAdapter.setListStatusSemester(coba);
        statusSemesterAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(statusSemesterAdapter);

        //getData();
    }

/*    private void getData() {

        recyclerView.setVisibility(View.GONE);
        dataEmpty.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        ApiService apiInterface = Client.getClient().create(ApiService.class);

        Call<StatusSemesterRecord> call = apiInterface.listStatusSemester(SharedPrefManager.getNPMChoosed(this));
        call.enqueue(new Callback<StatusSemesterRecord>() {

            @Override
            public void onResponse(Call<StatusSemesterRecord> call, retrofit2.Response<StatusSemesterRecord> response) {

                recyclerView.setVisibility(View.VISIBLE);
                dataEmpty.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {

                    assert response.body() != null;
//                    if (response.body().getTotalRecords() > 0) {
*//*                        statusSemesterAdapter = new StatusSemesterAdapter(context);
                        statusSemesterAdapter.setListStatusSemester(response.body().getScholarship());
                        statusSemesterAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(statusSemesterAdapter);*//*
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        dataEmpty.setVisibility(View.VISIBLE);
                        dataEmpty.setText(getString(R.string.data_kosong));
                        progressBar.setVisibility(View.GONE);
                    }

                } else {
                    recyclerView.setVisibility(View.GONE);
                    dataEmpty.setVisibility(View.VISIBLE);
                    dataEmpty.setText(getString(R.string.terjadi_kesalahan));
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<StatusSemesterRecord> call, Throwable t) {
                recyclerView.setVisibility(View.GONE);
                dataEmpty.setVisibility(View.VISIBLE);
                dataEmpty.setText(getString(R.string.server_error));
                progressBar.setVisibility(View.GONE);
                Log.d("c", t.getMessage());
            }
        });
    }*/

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
