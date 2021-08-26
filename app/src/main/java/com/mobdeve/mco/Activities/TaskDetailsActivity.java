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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobdeve.mco.Fragments.*;
import com.mobdeve.mco.Keys.DetailFields;
import com.mobdeve.mco.R;

import java.util.Arrays;

public class TaskDetailsActivity extends AppCompatActivity {

    private ImageView ivCheck;
    private TextView tvName, tvDesc, tvDone, tvNotif;

    private FragmentContainerView frcDetails;
    private ActionBar toolbar;

    String type, name, desc, notif, color;
    Boolean done;

    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        initComponents();

        i = getIntent();

        type = i.getStringExtra(DetailFields.TYPE.name());
        name = i.getStringExtra(DetailFields.NAME.name());
        desc = i.getStringExtra(DetailFields.DESC.name());
        notif = i.getStringExtra(DetailFields.NOTIF.name());
        color = i.getStringExtra(DetailFields.COLOR.name());
        done = i.getBooleanExtra(DetailFields.DONE.name(), false);

        tvName.setText(name);
        setDesc(desc);
        tvNotif.setText(notif);
        setDone(done, color);

        displayFragment(type);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.edit_details){
            //TODO: EDIT TASK DIALOGUE
        }

        return super.onOptionsItemSelected(item);
    }

    private void initComponents(){
        frcDetails = findViewById(R.id.frc_details);

        toolbar = getSupportActionBar();
        toolbar.setTitle("Daily Habits");

        tvName = findViewById(R.id.tv_details_name);
        tvDesc = findViewById(R.id.tv_details_desc);
        tvDone = findViewById(R.id.tv_details_done);
        tvNotif = findViewById(R.id.tv_details_notif);

        ivCheck = findViewById(R.id.iv_details_done);
        ivCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                done = !done;
                setDone(done, color);
            }
        });
    }

    private void setDesc(String desc){
        if(desc == null){
            tvDesc.setVisibility(View.GONE);
        } else
            tvDesc.setText(desc);
    }

    private void setDone(boolean done, String color){
        if(done){
            ImageViewCompat.setImageTintList(ivCheck, ColorStateList.valueOf(Color.parseColor(color)));
            tvDone.setText(R.string.done_text);
            tvDone.setTextColor(Color.parseColor(color));
        } else {
            ImageViewCompat.setImageTintList(ivCheck, ColorStateList.valueOf(Color.parseColor(getString(R.string.grey_highlight_string))));
            tvDone.setTextColor(Color.parseColor(getString(R.string.grey_highlight_string)));
            tvDone.setText(R.string.notdone_text);
        }
    }

    private void displayFragment(String type){
        switch (type){
            case "DAILY":
                loadFragment(new DailyDetailsFragment());
                break;
            case "GOAL": loadFragment(new GoalDetailsFragment());
                break;
            default: frcDetails.setVisibility(View.GONE);
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        Bundle args = new Bundle();

        args.putBooleanArray(DetailFields.DAYS.name(), i.getBooleanArrayExtra(DetailFields.DAYS.name()));
        args.putString(DetailFields.COLOR.name(), color);

        fragment.setArguments(args);
        transaction.replace(R.id.frc_details, fragment).commit();
    }
}