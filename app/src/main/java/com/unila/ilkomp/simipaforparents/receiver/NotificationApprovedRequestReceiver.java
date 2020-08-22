package com.unila.ilkomp.simipaforparents.receiver;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.unila.ilkomp.simipaforparents.HomeActivity;
import com.unila.ilkomp.simipaforparents.SharedPrefManager;

public class NotificationApprovedRequestReceiver extends BroadcastReceiver {

    Context context;

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {
        String id = intent.getStringExtra("id");
        String user = intent.getStringExtra("user");
        String name = intent.getStringExtra("name");
        String department = intent.getStringExtra("department");
        String path = intent.getStringExtra("path");

        this.context = context;

        showStudent(user, name, department, path);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        assert id != null;
        manager.cancel(Integer.parseInt(id));
    }

    private void showStudent(String user, String name, String department, String path) {

        SharedPrefManager.setNPMChoosed(context, user);
        SharedPrefManager.setNameStudentChoosed(context, name);
        SharedPrefManager.setDepartmentStudentChoosed(context, department);
        SharedPrefManager.setImageStudentChoosed(context, path);

        Intent intent = new Intent(context, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);
        try {
            pendingIntent.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }

        Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        context.sendBroadcast(it);

    }
}
