package com.mobdeve.mco;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.mobdeve.mco.Activities.MainActivity;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent notifIntent = new Intent(context, MainActivity.class);
        int NOTIF_ID = intent.getIntExtra("notifId", 0);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent
                .getActivity(context, NOTIF_ID, notifIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Channel ID same as ID in CreateNotifChannel
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "DoWhile")
                .setSmallIcon(R.drawable.dowhile_foreground)
                .setContentTitle(intent.getStringExtra("TaskName"))
                .setContentText(intent.getStringExtra("description"))
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notifManagerCompat = NotificationManagerCompat.from(context);
        notifManagerCompat.notify(NOTIF_ID, builder.build());
    }
}
