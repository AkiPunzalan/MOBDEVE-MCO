package com.mobdeve.mco.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mobdeve.mco.DatabaseHelper;
import com.mobdeve.mco.Fragments.*;
import com.mobdeve.mco.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sp;
    private DatabaseHelper db;
    private TextView tvToday;
    private BottomNavigationView nav;
    private FloatingActionButton fab_add;
    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHelper(this);
        resetStatus();
        createNotifChannel();

        toolbar = getSupportActionBar();
        toolbar.setTitle("Daily Habits");

        initComponents();
    }

    private void resetStatus(){
        sp = PreferenceManager.getDefaultSharedPreferences(this);

        //Reset Daily Statuses on the next day
        int lastStartupTime= sp.getInt("last_time_started", -1);
        int today = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);

        if (today != lastStartupTime) {
            Toast.makeText(this, "New Day!", Toast.LENGTH_SHORT).show();
            db.resetStatus();

            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("last_time_started", today);
            editor.apply();
        }
    }

    private void initComponents(){
        tvToday = findViewById(R.id.tv_today);
        tvToday.setText(getToday());

        nav = findViewById(R.id.bottom_nav);
        nav.setOnNavigationItemSelectedListener(navlistener);

        fab_add = findViewById(R.id.fab_add);
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddActivity.class);
                startActivity(i);
            }
        });

        //fragment on startup
        loadFragment(new DailyFragment());
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frc_main, fragment);
        transaction.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navlistener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment;

            switch (item.getItemId()) {
                case R.id.nav_daily:
                    toolbar.setTitle("Daily Habits");
                    selectedFragment = new DailyFragment();
                    loadFragment(selectedFragment);
                    return true;

                case R.id.nav_todo:
                    toolbar.setTitle("To Do List");
                    selectedFragment = new TodoFragment();
                    loadFragment(selectedFragment);
                    return true;

                case R.id.nav_goals:
                    toolbar.setTitle("Habit Goals");
                    selectedFragment = new GoalFragment();
                    loadFragment(selectedFragment);
                    return true;

                case R.id.nav_metrics:
                    toolbar.setTitle("Habit Metrics");
                    selectedFragment = new MetricsFragment();
                    loadFragment(selectedFragment);
                    return true;

            }
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.signin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.signin){
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    private String getToday(){
        LocalDateTime today = LocalDateTime.now();
        return today.format(DateTimeFormatter.ofPattern("EE, dd MMM"));
    }

    public void createNotifChannel(){
        CharSequence name = "TaskNotifChannel";
        String desc = "Channel for Tasks";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        NotificationChannel channel = new NotificationChannel("DoWhile", name, importance);
        channel.setDescription(desc);

        NotificationManager notifManager = getSystemService(NotificationManager.class);
        notifManager.createNotificationChannel(channel);
    }
}