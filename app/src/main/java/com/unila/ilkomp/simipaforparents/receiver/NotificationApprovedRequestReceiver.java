package com.unila.ilkomp.simipaforparents.receiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.unila.ilkomp.simipaforparents.HomeActivity;
import com.unila.ilkomp.simipaforparents.SharedPrefManager;

public class NotificationApprovedRequestReceiver extends BroadcastReceiver {

    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        String user = intent.getStringExtra("user");
        String name = intent.getStringExtra("name");
        String department = intent.getStringExtra("department");

        this.context = context;

        showStudent(user, name, department);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        assert user != null;
        manager.cancel(Integer.parseInt(user));
    }

    private void showStudent(String user, String name, String department) {

        SharedPrefManager.setNPMChoosed(context, user);
        SharedPrefManager.setNameStudentChoosed(context, name);
        SharedPrefManager.setDepartmentStudentChoosed(context, department);

        Intent intent = new Intent(context, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 , intent,
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
