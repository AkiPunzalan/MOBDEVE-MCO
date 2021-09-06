package com.mobdeve.mco.Activities;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;

import android.app.TimePickerDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.mobdeve.mco.Fragments.*;
import com.mobdeve.mco.Fragments.GoalAddFragment;
import com.mobdeve.mco.Keys.Types;
import com.mobdeve.mco.R;

import org.jetbrains.annotations.NotNull;

import eltos.simpledialogfragment.SimpleDialog;
import eltos.simpledialogfragment.color.SimpleColorDialog;


public class AddActivity extends AppCompatActivity implements SimpleDialog.OnDialogResultListener{

    FragmentContainerView frcAdd;
    Button btnConfirm;
    ImageButton btnColor;
    ImageView ivColor;
    RadioGroup rgType;
    TextView tvTaskDesc;
    EditText etNotif;

    String type, color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        initComponents();
        setDefaults();
    }

    private void initComponents(){

        //color palette array
        @ColorInt int[] colors = new int[]{
            ContextCompat.getColor(this, R.color.task_red),
            ContextCompat.getColor(this, R.color.task_orange),
            ContextCompat.getColor(this, R.color.task_gold),
            ContextCompat.getColor(this, R.color.task_green),
            ContextCompat.getColor(this, R.color.task_mint),
            ContextCompat.getColor(this, R.color.task_teal),
            ContextCompat.getColor(this, R.color.task_blue),
            ContextCompat.getColor(this, R.color.task_purple)
        };

        frcAdd = findViewById(R.id.frc_add);
        tvTaskDesc = findViewById(R.id.tv_taskdesc);

        ivColor = findViewById(R.id.iv_add_color);
        btnColor = findViewById(R.id.btn_add_color);
        btnColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleColorDialog.build()
                    .title("Pick color for task")
                    .colorPreset(getResources().getColor(R.color.grey_highlight))
                    .colors(colors)
                    .allowCustom(false)
                    .cancelable(false)
                    .show(AddActivity.this, "ColorPicker");
            }
        });

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

//                DatabaseHelper db = new DatabaseHelper(AddActivity.this);
//                db.addTodo(new Task("Habit 030", "...", "#96D563", false,
//                    LocalDateTime.of(2021, 10, 26, 19, 30, 0))
//                );
//
//                db.addDaily(new Task.Daily("Habit 030", "...", "#96D563", false,
//                    LocalDateTime.of(2021, 10, 26, 19, 30, 0),
//                    new boolean[]{true, false, false, true, false, false, true})
//                );
//
//                db.addGoal(new Task.Goal("Habit 030", "...", "#96D563", false,
//                    LocalDateTime.of(2021, 10, 26, 19, 30, 0),
//                    48)
//                );
            }
        });
    }

    private void onCheckChangedEvent(String type){
        switch(type){
            case "One-time":
                tvTaskDesc.setText(R.string.todo_desc);
                frcAdd.setVisibility(View.INVISIBLE);
                type = Types.Todo.name();
                break;
            case "Daily" :
                tvTaskDesc.setText(R.string.daily_desc);
                frcAdd.setVisibility(View.VISIBLE);
                type = Types.Daily.name();
                loadFragment(new DailyAddFragment());
                break;
            case "Goal":
                tvTaskDesc.setText(R.string.goal_desc);
                frcAdd.setVisibility(View.VISIBLE);
                type = Types.Goal.name();
                loadFragment(new GoalAddFragment());
        }
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frc_add, fragment);
        transaction.commit();
    }

    @Override
    public boolean onResult(@NonNull @NotNull String dialogTag, int which, @NonNull @NotNull Bundle extras) {

        if (dialogTag.equals("ColorPicker")){
            int selectedcolor = extras.getInt(SimpleColorDialog.COLOR);
            String sColor = String.format("#%06x", selectedcolor & 0xffffff);

            ImageViewCompat.setImageTintList(ivColor, ColorStateList.valueOf(Color.parseColor(sColor)));
            color = sColor;
            return true;
        }
        return false;
    }

    //Default values of form
    private void setDefaults() {
        color = String.format("#%06x", ContextCompat.getColor(this, R.color.task_red) & 0xffffff);
        type = Types.Todo.name();
    }
}