package com.unila.ilkomp.simipaforparents;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
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
import com.unila.ilkomp.simipaforparents.retrofit.ApiService;
import com.unila.ilkomp.simipaforparents.retrofit.Client;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

import static com.unila.ilkomp.simipaforparents.util.ControlUtil.elapseClick;
import static com.unila.ilkomp.simipaforparents.util.DeviceIDUtil.getUniqueIMEIId;

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
                    makeToken(teks_telepon, teks_password);
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
        assert wm != null;
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

    private void loginUser(final String teks_telepon, final String teks_password, final String token) {

        ApiService apiService = Client.getClient().create(ApiService.class);

        imei = getUniqueIMEIId(getApplicationContext());
        ip = getIP();

        Call<LoginResponce> loginProcess = apiService.login(teks_telepon, teks_password, imei, ip, token);
        loginProcess.enqueue(new Callback<LoginResponce>() {
            @Override
            public void onResponse(Call<LoginResponce> call, retrofit2.Response<LoginResponce> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getResponseCode() == 200) {
                        List<LoginRecord> loginRecord = response.body().getRecords();

                        Log.d("token", token);
                        Log.d("imei", imei);
                        Log.d("ip", ip);

                        SharedPrefManager.setPhoneNumberLoggedInUser(getBaseContext(), teks_telepon);
                        SharedPrefManager.setNameLoggedInUser(getBaseContext(), loginRecord.get(0).getDisplayName());
                        SharedPrefManager.setImageParent(getBaseContext(), loginRecord.get(0).getFoto());
                        SharedPrefManager.setJWT(getBaseContext(), loginRecord.get(0).getJWT());
                        SharedPrefManager.setLoggedInStatus(getBaseContext(), true);

                        Intent intent = new Intent(LoginActivity.this, ListStudentsActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);

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

    public void makeToken(final String username, final String password) {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("Token", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = Objects.requireNonNull(task.getResult()).getToken();

                        loginUser(username, password, token);

                        Log.d("Token", token);
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
