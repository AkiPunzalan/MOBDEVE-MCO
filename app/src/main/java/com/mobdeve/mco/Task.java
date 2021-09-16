package com.mobdeve.mco;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
    protected int _id;
    protected String name, desc;
    protected Boolean status, notifOn;
    protected String color;

    LocalDateTime nextnotif;

    public Task(String name, String desc, String color, boolean status, boolean notifOn, LocalDateTime notif){
        this.name = name;
        this.desc = desc;
        this.color = color;
        this.status = status;
        this.notifOn = notifOn;
        this.nextnotif = notif;
    }

    //db to-do constructor
    public Task(int _id, String name, String desc, String color, int status, int notifOn, String notif){
        this._id = _id;
        this.name = name;
        this.desc = desc;
        this.color = color;
        this.status = intToBoolean(status);
        this.notifOn = intToBoolean(notifOn);
        this.nextnotif = getNotif(notif);
    }

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

    public static LocalDateTime getNotif(String notif){
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

    public boolean intToBoolean(int n){
        if(n == 1)
            return true;
        else return false;
    }

    public boolean getNotifOn(){
        return notifOn;
    }

    public String getColor() {
        return color;
    }

    public static class Daily extends Task{
        //Monday to sunday, true = selected
        boolean[] days = {false, false, false, false, false, false, false};

        public Daily(String name, String desc, String color, boolean status, boolean notifOn, LocalDateTime notif, boolean[] days) {
            super(name, desc, color, status, notifOn, notif);
            this.days = days;
        }

        //db daily constructor
        public Daily(int _id, String name, String desc, String color, int status, int notifOn, String notif, String days) {
            super();
            this._id = _id;
            this.name = name;
            this.desc = desc;

            if(notif != null)
                this.nextnotif = getNotif(notif);
            else this.nextnotif = null;

            this.color = color;
            this.status = intToBoolean(status);
            this.notifOn = intToBoolean(notifOn);
            this.days = getDays(days);
        }

        public boolean[] getDays(){
            return days;
        }

        public static boolean[] getDays(String s){
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
        int currentprogress, maxreq;
        LocalDateTime enddate;

        public Goal(String name, String desc, String color, boolean status, boolean notifOn, LocalDateTime notif, int currentprogress) {
            super(name, desc, color, status, notifOn, notif);
            this.currentprogress = currentprogress;
//            this.maxreq = maxreq;
//            this.enddate = enddate;
        }

        public Goal(int _id, String name, String desc, String color, int status, int notifOn, String notif, int currentprogress) {
            super();
            this._id = _id;
            this.name = name;
            this.desc = desc;

            if(notif != null)
                this.nextnotif = getNotif(notif);
            else this.nextnotif = null;

            this.color = color;
            this.status = intToBoolean(status);
            this.notifOn = intToBoolean(notifOn);
            this.currentprogress = currentprogress;
        }

        public int getProgress(){
            return currentprogress;
        }
    }



}
