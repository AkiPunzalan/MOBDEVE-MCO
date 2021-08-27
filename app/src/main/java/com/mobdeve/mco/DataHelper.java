package com.mobdeve.mco;

import android.util.Log;

import androidx.core.content.ContextCompat;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

public class DataHelper {

    //generate arraylist of daily tasks
    public static ArrayList<Task.Daily> initDaily(){
        ArrayList<Task.Daily> list = new ArrayList<Task.Daily>();

        list.add(new Task.Daily(
                "Task #7",
                null,
                LocalDateTime.of(2021, Month.AUGUST, 26, 19, 30, 0),
                true, new boolean[]{true, false, false, true, false, false, true},
                "#96D563")
        );

        list.add(new Task.Daily(
                "Task #1",
                "...",
                LocalDateTime.of(2021, Month.SEPTEMBER, 29, 12, 10, 0),
                true, new boolean[]{true, true, true, true, true, true, true},
                "#DA3B55")
        );

        list.add(new Task.Daily(
                "Task #7",
                "...",
                null,
                false, new boolean[]{true, true, true, true, true, true, true},
                "#F07A48")
        );

        list.add(new Task.Daily(
                "Task #3",
                "...",
                LocalDateTime.of(2021, Month.SEPTEMBER, 8, 12, 0, 0),
                false, new boolean[]{false, true, true, true, true, true, false},
                "#96D563")
        );

        list.add(new Task.Daily(
                "Task #6",
                "...",
                LocalDateTime.of(2021, Month.SEPTEMBER, 18, 6, 20, 0),
                true, new boolean[]{true, true, false, false, true, false, true},
                "#DB8C2C")
        );

        return list;
    }

    public ArrayList<Task> initTodo(){
        ArrayList<Task> list = new ArrayList<>();

        list.add(new Task(
            "Task #1",
            "...",
            LocalDateTime.of(2021, Month.SEPTEMBER, 18, 6, 20, 0),
            true,
            "#DB8C2C"
        ));

        list.add(new Task(
                "Task #7",
                "...",
                LocalDateTime.of(2021, Month.SEPTEMBER, 10, 12, 20, 0),
                true,
                "#4A29E0"
        ));

        list.add(new Task(
                "Task #",
                "...",
                LocalDateTime.of(2021, Month.SEPTEMBER, 11, 13, 0, 0),
                true,
                "#96D563"
        ));

        return list;
    }

    public ArrayList<Task.Goal> initGoal(){
        ArrayList<Task.Goal> list = new ArrayList<>();

        list.add(new Task.Goal(
                "Task #2",
                "...",
                LocalDateTime.of(2021, Month.SEPTEMBER, 11, 13, 0, 0),
                true, 70,
                "#96D563"
        ));

        list.add(new Task.Goal(
                "Task #7",
                "...",
                LocalDateTime.of(2021, Month.SEPTEMBER, 2, 19, 30, 0),
                true, 50,
               "#42CDC1")
        );

        list.add(new Task.Goal(
                "Task #1",
                "...",
                LocalDateTime.of(2021, Month.SEPTEMBER, 29, 12, 10, 0),
                true, 80,
                "#DA3B55")
        );

        list.add(new Task.Goal(
                "Task #7",
                "...",
                LocalDateTime.of(2021, Month.SEPTEMBER, 8, 12, 0, 0),
                false, 30,
                "#42CDC1")
        );

        list.add(new Task.Goal(
                "Task #3",
                "...",
                LocalDateTime.of(2021, Month.SEPTEMBER, 8, 12, 0, 0),
                false, 20,
               "#4A29E0")
        );

        return list;
    }
}
