package com.unila.ilkomp.simipaforparents;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;
import com.unila.ilkomp.simipaforparents.model.AddMahasiswaModel;
import com.unila.ilkomp.simipaforparents.model.AddMahasiswaResponce;
import com.unila.ilkomp.simipaforparents.model.NotificationModel;
import com.unila.ilkomp.simipaforparents.model.NotificationResponce;
import com.unila.ilkomp.simipaforparents.model.NotificationSender;
import com.unila.ilkomp.simipaforparents.retrofit.ApiService;
import com.unila.ilkomp.simipaforparents.retrofit.Client;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMahasiswaActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressBar pDialog;
    private EditText inputNPM;
    private Button addNPM;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mahasiswa);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        pDialog = findViewById(R.id.progressBar);
        inputNPM = findViewById(R.id.input_npm);
        addNPM = findViewById(R.id.add);

        addNPM.setOnClickListener(this);
    }

    private void AddNPM() {

        String npm = inputNPM.getText().toString().trim();
        String no_hp = SharedPrefManager.getPhoneNumberLoggedInUser(getBaseContext());

        AddMahasiswaModel addMahasiswaModel = new AddMahasiswaModel(npm, no_hp);

        ApiService apiService = Client.getClient().create(ApiService.class);

        Call<AddMahasiswaResponce> createStudent = apiService.createStudent(addMahasiswaModel);

        createStudent.enqueue(new Callback<AddMahasiswaResponce>() {
            @Override
            public void onResponse(Call<AddMahasiswaResponce> call, retrofit2.Response<AddMahasiswaResponce> response) {

                pDialog.setVisibility(View.GONE);
                addNPM.setVisibility(View.VISIBLE);

                if (response.isSuccessful()){

                    assert response.body() != null;
                    if (response.body().getResponseCode() == 200){
                        //sen notif
                        sendNotifications(response.body().getRecords().get(0).getToken(),
                                getResources().getString(R.string.student_notification_title_id_1),
                        SharedPrefManager.getNameLoggedInUser(getApplicationContext())
                                +"("+SharedPrefManager.getPhoneNumberLoggedInUser(getApplicationContext())+") "+
                                getResources().getString(R.string.student_notification_message_id_1),
                                getResources().getString(R.string.student_notification_channel_name_1),
                                getResources().getString(R.string.student_notification_group_name_1),
                                response.body().getId(),
                                SharedPrefManager.getPhoneNumberLoggedInUser(getApplicationContext()),
                                SharedPrefManager.getImageParent(getApplicationContext()));

                        finishAffinity();
                        Intent intent = new Intent(AddMahasiswaActivity.this, ListStudentsActivity.class);
                        startActivity(intent);
                    } else if (response.body().getResponseCode() == 400){
                        Snackbar.make(findViewById(android.R.id.content), response.body().getMessage(),
                                Snackbar.LENGTH_SHORT).show();
                    } else if (response.body().getResponseCode() == 401){
                        Snackbar.make(findViewById(android.R.id.content), response.body().getMessage(),
                                Snackbar.LENGTH_SHORT).show();
                    } else if (response.body().getResponseCode() == 402){
                        Snackbar.make(findViewById(android.R.id.content), response.body().getMessage(),
                                Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    assert response.body() != null;
                    Snackbar.make(findViewById(android.R.id.content), response.body().getMessage(),
                            Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddMahasiswaResponce> call, Throwable t) {

                pDialog.setVisibility(View.GONE);
                addNPM.setVisibility(View.VISIBLE);

                Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.server_error),
                        Snackbar.LENGTH_SHORT).show();
            }
        });

/*        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put("npm", inputNPM.getText().toString().trim());
            request.put("no_hp", SharedPrefManager.getPhoneNumberLoggedInUser(getBaseContext()));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, BuildConfig.BASE_URL + "parent-create-student.php", request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.setVisibility(View.GONE);
                        addNPM.setVisibility(View.VISIBLE);
                        try {
                            //Check if user got registered successfully
                            if (response.getString("message").equals("Mahasiswa was created.")) {
                                //Set the user session
                                //finish();
                                finishAffinity();
                                Intent intent = new Intent(AddMahasiswaActivity.this, ListStudentsActivity.class);
                                startActivity(intent);
                            }else if(response.getString("message").equals("Mahasiswa Telah Terdaftar.")){
                                //Display error message if username is already existsing
                                Snackbar.make(findViewById(android.R.id.content), "Mahasiswa Telah Terdaftar.",
                                        Snackbar.LENGTH_SHORT).show();
                            }else if (response.getString("message").equals("Mahasiswa Tidak Terdaftar Di FMIPA.")){
                                Snackbar.make(findViewById(android.R.id.content), "Mahasiswa Tidak Terdaftar Di FMIPA atau NPM salah.",
                                        Snackbar.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.setVisibility(View.GONE);
                        addNPM.setVisibility(View.VISIBLE);

                        //Display error message whenever an error occurs
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        // Access the RequestQueue through your singleton class.
//        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsArrayRequest);*/
    }

    public void sendNotifications(String usertoken, String title, String message, String channel_id, String group_id, String id, String user, String photo_path) {
        NotificationModel data = new NotificationModel(title, message, channel_id, group_id, id, user, photo_path);
        NotificationSender sender = new NotificationSender(data, usertoken);

        Log.d("Token", "upload");

        ApiService apiService = Client.getClientNotification().create(ApiService.class);

        Call<NotificationResponce> sendNotifcation = apiService.sendNotifcation(sender);
        sendNotifcation.enqueue(new Callback<NotificationResponce>() {
            @Override
            public void onResponse(Call<NotificationResponce> call, Response<NotificationResponce> response) {
                Log.d("Token", response.code()+"");
                if (response.code() == 200) {
                    if (response.body().success != 1) {
                        Toast.makeText(AddMahasiswaActivity.this, "Failed ", Toast.LENGTH_LONG);
                    } else {
                        Log.d("Token", "Notification Success :)");
                    }
                }
            }

            @Override
            public void onFailure(Call<NotificationResponce> call, Throwable t) {
                Log.d("Token", "failed");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add:
                pDialog.setVisibility(View.VISIBLE);
                addNPM.setVisibility(View.GONE);

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                assert imm != null;
                imm.hideSoftInputFromWindow(inputNPM.getWindowToken(), 0);

                if (Check(inputNPM.getText().toString().trim()))
                    AddNPM();
                break;
        }
    }

    private boolean Check(String npm) {
        if (npm.isEmpty()) {
            inputNPM.setError("Harus Diisi!");
            pDialog.setVisibility(View.GONE);
            addNPM.setVisibility(View.VISIBLE);
            return false;
        }
        return true;
    }
}
