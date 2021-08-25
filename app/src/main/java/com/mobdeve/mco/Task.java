package com.mobdeve.mco;

import android.graphics.Color;

import java.time.LocalDateTime;
import java.util.Date;

public class Task {
    String name, desc;
    Boolean completed;
    int color;

    //TODO: implement notification
    LocalDateTime nextnotif;

    public Task(String name, String desc, LocalDateTime notif, Boolean completed, String color){
        this.name = name;
        this.desc = desc;
        this.nextnotif = notif;
        this.completed = completed;
        this.color = Color.parseColor(color);
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public LocalDateTime getNotif() {
        return nextnotif;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public int getColor() {
        return color;
    }

    public static class Daily extends Task{
        //Monday to sunday, true = selected
        boolean[] days = {false, false, false, false, false, false, false};

        public Daily(String name, String desc, LocalDateTime notif, boolean completed, boolean[] days, String color) {
            super(name, desc, notif, completed, color);
            this.days = days;
        }
    }

    public static class Goal extends Task{
        int completion;
        LocalDateTime startdate, enddate;

        public Goal(String name, String desc, LocalDateTime notif, boolean completed, int completion, String color) {
            super(name, desc, notif, completed, color);
            this.completion = completion;
        }
    }
}
