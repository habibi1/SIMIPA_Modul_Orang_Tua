package com.unila.ilkomp.simipaforparents;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.unila.ilkomp.simipaforparents.model.SplashscreenRecord;
import com.unila.ilkomp.simipaforparents.model.SplashscreenResponce;
import com.unila.ilkomp.simipaforparents.retrofit.ApiService;
import com.unila.ilkomp.simipaforparents.retrofit.Client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

public class SplashscreenActivity extends AppCompatActivity {

    private List<SplashscreenRecord> splashscreenRecords = new ArrayList<>();
    private int waktu_loading=2000;
    String current_date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    private ImageView ivSplashscreen, ivLogo, ivCircle;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        ivSplashscreen = findViewById(R.id.image_splashscreen);
        ivCircle = findViewById(R.id.image_circle);
        ivLogo = findViewById(R.id.image_logo);
        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.VISIBLE);

        LoadData();
    }

    private void LoadData() {

        ApiService apiInterface = Client.getClient().create(ApiService.class);

        Call<SplashscreenResponce> call = apiInterface.splashscreen("1");
        call.enqueue(new Callback<SplashscreenResponce>() {

            @Override
            public void onResponse(Call<SplashscreenResponce> call, retrofit2.Response<SplashscreenResponce> response) {

                progressBar.setVisibility(View.GONE);
                ivSplashscreen.setVisibility(View.VISIBLE);

                if (response.body() != null) {

                    splashscreenRecords = response.body().getRecords();

                    ShowModified();
                } else {
                    StartShow();
                }
            }

            @Override
            public void onFailure(Call<SplashscreenResponce> call, Throwable t) {
                Log.d("Error.Response", "");
                StartShow();
                Toast.makeText(getApplicationContext(),"Server Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void ShowModified() {
        String imagePath = BuildConfig.BASE_API + splashscreenRecords.get(0).getVector();

        Glide.with(getApplicationContext())
                .load(imagePath)
                .into(ivSplashscreen);

        StartShowModified();
    }

    private void StartShowModified() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //setelah loading maka akan langsung berpindah ke home activity
                Intent login = new Intent(SplashscreenActivity.this, IntroActivity.class);
                startActivity(login);
                finish();
            }
        },waktu_loading);
    }

    private void StartShow() {

        ivSplashscreen.setVisibility(View.VISIBLE);
        ivCircle.setVisibility(View.VISIBLE);
        ivLogo.setVisibility(View.VISIBLE);
        //progressBar.setVisibility(View.GONE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //setelah loading maka akan langsung berpindah ke home activity
                Intent login = new Intent(SplashscreenActivity.this, IntroActivity.class);
                startActivity(login);
                finish();

            }
        },waktu_loading);
    }


}
