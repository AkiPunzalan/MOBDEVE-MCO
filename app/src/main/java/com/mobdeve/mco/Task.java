package com.mobdeve.mco;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public Task(String name, String desc, String notif, int completed, String color){
        this.name = name;
        this.desc = desc;
        this.nextnotif = getNotif(notif);
        this.color = color;
        this.completed = getCompleted(completed);
    }

    public Task(String name, String color, boolean completion, LocalDateTime time) {}

    public Task() { }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public LocalDateTime getNotif() {
        return nextnotif;
    }

    public LocalDateTime getNotif(String notif){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(notif, format);
    }

    public String getNotifString(){
        if(nextnotif != null)
            return nextnotif.format(DateTimeFormatter.ofPattern("hh:mm a"));
        return "No Notifications";
    }

    public String getNotifStringWithMonth(){
        if(nextnotif != null)
            return nextnotif.format(DateTimeFormatter.ofPattern("dd MMM, hh:mm a"));
        return "No Notifications";
    }

    public boolean getCompleted() {
        return completed;
    }

    public boolean getCompleted(int completed){
        if(completed == 1)
            return true;
        else return false;
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

        public Daily(String name, String desc, String notif, int completed, String days, String color) {
            super();
            this.name = name;
            this.desc = desc;

            if(notif != null)
                this.nextnotif = getNotif(notif);
            else this.nextnotif = null;

            this.color = color;
            this.completed = getCompleted(completed);
            this.days = getDays(days);
        }

        public boolean[] getDays(){
            return days;
        }

        public boolean[] getDays(String s){
            boolean[] days = new boolean[7];

            for(int i=0; i<7; i++){
                if(s.charAt(i) == '1')
                    days[i] = true;
                else days[i] = false;
            }

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

        public Goal(String name, String desc, String notif, int completed, int completion, String color) {
            super();
            this.name = name;
            this.desc = desc;

            if(notif != null)
                this.nextnotif = getNotif(notif);
            else this.nextnotif = null;

            this.color = color;
            this.completed = getCompleted(completed);
            this.completion = completion;
        }

        public int getCompletion(){
            return completion;
        }
    }



}
