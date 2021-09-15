package com.mobdeve.mco.Activities;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mobdeve.mco.DatabaseHelper;
import com.mobdeve.mco.Fragments.*;
import com.mobdeve.mco.Fragments.GoalAddFragment;
import com.mobdeve.mco.Keys.Types;
import com.mobdeve.mco.R;
import com.mobdeve.mco.Task;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.Calendar;

import eltos.simpledialogfragment.SimpleDialog;
import eltos.simpledialogfragment.color.SimpleColorDialog;


public class AddActivity extends AppCompatActivity implements SimpleDialog.OnDialogResultListener{

    FragmentContainerView frcAdd;
    Button btnConfirm;
    ImageButton btnColor;
    ImageView ivColor;
    RadioGroup rgType;
    TextView tvTaskDesc, tvNotifLabel;
    EditText etName, etDesc, etNotif, etDate;

    //object values
    private String name, desc, type, color;
    private int year, month, day, hour, min;
    private boolean[] daysOfWeek;

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
        tvNotifLabel = findViewById(R.id.tv_add_notiflabel);

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

        etName = findViewById(R.id.et_add_name);
        etDesc = findViewById(R.id.et_add_desc);

        etNotif = findViewById(R.id.et_notif);
        etNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int inHourOfDay, int inMinute) {
                        etNotif.setText(inHourOfDay + ":" + inMinute);
                        hour = inHourOfDay;
                        min = inMinute;
                    }
                }, 10, 10, false);
                timePickerDialog.show();
            }
        });

        etDate = findViewById(R.id.et_notif_date);
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar today = Calendar.getInstance();

                new DatePickerDialog(AddActivity.this,
                    //event listener
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int inYear, int inMonth, int inDayOfMonth) {
                            inMonth += 1;
                            etDate.setText(inDayOfMonth + "/" + inMonth + "/" + inYear);
                            year = inYear;
                            month = inMonth;
                            day = inDayOfMonth;
                        }
                    },
                    //default time on dialog open
                    today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH))
                    .show();
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
                if(validateTask())
                    addTask(new DatabaseHelper(AddActivity.this));
            }
        });
    }

    //switch type event handler
    private void onCheckChangedEvent(String type){
        switch(type){
            case "One-time":
                tvTaskDesc.setText(R.string.todo_desc);
                frcAdd.setVisibility(View.INVISIBLE);
                etDate.setVisibility(View.VISIBLE);
                tvNotifLabel.setText("Notif*");
                this.type = Types.Todo.name();
                break;
            case "Daily" :
                tvTaskDesc.setText(R.string.daily_desc);
                frcAdd.setVisibility(View.VISIBLE);
                etDate.setVisibility(View.GONE);
                tvNotifLabel.setText("Notif*");
                this.type = Types.Daily.name();
                loadFragment(new DailyAddFragment());
                break;
            case "Goal":
                tvTaskDesc.setText(R.string.goal_desc);
                frcAdd.setVisibility(View.VISIBLE);
                etDate.setVisibility(View.VISIBLE);
                tvNotifLabel.setText("End Date");
                this.type = Types.Goal.name();
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

    //takes values from views and checks for empty fields
    private boolean validateTask(){
        name = etName.getText().toString().trim();
        desc = etDesc.getText().toString().trim();

        if(desc.equals(""))
            desc = null;

        if(name.equals("")) {
            Toast.makeText(this, "Please add task name", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(etNotif.getText().toString().equals("")) {
            Toast.makeText(this, "Please add task time", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(etDate.getText().toString().equals("") &&
                (type.equals(Types.Todo.name()) || type.equals(Types.Goal.name()) )){
            Toast.makeText(this, "Please add task date", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }

    private void addTask(DatabaseHelper db) {
        long resultId = -1;

        //add To-do
        if(type.equals(Types.Todo.name())){
            resultId = db.addTodo(new Task(name, desc, color, false,
                            LocalDateTime.of(year, month, day, hour, min, 0)));
        }
        //add Daily
        else if(type.equals(Types.Daily.name())){
            LocalDateTime today = LocalDateTime.now();
            resultId = db.addDaily(new Task.Daily(name, desc, color, false,
                            LocalDateTime.of(2021, 10, 13, hour, min, 0),
                            new boolean[]{false, true, false, true, true, true, true}));
        }
        //add Goal
        else if(type.equals(Types.Goal.name())){
            resultId = db.addGoal(new Task.Goal(name, desc, color, false,
                            LocalDateTime.of(year, month, day, hour, min, 0),
                            48));
        }

        Log.v("id_check", String.valueOf(resultId));

        if(resultId == -1)
            Toast.makeText(this, "Add Task Failed", Toast.LENGTH_SHORT).show();
        else {
            //schedule alarm
            Toast.makeText(AddActivity.this, "Added Successfully", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}