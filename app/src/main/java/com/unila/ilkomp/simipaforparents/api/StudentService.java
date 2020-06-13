package com.unila.ilkomp.simipaforparents.api;

import com.unila.ilkomp.simipaforparents.model.AchievementResponce;
import com.unila.ilkomp.simipaforparents.model.StudentWrapper;

import retrofit2.Call;
import retrofit2.http.GET;

public interface StudentService {
    @GET("read-profile.php")
    Call<StudentWrapper> getData();

    @GET("read-prestasi.php")
    Call<AchievementResponce> getAchievement();

}
