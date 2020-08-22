package com.unila.ilkomp.simipaforparents.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.unila.ilkomp.simipaforparents.ListStudentsActivity;
import com.unila.ilkomp.simipaforparents.R;
import com.unila.ilkomp.simipaforparents.SharedPrefManager;
import com.unila.ilkomp.simipaforparents.model.UpdateTokenResponce;
import com.unila.ilkomp.simipaforparents.receiver.NotificationApprovedRequestReceiver;
import com.unila.ilkomp.simipaforparents.retrofit.ApiService;
import com.unila.ilkomp.simipaforparents.retrofit.Client;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);


        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "FromParent: " + "notif");

        String title = remoteMessage.getData().get("title");
        String message = remoteMessage.getData().get("message");
        String channelId = remoteMessage.getData().get("channel_id");
        String group_id = remoteMessage.getData().get("group_id");
        String id = remoteMessage.getData().get("id");
        String user = remoteMessage.getData().get("user");
        String name = remoteMessage.getData().get("name");
        String department = remoteMessage.getData().get("department");
        String path = remoteMessage.getData().get("path");

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        Log.d(TAG, "Link path: " + path);
        Bitmap photoProfil = getCircleBitmap(getBitmapFromURL(path));

        Intent intent = new Intent(this, ListStudentsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , intent,
                PendingIntent.FLAG_ONE_SHOT);

        Intent broadcastIntentApproved = new Intent(this, NotificationApprovedRequestReceiver.class);
        broadcastIntentApproved.putExtra("id", id);
        broadcastIntentApproved.putExtra("user", user);
        broadcastIntentApproved.putExtra("name", name);
        broadcastIntentApproved.putExtra("department", department);
        broadcastIntentApproved.putExtra("path", path);
        broadcastIntentApproved.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent actionIntentApproved = PendingIntent.getBroadcast(this,
                0, broadcastIntentApproved, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder;

        assert channelId != null;
        if (channelId.equals(getResources().getString(R.string.parent_notification_channel_name_4))){
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            notificationBuilder = new NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(R.drawable.ic_notification_unila)
                    .setLargeIcon(photoProfil)
                    .setContentTitle(title)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(message))
                    .setAutoCancel(true)
                    .setGroup(group_id)
                    .addAction(R.drawable.ic_person_blue_40dp, getResources().getString(R.string.lihat), actionIntentApproved)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);
        } else {
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            notificationBuilder = new NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(R.drawable.ic_notification_unila)
                    .setLargeIcon(photoProfil)
                    .setContentTitle(title)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(message))
                    .setAutoCancel(true)
                    .setGroup(group_id)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);
        }

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    channelId,
                    NotificationManager.IMPORTANCE_DEFAULT);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }

        assert notificationManager != null;
        assert id != null;
        notificationManager.notify(Integer.parseInt(id), notificationBuilder.build());

    }

    private Bitmap getCircleBitmap(Bitmap bitmapFromURL) {
        Bitmap bitmap;
        Rect srcRect, dstRect;
        float r;
        final int width = bitmapFromURL.getWidth();
        final int height = bitmapFromURL.getHeight();

        if (width > height){
            bitmap = Bitmap.createBitmap(height, height, Bitmap.Config.ARGB_8888);
            int left = (width - height) / 2;
            int right = left + height;
            srcRect = new Rect(left, 0, right, height);
            dstRect = new Rect(0, 0, height, height);
            r = height / 2;
        } else {
            bitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
            int top = (height - width) / 2;
            int bottom = top + width;
            srcRect = new Rect(0, top, width, bottom);
            dstRect = new Rect(0, 0, width, width);
            r = width / 2;
        }

        Canvas canvas = new Canvas(bitmap);

        final int color = 0xff424242;
        final Paint paint = new Paint();

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(r, r, r, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmapFromURL, srcRect, dstRect, paint);

        bitmapFromURL.recycle();

        return bitmap;
    }

    private Bitmap getBitmapFromURL(String path) {
        try {

            Log.d(TAG, "Status: good");
            URL url = new URL(path);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();

            InputStream inputStream = connection.getInputStream();

            return BitmapFactory.decodeStream(inputStream);

        } catch (IOException e){
            e.printStackTrace();

            Log.d(TAG, "Status: null");

            return null;
        }
    }
    // [END receive_message]


    // [START on_new_token]

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token);
    }
    // [END on_new_token]


    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.

        ApiService apiService = Client.getClient().create(ApiService.class);

        String imei = getUniqueIMEIId(getApplicationContext());

        Call<UpdateTokenResponce> updateToken = apiService.updateToken(SharedPrefManager.getPhoneNumberLoggedInUser(getApplicationContext()), imei, token);
        updateToken.enqueue(new Callback<UpdateTokenResponce>() {
            @Override
            public void onResponse(Call<UpdateTokenResponce> call, retrofit2.Response<UpdateTokenResponce> response) {

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getResponseCode() == 200) {

                        if (response.body().getTotalRecords() > 0) {
                            Log.d(TAG, "Success Refreshed token to Server: " + token);
                        } else {
                            Log.d(TAG, "Failed Refreshed token to Server: " + token);
                        }

                    } else if (response.body().getResponseCode() == 400){
                        Log.d(TAG, "Something Wrong!!!");
                    }
                } else {
                    Log.d(TAG, "Something Wrong!!!");
                }
            }

            @Override
            public void onFailure(Call<UpdateTokenResponce> call, Throwable t) {
                Log.d(TAG, "Server Error!!!");
            }
        });

    }

    public String getUniqueIMEIId(Context context) {

        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei;

        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
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
}
