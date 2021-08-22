package com.mobdeve.mco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

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
        nav = findViewById(R.id.bottom_nav);
        nav.setOnNavigationItemSelectedListener(navlistener);

        fab_add = findViewById(R.id.fab_add);
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Implement Add Task Activity
                //add activity
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
}