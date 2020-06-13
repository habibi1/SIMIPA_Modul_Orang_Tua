package com.unila.ilkomp.simipaforparents;

import android.app.Activity;
import android.app.Dialog;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;

import com.google.android.material.snackbar.Snackbar;
import com.unila.ilkomp.simipaforparents.model.UploadImageResponce;
import com.unila.ilkomp.simipaforparents.retrofit.ApiService;
import com.unila.ilkomp.simipaforparents.retrofit.Client;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

import static com.unila.ilkomp.simipaforparents.util.ControlUtil.elapseClick;

public class AddPhotoProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int STATE_INITIALIZED = 1;
    private static final int STATE_CODE_SENT = 2;
    private static final int STATE_VERIFY_FAILED = 3;
    private static final int STATE_VERIFY_SUCCESS = 4;
    private static final int STATE_SIGNIN_FAILED = 5;
    private static final int STATE_SIGNIN_SUCCESS = 6;
    private static final int STATE_READ_EXTERNAL_STORAGE = 7;

    Dialog select_method;
    CardView cv_camera, cv_gallery;
    String imageFilePath;
    static final int REQUEST_TAKE_PHOTO = 1;
    private static final int PICK_IMAGE = 100;

    private Button btnSave;
    private ImageView ivPhoto;
    private ProgressBar progressBar;

    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo_profile);

        select_method = new Dialog(this);
        select_method.setContentView(R.layout.dialog_choose_method_add_photo);

        btnSave = findViewById(R.id.btn_upload_foto);
        ivPhoto = findViewById(R.id.imageButton);
        progressBar = findViewById(R.id.progressBar);

        ivPhoto.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageButton:
                showPopUpChoosePhoto();
                break;
            case R.id.btn_upload_foto:
                if (checkPhoto()){
                    btnSave.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    uploadImage();
                }
                break;
        }
    }

    private boolean checkPhoto() {
        boolean status = true;

        if (imageUri == null){
            ivPhoto.setFocusable(true);
            ivPhoto.setFocusableInTouchMode(true);
            ivPhoto.requestFocus();
            status = false;

            Snackbar.make(findViewById(android.R.id.content), "Foto tidak boleh kosong.",
                    Snackbar.LENGTH_SHORT).show();
        }

        return status;
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
        select_method.show();
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

    private void uploadImage(){
        String filePath = getRealPathFromURIPath(imageUri,AddPhotoProfileActivity.this);
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
                        btnSave.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        Snackbar.make(findViewById(android.R.id.content), "Upload foto gagal.",
                                Snackbar.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UploadImageResponce> call, Throwable t) {
                Toast.makeText(AddPhotoProfileActivity.this, "error1" + t.toString(), Toast.LENGTH_LONG).show();
                Toast.makeText(AddPhotoProfileActivity.this, "error2" + call.toString(), Toast.LENGTH_LONG).show();
                btnSave.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
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
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
