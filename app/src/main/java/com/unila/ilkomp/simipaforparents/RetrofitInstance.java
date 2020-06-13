package com.unila.ilkomp.simipaforparents;

import com.unila.ilkomp.simipaforparents.api.StudentService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static Retrofit retrofit = null;

    public static StudentService getStudentService (){
        if (retrofit == null){
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(StudentService.class);
    }

}
