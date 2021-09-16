package com.mobdeve.mco;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.mobdeve.mco.Keys.Types;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;


public class AlarmHelper {

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private Context context;

    public AlarmHelper(Context context){
        this.context = context;
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public void setAlarm(String type, LocalDateTime time, int id, String name){
        int[] alarmCodes = GenerateAlarmCodes(type, id);

        if(type.equals(Types.Todo.name())){
            schedule(alarmCodes[0], time, name);
        }else if(type.equals(Types.Daily.name())){

        }
    }

    private void schedule(int reqCode, LocalDateTime time, String name){
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        long milis = time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        Intent i = new Intent(context, AlarmReceiver.class);
        i.putExtra("notifId", reqCode);
        i.putExtra("TaskName", name);

        pendingIntent = PendingIntent.getBroadcast(context, reqCode, i, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.set(AlarmManager.RTC, milis, pendingIntent);

        Toast.makeText(context, "Alarm Scheduled", Toast.LENGTH_SHORT).show();
    }

    private void scheduleWeekly(int reqCode, int hour, int min, String name){
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        LocalDateTime time = LocalDate.now().atTime(hour,min);
        long milis = time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        Intent i = new Intent(context, AlarmReceiver.class);
        i.putExtra("notifId", reqCode);
        i.putExtra("TaskName", name);

        pendingIntent = PendingIntent.getBroadcast(context, reqCode, i, PendingIntent.FLAG_UPDATE_CURRENT);

        //TODO: interval milis change to weekly
        alarmManager.setInexactRepeating(AlarmManager.RTC, milis, 2*60*1000, pendingIntent);

        Toast.makeText(context, "Alarm Scheduled", Toast.LENGTH_SHORT).show();
    }

    public void cancelAllAlarms(String type, int id){
        int[] codes = GenerateAlarmCodes(type, id);

        for(int code: codes)
            cancelAlarm(code);
    }

    public void cancelAlarm(int reqCode){
        Intent i = new Intent(context, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(context, reqCode, i, PendingIntent.FLAG_UPDATE_CURRENT);

        if(alarmManager == null){
            alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        }

        alarmManager.cancel(pendingIntent);

        Toast.makeText(context, "Alarm Cancelled", Toast.LENGTH_SHORT).show();
    }

    public static int[] GenerateAlarmCodes(String type, int id){
        int[] codes = new int[8];
        int typeKey = 0;

        switch(type){
            case "Todo": typeKey = 1;
                break;
            case "Daily": typeKey = 2;
                break;
            case "Goal": typeKey = 3;
                break;
        }

        for(int i = 0; i < 8; i++){
            String s = typeKey + "" + i + "" + id;
            codes[i] = Integer.parseInt(s);
        }

        return codes;
    }
}
