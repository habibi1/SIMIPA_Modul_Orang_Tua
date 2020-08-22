package com.unila.ilkomp.simipaforparents.util;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.unila.ilkomp.simipaforparents.R;
import com.unila.ilkomp.simipaforparents.model.NotificationModel;
import com.unila.ilkomp.simipaforparents.model.NotificationResponce;
import com.unila.ilkomp.simipaforparents.model.NotificationSender;
import com.unila.ilkomp.simipaforparents.retrofit.ApiService;
import com.unila.ilkomp.simipaforparents.retrofit.Client;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationControl {

    public static void sendNotifications(final Context context, String usertoken, String title, String message, String channel_id, String group_id, String id, String user, String photo_path) {

        //dismiss notification
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        assert id != null;
        manager.cancel(Integer.parseInt(id));

        //send notification
        NotificationModel data = new NotificationModel(title, message, channel_id, group_id, id, user, photo_path);
        NotificationSender sender = new NotificationSender(data, usertoken);

        Log.d("Token", "upload");

        ApiService apiService = Client.getClientNotification().create(ApiService.class);

        Call<NotificationResponce> sendNotifcation = apiService.sendNotifcation(sender);
        sendNotifcation.enqueue(new Callback<NotificationResponce>() {

            @SuppressLint("ShowToast")
            @Override
            public void onResponse(@NonNull Call<NotificationResponce> call, @NonNull Response<NotificationResponce> response) {
                Log.d("Token", response.code() + "");
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().success != 1) {
                        Toast.makeText(context, context.getString(R.string.gagal_mengirim_notifikasi), Toast.LENGTH_LONG);
                    } else {
                        Log.d("Token", "Notification Success :)");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<NotificationResponce> call, @NonNull Throwable t) {
                Log.d("Token", "failed");
                Toast.makeText(context, context.getString(R.string.gagal_mengirim_notifikasi), Toast.LENGTH_LONG);
            }
        });
    }

}
