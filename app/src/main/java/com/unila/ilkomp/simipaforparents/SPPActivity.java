package com.unila.ilkomp.simipaforparents;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.unila.ilkomp.simipaforparents.adapter.SppAdapter;
import com.unila.ilkomp.simipaforparents.model.ScholarshipRecord;
import com.unila.ilkomp.simipaforparents.model.ScholarshipResponce;
import com.unila.ilkomp.simipaforparents.retrofit.ApiService;
import com.unila.ilkomp.simipaforparents.retrofit.Client;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

public class SPPActivity extends AppCompatActivity {

    Toolbar toolbar;
    SppAdapter sppAdapter;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    TextView dataEmpty;
    Context context;
    private ArrayList<ScholarshipRecord> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_p_p);

        context = SPPActivity.this;

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView = findViewById(R.id.rv_spp);
        progressBar = findViewById(R.id.progress_bar);
        dataEmpty = findViewById(R.id.data_empty);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getData();
    }

    private void getData() {

        recyclerView.setVisibility(View.GONE);
        dataEmpty.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        ApiService apiInterface = Client.getClient().create(ApiService.class);

        Call<ScholarshipResponce> call = apiInterface.listScholarship(SharedPrefManager.getNPMChoosed(this));
        call.enqueue(new Callback<ScholarshipResponce>() {

            @Override
            public void onResponse(Call<ScholarshipResponce> call, retrofit2.Response<ScholarshipResponce> response) {

                recyclerView.setVisibility(View.VISIBLE);
                dataEmpty.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {

                    assert response.body() != null;
                    if (response.body().getTotalRecords() > 0) {
                        sppAdapter = new SppAdapter(context);
                        sppAdapter.setListScholarship(response.body().getScholarship());
                        sppAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(sppAdapter);
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
            public void onFailure(Call<ScholarshipResponce> call, Throwable t) {
                recyclerView.setVisibility(View.GONE);
                dataEmpty.setVisibility(View.VISIBLE);
                dataEmpty.setText(getString(R.string.server_error));
                progressBar.setVisibility(View.GONE);
                Log.d("c", t.getMessage());
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
