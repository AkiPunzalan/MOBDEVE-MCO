package com.mobdeve.mco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainActivity extends AppCompatActivity {

    private TextView tvToday;
    private BottomNavigationView nav;
    private FloatingActionButton fab_add;
    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = getSupportActionBar();
        toolbar.setTitle("Daily Habits");

        initComponents();
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
        transaction.addToBackStack(null);
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

    private String getToday(){
        LocalDateTime today = LocalDateTime.now();
        return today.format(DateTimeFormatter.ofPattern("EE, dd MMM"));
    }
}