package com.unila.ilkomp.simipaforparents;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.unila.ilkomp.simipaforparents.model.LoginRecord;
import com.unila.ilkomp.simipaforparents.model.LoginResponce;
import com.unila.ilkomp.simipaforparents.model.UpdateTokenResponce;
import com.unila.ilkomp.simipaforparents.retrofit.ApiService;
import com.unila.ilkomp.simipaforparents.retrofit.Client;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import static com.unila.ilkomp.simipaforparents.util.ControlUtil.elapseClick;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private int PHONE_STATE_CODE=1;
    private long mLastClickTime = 0;

    private Button login;
    private TextView register;
    private EditText input_telepon, input_password;
    private ProgressBar progressBar;
    private String imei;
    private String ip;
    //private String device_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

    @Override
    protected void onStart() {
        super.onStart();
        if (SharedPrefManager.getLoggedInStatus(getBaseContext())) {
            startActivity(new Intent(getBaseContext(), ListStudentsActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        reqPhonePermission();

        imei = getUniqueIMEIId(this);
        ip = getIP();

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

        login = findViewById(R.id.login);
        register = findViewById(R.id.tv_register);
        input_telepon = findViewById(R.id.input_telepon);
        input_password = findViewById(R.id.input_password);
        progressBar = findViewById(R.id.progressBar);

        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        elapseClick();

        switch (v.getId()){
            case R.id.login:
                login.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);

                String teks_telepon = input_telepon.getText().toString().trim();
                String teks_password = input_password.getText().toString().trim();

                if (Check(teks_telepon, teks_password)){
                    Login(teks_telepon, teks_password, imei, ip);
                }
                break;
            case R.id.tv_register:
                Intent intent = new Intent(LoginActivity.this, VerifyPhoneNumberActivity.class);
                startActivity(intent);
                break;
        }
    }

    public String getIP(){
        String phone_ip;

        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        phone_ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

        if (phone_ip.equals("0.0.0.0")){
            try {
                List<NetworkInterface>  interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
                for (NetworkInterface intf : interfaces){
                    List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                    for (InetAddress addr : addrs){
                        if (!addr.isLoopbackAddress()){
                            return addr.getHostAddress();
                        }
                    }
                }
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }

        return phone_ip;
    }

    public String getUniqueIMEIId(Context context) {

        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei;

        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                imei = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                imei = telephonyManager.getImei();
            } else {
                imei = telephonyManager.getDeviceId();
            }

            if (imei != null && !imei.isEmpty()) {
                return imei;
            } else {
                return android.os.Build.SERIAL;
            }

        }

        return "not_found";
    }

    private void reqPhonePermission(){
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_PHONE_STATE},PHONE_STATE_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PHONE_STATE_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Snackbar.make(findViewById(android.R.id.content), "Permission granted",
                        Snackbar.LENGTH_SHORT).show();
            } else {
                Snackbar.make(findViewById(android.R.id.content), "Permission denied",
                        Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    private void Login(final String teks_telepon, final String teks_password, final String teks_imei, final String teks_ip) {

        ApiService apiService = Client.getClient().create(ApiService.class);

        Call<LoginResponce> loginProcess = apiService.login(teks_telepon, teks_password, teks_imei, teks_ip);
        loginProcess.enqueue(new Callback<LoginResponce>() {
            @Override
            public void onResponse(Call<LoginResponce> call, retrofit2.Response<LoginResponce> response) {
                if (response.code() != 500){
                    if (response.body().getResponseCode() == 200){
                        List<LoginRecord> loginRecord = response.body().getRecords();

                        FirebaseInstanceId.getInstance().getInstanceId()
                                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                        if (!task.isSuccessful()) {
                                            Log.w("Token", "getInstanceId failed", task.getException());
                                            return;
                                        }

                                        // Get new Instance ID token
                                        String token = task.getResult().getToken();

                                        UpdateToken(teks_telepon, teks_imei, token, loginRecord);

                                        // Log and toast
//                                        String msg = getString(R.string.msg_token_fmt, token);
                                        Log.d("Token", token);
//                                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                                    }
                                });

                    } else {
                        progressBar.setVisibility(View.GONE);
                        login.setVisibility(View.VISIBLE);
                        Snackbar.make(findViewById(android.R.id.content), "Login Gagal!",
                                Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    progressBar.setVisibility(View.GONE);
                    login.setVisibility(View.VISIBLE);
                    Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.terjadi_kesalahan),
                            Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponce> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                login.setVisibility(View.VISIBLE);

                Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.server_error),
                        Snackbar.LENGTH_SHORT).show();

            }
        });
    }

    public void UpdateToken(String telepon, String imei, String token, List<LoginRecord> loginRecord) {
        ApiService apiService = Client.getClient().create(ApiService.class);

        Call<UpdateTokenResponce> updateToken = apiService.updateToken(telepon, imei, token);
        updateToken.enqueue(new Callback<UpdateTokenResponce>() {
            @Override
            public void onResponse(Call<UpdateTokenResponce> call, retrofit2.Response<UpdateTokenResponce> response) {

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getResponseCode() == 200) {

                        if (response.body().getTotalRecords() > 0){
                            Log.d("Update Token", "Success Refreshed token to Server: " + token);

                            SharedPrefManager.setPhoneNumberLoggedInUser(getBaseContext(), telepon);
                            SharedPrefManager.setNameLoggedInUser(getBaseContext(), loginRecord.get(0).getNama());
                            SharedPrefManager.setImageParent(getBaseContext(), loginRecord.get(0).getFoto());
                            SharedPrefManager.setLoggedInStatus(getBaseContext(),true);

                            Intent intent = new  Intent(LoginActivity.this, ListStudentsActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            Log.d("Update Token", "Failed Refreshed token to Server: " + response.message());

                            progressBar.setVisibility(View.GONE);
                            login.setVisibility(View.VISIBLE);
                            Snackbar.make(findViewById(android.R.id.content), "Gagal memperbaharui token.",
                                    Snackbar.LENGTH_SHORT).show();
                        }

                    } else if (response.body().getResponseCode() == 400){
                        Log.d("Update Token", "Something Wrong!!!");

                        progressBar.setVisibility(View.GONE);
                        login.setVisibility(View.VISIBLE);
                        Snackbar.make(findViewById(android.R.id.content), "Terjadi kesalahan saat memperbaharui token.",
                                Snackbar.LENGTH_SHORT).show();

                    }
                } else {
                    Log.d("Update Token", "Something Wrong!!!");
                    progressBar.setVisibility(View.GONE);
                    login.setVisibility(View.VISIBLE);

                    Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.terjadi_kesalahan),
                            Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateTokenResponce> call, Throwable t) {
                Log.d("Update Token", "Server Error!!!");

                progressBar.setVisibility(View.GONE);
                login.setVisibility(View.VISIBLE);

                Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.server_error),
                        Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private boolean Check(String telepon, String password) {
        if (telepon.isEmpty()) {
            input_telepon.setError("Harus Diisi!");
            progressBar.setVisibility(View.GONE);
            login.setVisibility(View.VISIBLE);
            return false;
        }

        if (password.isEmpty()){
            input_password.setError("Harus Diisi!");
            progressBar.setVisibility(View.GONE);
            login.setVisibility(View.VISIBLE);
            return false;
        }
        return true;
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
}
