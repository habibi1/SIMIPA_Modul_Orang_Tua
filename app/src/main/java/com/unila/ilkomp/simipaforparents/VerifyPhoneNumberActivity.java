package com.unila.ilkomp.simipaforparents;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthActionCodeException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.unila.ilkomp.simipaforparents.model.VerifyPhoneNumberRecord;
import com.unila.ilkomp.simipaforparents.retrofit.ApiService;
import com.unila.ilkomp.simipaforparents.retrofit.Client;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;

import static com.unila.ilkomp.simipaforparents.util.ControlUtil.elapseClick;

public class VerifyPhoneNumberActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "PhoneAuthActivity";

    private static final int STATE_INITIALIZED = 1;
    private static final int STATE_CODE_SENT = 2;
    private static final int STATE_VERIFY_FAILED = 3;
    private static final int STATE_VERIFY_SUCCESS = 4;
    private static final int STATE_SIGNIN_FAILED = 5;
    private static final int STATE_SIGNIN_SUCCESS = 6;
    private static final int STATE_READ_EXTERNAL_STORAGE = 7;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private Toolbar toolbar;

    EditText codeCountri, phoneNumber, codeVerification;
    Button sendCodeOTP, verification;
    TextView tvTimer, resendCode, textClose, waktu;
    ProgressBar progressBar, progressBar_verification, progressBar_resendCode;
    private Dialog verificationCode;

    private int time=60;
    private boolean send_code = true;
    String text_message = "";
    boolean setToast = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone_number);

        toolbar = findViewById(R.id.toolbar11);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        codeCountri = findViewById(R.id.code_countri_phone_number);
        phoneNumber = findViewById(R.id.phone_number);
        sendCodeOTP = findViewById(R.id.btn_send_phone_number);
        tvTimer = findViewById(R.id.tv_timer);
        progressBar = findViewById(R.id.progressBar);

        tvTimer.setText("");
        tvTimer.setVisibility(View.GONE);

        verificationCode = new Dialog(this);
        verificationCode.setContentView(R.layout.dialog_confirm_phone_number);

        codeVerification = verificationCode.findViewById(R.id.code_verification);
        verification = verificationCode.findViewById(R.id.btn_verification);
        resendCode = verificationCode.findViewById(R.id.btn_resend_code);
        progressBar_verification = verificationCode.findViewById(R.id.progressBar_verification);
        progressBar_resendCode = verificationCode.findViewById(R.id.progressBar_resend_code);
        textClose = verificationCode.findViewById(R.id.txtclose);
        waktu = verificationCode.findViewById(R.id.waktu_verifikasi);
        waktu.setText("");

        sendCodeOTP.setOnClickListener(this);

        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        // Initialize phone auth callbacks
        // [START phone_auth_callbacks]
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:" + credential);
                // [START_EXCLUDE silent]
                mVerificationInProgress = false;

                text_message = "Verifikasi Sukses.";
                setToast = false;

                showToastOrSnackbar(text_message, setToast);

                verificationCode.dismiss();
                sendCodeOTP.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);

                // [END_EXCLUDE]

                // [START_EXCLUDE silent]
                // Update the UI and attempt sign in with the phone credential
                updateUI(STATE_VERIFY_SUCCESS, credential);
                // [END_EXCLUDE]
                signInWithPhoneAuthCredential(credential);

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);
                // [START_EXCLUDE silent]
                mVerificationInProgress = false;
                // [END_EXCLUDE]

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // [START_EXCLUDE]

                    codeCountri.setEnabled(true);
                    phoneNumber.setEnabled(true);

                    text_message = "Nomor tidak valid";
                    setToast = false;
                    // [END_EXCLUDE]
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // [START_EXCLUDE]
                    text_message = "Quota exceeded";
                    setToast = true;
                    // [END_EXCLUDE]
                } else if (e instanceof FirebaseAuthActionCodeException){
                    text_message = "Kode salah!";
                    setToast = true;
                }

                // Show a message and update the UI
                // [START_EXCLUDE]
                updateUI(STATE_VERIFY_FAILED);
                // [END_EXCLUDE]
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
                text_message = "Masa berlaku kode habis.";
                setToast = true;
                updateUI(STATE_VERIFY_FAILED);
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                text_message = "Kode terkirim.";
                setToast = true;

                // [START_EXCLUDE]
                // Update UI
                updateUI(STATE_CODE_SENT);
                // [END_EXCLUDE]
            }
        };
        // [END phone_auth_callbacks]
    }

    @Override
    public void onClick(View v) {

        elapseClick();

        switch (v.getId()){
            case R.id.btn_send_phone_number:

                if (checkField()){
                    if (send_code){
                        tvTimer.setVisibility(View.GONE);
                        sendCodeOTP.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        checkPhoneNumberStatus(codeCountri.getText().toString().trim() + phoneNumber.getText().toString().trim());
                    } else {
                        ShowPopUp();
                        Toast toast = Toast.makeText(VerifyPhoneNumberActivity.this, "Timer belum selesai", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
//                    updateUItoRegister();
                }
                break;
        }

    }

    private void checkPhoneNumberStatus(final String phoneNumberConcate) {

        codeCountri.setEnabled(false);
        phoneNumber.setEnabled(false);

        ApiService apiService = Client.getClient().create(ApiService.class);

        Call<VerifyPhoneNumberRecord> verifyPhoneNumber = apiService.verifyPhoneNumber(phoneNumberConcate);
        verifyPhoneNumber.enqueue(new Callback<VerifyPhoneNumberRecord>() {
            @Override
            public void onResponse(Call<VerifyPhoneNumberRecord> call, retrofit2.Response<VerifyPhoneNumberRecord> response) {

                if (response.code() != 500){
                    if (response.code() == 200){

                        startPhoneNumberVerification(phoneNumberConcate);

                    } else if (response.code() == 400){

                        codeCountri.setEnabled(true);
                        phoneNumber.setEnabled(true);

                        progressBar.setVisibility(View.GONE);
                        sendCodeOTP.setVisibility(View.VISIBLE);

                        text_message = "Nomor sudah terdaftar.";
                        setToast = false;

                        showToastOrSnackbar(text_message, setToast);
                    }
                } else {

                    codeCountri.setEnabled(true);
                    phoneNumber.setEnabled(true);

                    progressBar.setVisibility(View.GONE);
                    sendCodeOTP.setVisibility(View.VISIBLE);
                    Snackbar.make(findViewById(android.R.id.content), "Server Error",
                            Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VerifyPhoneNumberRecord> call, Throwable t) {

                codeCountri.setEnabled(true);
                phoneNumber.setEnabled(true);

                progressBar.setVisibility(View.GONE);
                sendCodeOTP.setVisibility(View.VISIBLE);

                Snackbar.make(findViewById(android.R.id.content), "Tidak bisa menghubungi server.",
                        Snackbar.LENGTH_SHORT).show();

            }
        });

    }

    private boolean checkField() {

        boolean status = true;

        if (codeCountri.getText().toString().trim().isEmpty()){
            codeCountri.setError(getString(R.string.harus_diisi));
            status = false;
        }

        if (phoneNumber.getText().toString().trim().isEmpty()){
            phoneNumber.setError(getString(R.string.harus_diisi));
            status = false;
        }

        return status;
    }

    private void showToastOrSnackbar(String message, boolean statusToast){
        if (statusToast){
            Toast toast = Toast.makeText(VerifyPhoneNumberActivity.this, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            Snackbar.make(findViewById(android.R.id.content), message,
                    Snackbar.LENGTH_SHORT).show();
        }
    }

    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        // [END start_phone_auth]

        mVerificationInProgress = true;

    }

    public void timer(){
        send_code = false;
        time = 60;
        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                tvTimer.setText("Timer: " + checkDigit(time));
                waktu.setText("Timer: " + checkDigit(time));
                time--;
            }

            public void onFinish() {

                codeCountri.setEnabled(true);
                phoneNumber.setEnabled(true);

                tvTimer.setText("");
                tvTimer.setVisibility(View.GONE);
                waktu.setText("");
                send_code = true;
            }
        }.start();
    }

    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential);
    }

    // [START resend_verification]
    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
        mVerificationInProgress = true;
    }
    // [END resend_verification]

    // [START sign_in_with_phone]
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            // [START_EXCLUDE]
                            updateUI(STATE_SIGNIN_SUCCESS, user);
                            // [END_EXCLUDE]

                            verificationCode.dismiss();
                            sendCodeOTP.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);

                            updateUItoRegister();

                            user.delete();
                            mVerificationId = "";
                            signOut();

                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                // [START_EXCLUDE silent]
                                text_message = "Kode salah!";
                                setToast = true;
                                // [END_EXCLUDE]

                                showToastOrSnackbar(text_message, setToast);

                            }
                            // [START_EXCLUDE silent]
                            // Update UI
                            updateUI(STATE_SIGNIN_FAILED);
                            // [END_EXCLUDE]
                        }
                    }
                });
    }
    // [END sign_in_with_phone]

    private void signOut() {
        mAuth.signOut();
        updateUI(STATE_INITIALIZED);
    }

    private void updateUI(int uiState) {
        updateUI(uiState, mAuth.getCurrentUser(), null);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            updateUI(STATE_SIGNIN_SUCCESS, user);
        } else {
            updateUI(STATE_INITIALIZED);
        }
    }

    private void updateUI(int uiState, FirebaseUser user) {
        updateUI(uiState, user, null);
    }

    private void updateUI(int uiState, PhoneAuthCredential cred) {
        updateUI(uiState, null, cred);
    }

    private void updateUI(int uiState, FirebaseUser user, PhoneAuthCredential cred) {
        switch (uiState) {
            case STATE_INITIALIZED:
                // Initialized state, show only the phone number field and start button

                break;
            case STATE_CODE_SENT:
                // Code sent state, show the verification field, the

                showToastOrSnackbar(text_message, setToast);

                sendCodeOTP.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                ShowPopUp();
                timer();
                break;

            case STATE_VERIFY_FAILED:
                // Verification has failed, show all options

                showToastOrSnackbar(text_message, setToast);

                codeVerification.setText("");
                verification.setVisibility(View.VISIBLE);
                resendCode.setVisibility(View.VISIBLE);
                progressBar_verification.setVisibility(View.GONE);
                progressBar_resendCode.setVisibility(View.GONE);

                sendCodeOTP.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                break;
            case STATE_VERIFY_SUCCESS:
                // Verification has succeeded, proceed to firebase sign in

                // Set the verification text based on the credential
                if (cred != null) {
                    if (cred.getSmsCode() != null) {
                        codeVerification.setText(cred.getSmsCode());
                    } else {
                        codeVerification.setText("");
                    }
                }

                break;

            case STATE_SIGNIN_FAILED:
                codeVerification.setText("");
                verification.setVisibility(View.VISIBLE);
                resendCode.setVisibility(View.VISIBLE);
                progressBar_verification.setVisibility(View.GONE);
                progressBar_resendCode.setVisibility(View.GONE);
                break;
        }

        if (user == null) {
            // Signed out

        } else {
            // Signed in

        }
    }

    public void ShowPopUp(){

        codeVerification.setText("");
        verification.setVisibility(View.VISIBLE);
        resendCode.setVisibility(View.VISIBLE);
        progressBar_verification.setVisibility(View.GONE);
        progressBar_resendCode.setVisibility(View.GONE);

        verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                elapseClick();

                String code = codeVerification.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    codeVerification.setError(getString(R.string.harus_diisi));
                    return;
                }

                verification.setVisibility(View.GONE);
                progressBar_verification.setVisibility(View.VISIBLE);

                verifyPhoneNumberWithCode(mVerificationId, code);
            }
        });

        resendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                elapseClick();

                if (send_code) {
                    resendCode.setVisibility(View.GONE);
                    progressBar_resendCode.setVisibility(View.VISIBLE);
                    resendVerificationCode(codeCountri.getText().toString().trim() + phoneNumber.getText().toString().trim(), mResendToken);
                } else {
                    Toast toast = Toast.makeText(VerifyPhoneNumberActivity.this, "Timer belum selesai", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });

        textClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                elapseClick();

                verificationCode.dismiss();
                tvTimer.setVisibility(View.VISIBLE);
            }
        });

        verificationCode.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        verificationCode.getWindow().setGravity(Gravity.BOTTOM);
        verificationCode.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        verificationCode.setCanceledOnTouchOutside(false);
        verificationCode.getWindow().setWindowAnimations(R.style.DialogAnimation_up_down);
        verificationCode.show();
    }

    private void updateUItoRegister() {
        Intent intent = new Intent(this, SignUpActivity.class);
        intent.putExtra("PHONE_NUMBER", codeCountri.getText().toString().trim() + phoneNumber.getText().toString().trim());
        startActivity(intent);
        finish();
    }
}
