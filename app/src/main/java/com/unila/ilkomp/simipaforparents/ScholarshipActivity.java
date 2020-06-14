package com.unila.ilkomp.simipaforparents;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.unila.ilkomp.simipaforparents.adapter.ScholarshipAdapter;
import com.unila.ilkomp.simipaforparents.model.ScholarshipRecord;
import com.unila.ilkomp.simipaforparents.model.ScholarshipResponce;
import com.unila.ilkomp.simipaforparents.retrofit.ApiService;
import com.unila.ilkomp.simipaforparents.retrofit.Client;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

public class ScholarshipActivity extends AppCompatActivity {

    ScholarshipAdapter scholarshipAdapter;
    RecyclerView recyclerView;
    ImageView imageStudent;
    TextView nameStudent, npmStudent;
    private ArrayList<ScholarshipRecord> list = new ArrayList<>();
    ProgressBar progressBar;
    TextView dataEmpty;
    Toolbar toolbar;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scholarship);

        context = ScholarshipActivity.this;

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView = findViewById(R.id.rv_scholarship);
        progressBar = findViewById(R.id.progressBar);
        dataEmpty = findViewById(R.id.data_empty);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getData();

        showRecyclerList();
    }

    private void showRecyclerList(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ScholarshipAdapter scholarshipAdapter = new ScholarshipAdapter(list);
        recyclerView.setAdapter(scholarshipAdapter);
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
                    if (response.body().getTotalRecords() > 0){
                        scholarshipAdapter = new ScholarshipAdapter(context);
                        scholarshipAdapter.setListScholarship(response.body().getScholarship());
                        scholarshipAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(scholarshipAdapter);

                        scholarshipAdapter.setOnItemClickCallback(new ScholarshipAdapter.OnItemClickCallback() {
                            @Override
                            public void onItemClicked(ScholarshipRecord data) {
                                Toast.makeText(ScholarshipActivity.this, "worrrk" + data, Toast.LENGTH_SHORT).show();
                            }
                        });
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
