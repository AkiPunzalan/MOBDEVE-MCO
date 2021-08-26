package com.mobdeve.mco.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobdeve.mco.Fragments.*;
import com.mobdeve.mco.Keys.DetailFields;
import com.mobdeve.mco.R;

public class TaskDetailsActivity extends AppCompatActivity {

    private ImageView ivCheck;
    private TextView tvName, tvDesc, tvDone, tvNotif;

    private FragmentContainerView frcDetails;
    private ActionBar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);
        frcDetails = findViewById(R.id.frc_details);

        toolbar = getSupportActionBar();
        toolbar.setTitle("Daily Habits");

        ivCheck = findViewById(R.id.iv_details_done);
        tvName = findViewById(R.id.tv_details_name);
        tvDesc = findViewById(R.id.tv_details_desc);
        tvDone = findViewById(R.id.tv_details_done);
        tvNotif = findViewById(R.id.tv_details_notif);

        Intent i = getIntent();

        String type = i.getStringExtra(DetailFields.TYPE.name());
        String name = i.getStringExtra(DetailFields.NAME.name());
        String desc = i.getStringExtra(DetailFields.DESC.name());
        String notif = i.getStringExtra(DetailFields.NOTIF.name());
        String color = i.getStringExtra(DetailFields.COLOR.name());
        Boolean done = i.getBooleanExtra(DetailFields.DONE.name(), false);

        tvName.setText(name);
        tvDesc.setText(desc);
        tvNotif.setText(notif);
        setDone(done, color);

        displayFragment(type);
    }

    private void setDone(boolean done, String color){
        if(done){
            ImageViewCompat.setImageTintList(ivCheck, ColorStateList.valueOf(Color.parseColor(color)));
            tvDone.setText(R.string.done_text);
            tvDone.setTextColor(Color.parseColor(color));
        } else {
            tvDone.setText(R.string.notdone_text);
        }
    }

    private void displayFragment(String type){
        switch (type){
            case "DAILY": loadFragment(new DailyDetailsFragment());break;
            case "GOAL": loadFragment(new GoalDetailsFragment());
            default: frcDetails.setVisibility(View.GONE);
        }
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frc_details, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}