package com.unila.ilkomp.simipaforparents.retrofit;

import com.unila.ilkomp.simipaforparents.BuildConfig;
import com.unila.ilkomp.simipaforparents.model.AchievementResponce;
import com.unila.ilkomp.simipaforparents.model.AddMahasiswaModel;
import com.unila.ilkomp.simipaforparents.model.AddMahasiswaResponce;
import com.unila.ilkomp.simipaforparents.model.CountSeminarResponce;
import com.unila.ilkomp.simipaforparents.model.DeleteStudentModel;
import com.unila.ilkomp.simipaforparents.model.DeleteStudentResponce;
import com.unila.ilkomp.simipaforparents.model.KalenderAkademikResponce;
import com.unila.ilkomp.simipaforparents.model.LoginResponce;
import com.unila.ilkomp.simipaforparents.model.NotificationResponce;
import com.unila.ilkomp.simipaforparents.model.NotificationSender;
import com.unila.ilkomp.simipaforparents.model.PresenceBimbinganResponce;
import com.unila.ilkomp.simipaforparents.model.PresenceSeminarResponce;
import com.unila.ilkomp.simipaforparents.model.ProfileStudentResponce;
import com.unila.ilkomp.simipaforparents.model.ScheduleResponce;
import com.unila.ilkomp.simipaforparents.model.ScholarshipResponce;
import com.unila.ilkomp.simipaforparents.model.SplashscreenResponce;
import com.unila.ilkomp.simipaforparents.model.StudentsResponce;
import com.unila.ilkomp.simipaforparents.model.UpdateTokenResponce;
import com.unila.ilkomp.simipaforparents.model.UploadImageResponce;
import com.unila.ilkomp.simipaforparents.model.VerifyPhoneNumberRecord;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {

    @FormUrlEncoded
    @POST("login-parents.php")
    Call<LoginResponce> login(
            @Field("nohp") String nohp,
            @Field("pass") String pass,
            @Field("imei") String imei,
            @Field("ip") String ip
    );

    @FormUrlEncoded
    @POST("register-cek-nohp-terdaftar.php")
    Call<VerifyPhoneNumberRecord> verifyPhoneNumber(
            @Field("no_hp") String no_hp
    );

    @POST("parent-delete-student.php")
    Call<DeleteStudentResponce> deleteStudent(
            @Body DeleteStudentModel deleteStudentModel
            );

    @POST("parent-create-student.php")
    Call<AddMahasiswaResponce> createStudent(
            @Body AddMahasiswaModel addMahasiswaModel
    );

    @FormUrlEncoded
    @POST("update-token.php")
    Call<UpdateTokenResponce> updateToken(
            @Field("user") String user,
            @Field("imei") String imei,
            @Field("token") String token
    );

    @FormUrlEncoded
    @POST("register-parent.php")
    Call<ResponseBody> register(
            @Field("npm") String npm,
            @Field("id_parent") String idParent
    );

    @Multipart
    @POST("upload-foto.php")
    Call<UploadImageResponce> uploadGambar(
            @Part MultipartBody.Part body,
            @Part("file") RequestBody requestBody
            );

    @GET("parent-read-students.php")
    Call<StudentsResponce> listMahasiswa(
            @Query("no_hp") String no_hp
    );

    @GET("read-kalender-akademik.php")
    Call<KalenderAkademikResponce> kalenderAkademik(
            @Query("semester") String semester,
            @Query("tahun_akademik") String tahunAkademik
    );

    @GET("read-tanggal-penting.php")
    Call<KalenderAkademikResponce> importantDate(
            @Query("semester") String semester,
            @Query("tahun_akademik") String tahunAkademik
    );

    @GET("read-profile.php")
    Call<ProfileStudentResponce> profilStudent(
            @Query("npm") String npm
    );

    @GET("read-prestasi.php")
    Call<AchievementResponce> listAchievement(
            @Query("npm") String npm,
            @Query("kategori") String kategori
    );

    @GET("read-beasiswa.php")
    Call<ScholarshipResponce> listScholarship(
            @Query("npm") String npm
    );

    @GET("read-rekapitulasi-seminar-mhs.php")
    Call<PresenceSeminarResponce> listPresenceSeminar(
            @Query("npm") String npm
    );

    @GET("read-rekapitulasi-seminar-mhs.php")
    Call<CountSeminarResponce> countSeminar(
            @Query("npm") String npm
    );

    @GET("read-rekapitulasi-skripsi.php")
    Call<PresenceBimbinganResponce> rekapitulasiBimbinganSkripsi(
            @Query("npm") String npm
    );

    @GET("read-update-splashscreen.php")
    Call<SplashscreenResponce> splashscreen(
            @Query("flag") String flag
    );

    @GET("read-jadwal-mhs-parent.php")
    Call<ScheduleResponce> listSchedule(
            @Query("hari") String hari,
            @Query("npm") String npm,
            @Query("tahun_akademik") String tahun_akademik,
            @Query("semester") String semester
    );

    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=" + BuildConfig.BASE_URL_SERVER_FIREBASE_NOTIFICATION
            }
    )
    @POST("fcm/send")
    Call<NotificationResponce> sendNotifcation(@Body NotificationSender body);
}
