package com.mobdeve.mco;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.mobdeve.mco.Keys.Types;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;


public class AlarmHelper {

    private DatabaseHelper db;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private Context context;

    //object values
    private String name, desc, time;
    private boolean[] days;

    public AlarmHelper(Context context){
        this.context = context;
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        db = new DatabaseHelper(context);
    }

    public void setAlarm(String type, int id){
        int[] alarmCodes = GenerateAlarmCodes(type, id);

        getTaskValues(type, id);

        if(type.equals(Types.Todo.name())){
            schedule(alarmCodes[0]);
        }else if(type.equals(Types.Daily.name())){
            scheduleDaysOfWeek(alarmCodes);
        }
    }

    private void getTaskValues(String type, int id){
        name = db.getStringField(type, "name", id);
        desc = db.getStringField(type, "description", id);
        time = db.getStringField(type, "next_notif", id);

        if(type.equals(Types.Daily.name()))
            days = Task.Daily.getDays(db.getStringField(type, "selected_days", id));
    }

    private void schedule(int reqCode){
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        LocalDateTime alarmTime = Task.getNotif(time);

        long milis = alarmTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        Intent i = new Intent(context, AlarmReceiver.class);
        i.putExtra("notifId", reqCode);
        i.putExtra("TaskName", name);

        if(!(desc == null))
            i.putExtra("description", desc);
        else i.putExtra("description", "");

        pendingIntent = PendingIntent.getBroadcast(context, reqCode, i, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.set(AlarmManager.RTC, milis, pendingIntent);

        Toast.makeText(context, "Alarm Scheduled", Toast.LENGTH_SHORT).show();
    }

    private void scheduleDaysOfWeek(int[] codes){
        int hour = Task.getNotif(time).getHour();
        int min = Task.getNotif(time).getMinute();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime alarmTime = LocalDate.now().atTime(hour, min, 0);

        //skip code of index 0 | i = int DayOfWeek
        for(int i = 1; i < codes.length; i++){
            if(days[i-1]){
                //check if alarm is scheduled for today and created before scheduling time
                if(now.getDayOfWeek().getValue() == i && now.isBefore(alarmTime)){
                    //schedule weekly alarm as is, intended to notify within the day
                    scheduleWeekly(codes[i], alarmTime);
                } else {
                    //otherwise schedule for the next instance of the day of the week
                    LocalDateTime nextTime = alarmTime.with(TemporalAdjusters.next(DayOfWeek.of(i)));
                    scheduleWeekly(codes[i], nextTime);
                }
            }
        }
        Toast.makeText(context, "Alarms Scheduled", Toast.LENGTH_SHORT).show();
    }

    private void scheduleWeekly(int reqCode, LocalDateTime alarmTime){
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        long milis = alarmTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        Intent i = new Intent(context, AlarmReceiver.class);
        i.putExtra("notifId", reqCode);
        i.putExtra("TaskName", name);
        if(!(desc == null))
            i.putExtra("description", desc);
        else i.putExtra("description", "");

        pendingIntent = PendingIntent.getBroadcast(context, reqCode, i, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setInexactRepeating(AlarmManager.RTC, milis, AlarmManager.INTERVAL_DAY * 7 , pendingIntent);
    }

    public void cancelAllAlarms(String type, int id){
        int[] codes = GenerateAlarmCodes(type, id);

        for(int code: codes)
            cancelAlarm(code);

        //Toast.makeText(context, "Alarm Cancelled", Toast.LENGTH_SHORT).show();
    }

    public void cancelAlarm(int reqCode){
        Intent i = new Intent(context, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(context, reqCode, i, PendingIntent.FLAG_UPDATE_CURRENT);

        if(alarmManager == null){
            alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        }

        alarmManager.cancel(pendingIntent);
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

        //Toast.makeText(context, Arrays.toString(codes), Toast.LENGTH_SHORT).show();
        return codes;
    }
}
