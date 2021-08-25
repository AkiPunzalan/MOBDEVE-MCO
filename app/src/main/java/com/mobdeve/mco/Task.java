package com.mobdeve.mco;

import android.graphics.Color;

import androidx.annotation.ColorInt;

import java.time.LocalDateTime;
import java.util.Date;

public class Task {
    String name, desc;
    Boolean completed;
    String color;

    //TODO: implement notification
    LocalDateTime nextnotif;

    public Task(String name, String desc, LocalDateTime notif, Boolean completed, String color){
        this.name = name;
        this.desc = desc;
        this.nextnotif = notif;
        this.completed = completed;
        this.color = color;
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

    public boolean getCompleted() {
        return completed;
    }

    public String getColor() {
        return color;
    }

    public static class Daily extends Task{
        //Monday to sunday, true = selected
        boolean[] days = {false, false, false, false, false, false, false};

        public Daily(String name, String desc, LocalDateTime notif, boolean completed, boolean[] days, String color) {
            super(name, desc, notif, completed, color);
            this.days = days;
        }

        public boolean[] getDays(){
            return days;
        }
    }

    public static class Goal extends Task{
        int completion;
        LocalDateTime enddate;

        public Goal(String name, String desc, LocalDateTime notif, boolean completed, int completion, String color) {
            super(name, desc, notif, completed, color);
            this.completion = completion;
        }

        public int getCompletion(){
            return completion;
        }
    }
}
