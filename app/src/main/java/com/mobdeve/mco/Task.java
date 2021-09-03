package com.mobdeve.mco;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
    int _id;
    String name, desc;
    Boolean status;
    String color;

    //TODO: implement notification
    LocalDateTime nextnotif;

    public Task(String name, String desc, String color, boolean status, LocalDateTime notif){
        this.name = name;
        this.desc = desc;
        this.color = color;
        this.status = status;
        this.nextnotif = notif;
    }

    public Task(int _id, String name, String desc, String color, boolean status, LocalDateTime notif){
        this._id = _id;
        this.name = name;
        this.desc = desc;
        this.nextnotif = notif;
        this.status = status;
        this.color = color;
    }

    //db to-do constructor
    public Task(int _id, String name, String desc, String color, int status, String notif){
        this._id = _id;
        this.name = name;
        this.desc = desc;
        this.color = color;
        this.status = getStatus(status);
        this.nextnotif = getNotif(notif);
    }

    public Task(String name, String color, boolean completion, LocalDateTime time) {}

    public Task() { }

    public int getId(){
        return _id;
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

    public boolean getStatus() {
        return status;
    }

    public boolean getStatus(int status){
        if(status == 1)
            return true;
        else return false;
    }

    public String getColor() {
        return color;
    }

    public static class Daily extends Task{
        //Monday to sunday, true = selected
        boolean[] days = {false, false, false, false, false, false, false};

        public Daily(String name, String desc, String color, boolean status, LocalDateTime notif, boolean[] days) {
            super(name, desc, color, status, notif);
            this.days = days;
        }

        //db daily constructor
        public Daily(int _id, String name, String desc, String color, int status, String notif, String days) {
            super();
            this._id = _id;
            this.name = name;
            this.desc = desc;

            if(notif != null)
                this.nextnotif = getNotif(notif);
            else this.nextnotif = null;

            this.color = color;
            this.status = getStatus(status);
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

        public Goal(String name, String desc, String color, boolean status, LocalDateTime notif, int completion) {
            super(name, desc, color, status, notif);
            this.completion = completion;
        }

        public Goal(int _id, String name, String desc, String color, int status, String notif, int completion) {
            super();
            this.name = name;
            this.desc = desc;

            if(notif != null)
                this.nextnotif = getNotif(notif);
            else this.nextnotif = null;

            this.color = color;
            this.status = getStatus(status);
            this.completion = completion;
        }

        public int getCompletion(){
            return completion;
        }
    }



}
