package com.mobdeve.mco;

import java.util.Date;

public class Task {
    String name, desc;
    Boolean completed;

    //TODO: implement notification class
    Date notif;

    public Task(String name, String desc, Date notif, Boolean completed){
        this.name = name;
        this.desc = desc;
        this.notif = notif;
        this.completed = completed;
    }

    public class Daily extends Task{
        //Monday to sunday, true = selected
        boolean[] days = {false, false, false, false, false, false, false};

        public Daily(String name, String desc, Date notif, boolean completed, boolean[] days) {
            super(name, desc, notif, completed);
            this.days = days;
        }
    }

    public class Goal extends Task{
        int completion;
        Date enddate;

        public Goal(String name, String desc, Date notif, boolean completed) {
            super(name, desc, notif, completed);
        }
    }
}
