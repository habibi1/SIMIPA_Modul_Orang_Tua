package com.unila.ilkomp.simipaforparents;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;
import com.unila.ilkomp.simipaforparents.model.UploadImageResponce;
import com.unila.ilkomp.simipaforparents.retrofit.ApiService;
import com.unila.ilkomp.simipaforparents.retrofit.Client;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

import static com.unila.ilkomp.simipaforparents.BuildConfig.BASE_URL;
import static com.unila.ilkomp.simipaforparents.util.ControlUtil.elapseClick;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "PhoneAuthActivity";

    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";

    private static final int STATE_INITIALIZED = 1;
    private static final int STATE_CODE_SENT = 2;
    private static final int STATE_VERIFY_FAILED = 3;
    private static final int STATE_VERIFY_SUCCESS = 4;
    private static final int STATE_SIGNIN_FAILED = 5;
    private static final int STATE_SIGNIN_SUCCESS = 6;
    private static final int STATE_READ_EXTERNAL_STORAGE = 7;

    private long mLastClickTime = 0;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private Toolbar toolbar;
    TextView resendCode, waktu, textClose, timer;

    private Button register, verification;
    private ImageView ivPhoto;

    private EditText phoneNumber, codeVerification, parentsName, npmStudent, password;

    private Dialog verificationCode;
    private ProgressDialog pDialog;
    private ProgressBar progressBar_register, progressBar_verification, progressBar_resendCode;

    String extraPhoneNumber;

    Dialog select_method;
    CardView cv_camera, cv_gallery;
    String imageFilePath;
    static final int REQUEST_TAKE_PHOTO = 1;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;

    int time=60;
    boolean send_code = true;

    String text_message = "";
    boolean setToast = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        toolbar = findViewById(R.id.toolbar11);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        register = findViewById(R.id.btn_register);
        phoneNumber = findViewById(R.id.phone_number);
        password = findViewById(R.id.password);
        parentsName = findViewById(R.id.parents_name);
        npmStudent = findViewById(R.id.NPM_register);
        progressBar_register = findViewById(R.id.progressBar);
        ivPhoto = findViewById(R.id.imageButton);

        select_method = new Dialog(this);
        select_method.setContentView(R.layout.dialog_choose_method_add_photo);

        Bundle extras = getIntent().getExtras();
        phoneNumber.setText(extras.getString("PHONE_NUMBER"));

        phoneNumber.setOnClickListener(this);
        register.setOnClickListener(this);
        ivPhoto.setOnClickListener(this);
    }

    private void reqStoragePermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STATE_READ_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STATE_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {

        elapseClick();

        switch (v.getId()){
            case R.id.btn_register:
                if(checkField()) {
                    register.setVisibility(View.GONE);
                    progressBar_register.setVisibility(View.VISIBLE);
                    registerUser();
                }
                break;
            case R.id.imageButton:
                showPopUpChoosePhoto();
                break;
            case R.id.phone_number:
                //belum work
                text_message = "Nomor sudah terverifikasi.";
                setToast = false;

                phoneNumber.setFocusable(true);
                phoneNumber.setFocusableInTouchMode(true);
                phoneNumber.requestFocus();

                showToastOrSnackbar(text_message, setToast);
                break;
        }
    }

    private boolean checkField() {
        boolean status= true;

        if (phoneNumber.getText().toString().isEmpty()){
            phoneNumber.setError(getString(R.string.harus_diisi));
            status = false;
        }

        if (password.getText().toString().trim().isEmpty()){
            password.setError(getString(R.string.harus_diisi));
            status = false;
        }

        if (parentsName.getText().toString().trim().isEmpty()){
            parentsName.setError(getString(R.string.harus_diisi));
            status = false;
        }

        if (npmStudent.getText().toString().trim().isEmpty()){
            npmStudent.setError(getString(R.string.harus_diisi));
            status = false;
        }

        if (imageUri == null){
            ivPhoto.setFocusable(true);
            ivPhoto.setFocusableInTouchMode(true);
            ivPhoto.requestFocus();
            status = false;

            text_message = "Foto tidak boleh kosong!";
            setToast = false;

            showToastOrSnackbar(text_message, setToast);
        }

        return  status;
    }

    private void showToastOrSnackbar(String message, boolean statusToast){
        if (statusToast){
            Toast toast = Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            Snackbar.make(findViewById(android.R.id.content), message,
                    Snackbar.LENGTH_SHORT).show();
        }
    }

    public void showPopUpChoosePhoto() {

        cv_gallery = select_method.findViewById(R.id.cv_gallery);
        cv_camera = select_method.findViewById(R.id.cv_camera);

        cv_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                elapseClick();

                select_method.dismiss();
                openGallery();
            }
        });

        cv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                elapseClick();

                select_method.dismiss();
                openCameraIntent();
            }
        });

        select_method.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        select_method.getWindow().setGravity(Gravity.BOTTOM);
        select_method.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        select_method.getWindow().setWindowAnimations(R.style.DialogAnimation_up_down);
        select_method.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();

            ivPhoto.setImageURI(imageUri);
        }

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {

            File f = new File(imageFilePath);
            imageUri = Uri.fromFile(f);

            ivPhoto.setImageURI(imageUri);
        }
    }

    private void openGallery() {

        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);

    }

    private void openCameraIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        getApplicationContext().getPackageName(),
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",   /* suffix */
                storageDir      /* directory */
        );

        imageFilePath = image.getAbsolutePath();
        return image;
    }

    private void registerUser() {

        String filePath = getRealPathFromURIPath(imageUri,SignUpActivity.this);
        File file = new File(filePath);

        //displayLoader();
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put("no_hp", phoneNumber.getText().toString().trim());
            request.put("nama", parentsName.getText().toString().trim());
            request.put("password", password.getText().toString().trim());
            request.put("npm", npmStudent.getText().toString().trim());
            request.put("foto", file.getName());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, BASE_URL + "register-parent.php", request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //pDialog.dismiss();
                        try {
                            //Check if user got registered successfully
                            if (response.getString("message").equals("User was created.")) {
                                //Set the user session
                                uploadImage();
                            } else {
                                register.setVisibility(View.VISIBLE);
                                progressBar_register.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(),
                                        response.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //pDialog.dismiss();

                        //Display error message whenever an error occurs
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsArrayRequest);
    }

    private void uploadImage(){
        String filePath = getRealPathFromURIPath(imageUri,SignUpActivity.this);
        File file = new File(filePath);
        Log.d("File",""+file.getName());

        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"),file); //membungkus file ke dalam request body
        MultipartBody.Part body = MultipartBody.Part.createFormData("file",file.getName(),mFile); // membuat formdata multipart berisi request body
        RequestBody file_name = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        ApiService apiInterface = Client.getClient().create(ApiService.class);
        Call<UploadImageResponce> uploadGambar = apiInterface.uploadGambar(body, file_name);
        uploadGambar.enqueue(new Callback<UploadImageResponce>() {
            @Override
            public void onResponse(Call<UploadImageResponce> call, retrofit2.Response<UploadImageResponce> response) {

                if (response.isSuccessful()){
                    assert response.body() != null;
                    if (response.body().getSuccess()){
                        updateUI();
                    } else {

                        Snackbar.make(findViewById(android.R.id.content), "Upload foto gagal.",
                                Snackbar.LENGTH_SHORT).show();

                        updateUI();
                    }
                }
            }

            @Override
            public void onFailure(Call<UploadImageResponce> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, "error1" + t.toString(), Toast.LENGTH_LONG).show();
                Toast.makeText(SignUpActivity.this, "error2" + call.toString(), Toast.LENGTH_LONG).show();

                Snackbar.make(findViewById(android.R.id.content), "Upload foto gagal.",
                        Snackbar.LENGTH_SHORT).show();

                updateUI();

            }
        });
    }

    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    private void updateUI() {

        text_message = "Pendaftaran berhasil.";
        setToast = false;

        showToastOrSnackbar(text_message, setToast);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();

    }
}
