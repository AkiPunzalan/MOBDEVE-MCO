package com.mobdeve.mco.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mobdeve.mco.DatabaseHelper;
import com.mobdeve.mco.Fragments.*;
import com.mobdeve.mco.Fragments.GoalAddFragment;
import com.mobdeve.mco.R;

import java.time.LocalDateTime;

public class AddActivity extends AppCompatActivity {

    FragmentContainerView frcAdd;
    Button btnConfirm;
    RadioGroup rgType;
    TextView tvTaskDesc;
    EditText etNotif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        initComponents();
    }

    private void initComponents(){

        frcAdd = findViewById(R.id.frc_add);
        tvTaskDesc = findViewById(R.id.tv_taskdesc);

        etNotif = findViewById(R.id.et_notif);
        etNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        etNotif.setText(hourOfDay + ":" + minute);
                    }
                }, 10, 10, false);
                timePickerDialog.show();
            }
        });

        rgType = findViewById(R.id.rg_tasktype);
        rgType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadio = group.findViewById(checkedId);
                String selected = (String) selectedRadio.getText();

                tvTaskDesc.setVisibility(View.VISIBLE);
                onCheckChangedEvent(selected);
            }
        });

        btnConfirm = findViewById(R.id.btn_add_confirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper db = new DatabaseHelper(AddActivity.this);
//                db.addTodo("Habit 000", "...", "#96D563", 1,
//                       LocalDateTime.of(2021, 10, 26, 19, 30, 0));
//
//                db.addDaily("Habit 000", "...", "#96D563", 1,
//                        LocalDateTime.of(2021, 10, 26, 19, 30, 0),
//                        new boolean[]{true, false, false, true, false, false, true});
//
//                db.addGoal("Habit 000", "...", "#96D563", 1,
//                        LocalDateTime.of(2021, 10, 26, 19, 30, 0),
//                        48);
            }
        });
    }

    private void onCheckChangedEvent(String type){
        switch(type){
            case "One-time":
                tvTaskDesc.setText(R.string.todo_desc);
                frcAdd.setVisibility(View.INVISIBLE);
                break;
            case "Daily" :
                tvTaskDesc.setText(R.string.daily_desc);
                frcAdd.setVisibility(View.VISIBLE);
                loadFragment(new DailyAddFragment());
                break;
            case "Goal":
                tvTaskDesc.setText(R.string.goal_desc);
                frcAdd.setVisibility(View.VISIBLE);
                loadFragment(new GoalAddFragment());
        }
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frc_add, fragment);
        transaction.commit();
    }
}