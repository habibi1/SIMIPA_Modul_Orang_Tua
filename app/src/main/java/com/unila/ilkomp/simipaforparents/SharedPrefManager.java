package com.unila.ilkomp.simipaforparents;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPrefManager {
    /** Pendeklarasian key-data berupa String, untuk sebagai wadah penyimpanan data.
     * Jadi setiap data mempunyai key yang berbeda satu sama lain */
    static final String KEY_NO_TELEPON ="user";
    static final String KEY_USERNAME_SEDANG_LOGIN = "Username_logged_in";
    static final String KEY_STATUS_SEDANG_LOGIN = "Status_logged_in";
    static final String KEY_NPM = "npm_choosed";
    static final String KEY_NAME_STUDENT_CHOOSED = "name_student_choosed";
    static final String KEY_DEPARTMENT_STUDENT_CHOOSED = "department_student_choosed";
    static final String KEY_IMAGE_STUDENT_CHOOSED = "image_student_choosed";
    static final String KEY_IMAGE_PARENT = "image_parent";

    /** Pendlakarasian Shared Preferences yang berdasarkan paramater context */
    private static SharedPreferences getSharedPreference(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /** Deklarasi Edit Preferences dan mengubah data
     *  yang memiliki key isi KEY_USER_TEREGISTER dengan parameter username */
    public static void setPhoneNumberLoggedInUser(Context context, String username){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_NO_TELEPON, username);
        editor.apply();
    }

    /** Mengembalikan nilai dari key KEY_USER_TEREGISTER berupa String */
    public static String getPhoneNumberLoggedInUser(Context context){
        return getSharedPreference(context).getString(KEY_NO_TELEPON,"");
    }

    public static void setImageParent(Context context, String image){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_IMAGE_PARENT, BuildConfig.BASE_IMAGE + image);
        editor.apply();
    }

    public static String getImageParent(Context context){
        return getSharedPreference(context).getString(KEY_IMAGE_PARENT,"");
    }

    /** Deklarasi Edit Preferences dan mengubah data
     *  yang memiliki key isi KEY_USER_TEREGISTER dengan parameter username */
    public static void setNPMChoosed(Context context, String npm){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_NPM, npm);
        editor.apply();
    }

    /** Mengembalikan nilai dari key KEY_USER_TEREGISTER berupa String */
    public static String getNPMChoosed(Context context){
        return getSharedPreference(context).getString(KEY_NPM,"");
    }

    public static String getNameStudentChoosed(Context context){
        return getSharedPreference(context).getString(KEY_NAME_STUDENT_CHOOSED,"");
    }

    public static void setNameStudentChoosed(Context context, String name){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_NAME_STUDENT_CHOOSED, name);
        editor.apply();
    }

    public static String getDepartmentStudentChoosed(Context context){
        return getSharedPreference(context).getString(KEY_DEPARTMENT_STUDENT_CHOOSED,"");
    }

    public static void setDepartmentStudentChoosed(Context context, String department){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_DEPARTMENT_STUDENT_CHOOSED, department);
        editor.apply();
    }

    public static void setImageStudentChoosed(Context context, String image){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_IMAGE_STUDENT_CHOOSED, image);
        editor.apply();
    }

    public static String getImageStudentChoosed(Context context){
        return getSharedPreference(context).getString(KEY_IMAGE_STUDENT_CHOOSED,"");
    }

    /** Deklarasi Edit Preferences dan mengubah data
     *  yang memiliki key KEY_USERNAME_SEDANG_LOGIN dengan parameter username */
    public static void setNameLoggedInUser(Context context, String username){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_USERNAME_SEDANG_LOGIN, username);
        editor.apply();
    }
    /** Mengembalikan nilai dari key KEY_USERNAME_SEDANG_LOGIN berupa String */
    public static String getNameLoggedInUser(Context context){
        return getSharedPreference(context).getString(KEY_USERNAME_SEDANG_LOGIN,"");
    }

    /** Deklarasi Edit Preferences dan mengubah data
     *  yang memiliki key KEY_STATUS_SEDANG_LOGIN dengan parameter status */
    public static void setLoggedInStatus(Context context, boolean status){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putBoolean(KEY_STATUS_SEDANG_LOGIN,status);
        editor.apply();
    }

    /** Mengembalikan nilai dari key KEY_STATUS_SEDANG_LOGIN berupa boolean */
    public static boolean getLoggedInStatus(Context context){
        return getSharedPreference(context).getBoolean(KEY_STATUS_SEDANG_LOGIN,false);
    }

    /** Deklarasi Edit Preferences dan menghapus data, sehingga menjadikannya bernilai default
     *  khusus data yang memiliki key KEY_USERNAME_SEDANG_LOGIN dan KEY_STATUS_SEDANG_LOGIN */
    public static void clearLoggedInUser (Context context){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.remove(KEY_USERNAME_SEDANG_LOGIN);
        editor.remove(KEY_STATUS_SEDANG_LOGIN);
        editor.apply();
    }
}
