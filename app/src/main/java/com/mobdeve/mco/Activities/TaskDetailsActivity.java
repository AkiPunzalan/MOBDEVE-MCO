package com.mobdeve.mco.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mobdeve.mco.AlarmHelper;
import com.mobdeve.mco.DatabaseHelper;
import com.mobdeve.mco.Fragments.*;
import com.mobdeve.mco.Keys.DetailFields;
import com.mobdeve.mco.Keys.Types;
import com.mobdeve.mco.R;
import com.mobdeve.mco.Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class TaskDetailsActivity extends AppCompatActivity {

    private DatabaseHelper db = new DatabaseHelper(TaskDetailsActivity.this);
    AlarmHelper ah;

    private ImageView ivCheck;
    private TextView tvName, tvDesc, tvDone, tvNotif, tvCheckin, tvEndDate;
    private EditText etEditName, etEditDesc;
    private Button btnDelete, btnCancel, btnSave, btnEditTime;
    private SwitchCompat swNotif;

    private FragmentContainerView frcDetails;
    private ActionBar toolbar;

    //object values
    int id, hour, min, year, month, day;
    String type, name, desc, notif, color;
    Boolean done, notifOn;

    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);
        ah = new AlarmHelper(this);
        getIntentValues();

        //default values for time in edit dialog
        LocalDateTime currentTime = Task.getNotif(db.getStringField(type, "next_notif", id));
        year = currentTime.getYear();
        month = currentTime.getMonthValue();
        day = currentTime.getDayOfMonth();
        hour = currentTime.getHour();
        min = currentTime.getMinute();

        initComponents();
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

        //BTN CLICK EVENT
        if (id == R.id.edit_details){
            //DIALOG SETUP
            final Dialog dialog = new Dialog(TaskDetailsActivity.this);

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.dialog_editdetails);

            //INIT DIALOG COMPONENTS
            initDialogComponents(dialog);
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void getIntentValues(){
        i = getIntent();

        id = i.getIntExtra(DetailFields.ID.name(), -1);
        type = i.getStringExtra(DetailFields.TYPE.name());
        name = i.getStringExtra(DetailFields.NAME.name());
        desc = i.getStringExtra(DetailFields.DESC.name());
        notif = i.getStringExtra(DetailFields.NOTIF.name());
        color = i.getStringExtra(DetailFields.COLOR.name());
        done = i.getBooleanExtra(DetailFields.DONE.name(), false);
        notifOn = i.getBooleanExtra(DetailFields.NOTIFON.name(), true);
    }

    private void EditTime(){
        Calendar today = Calendar.getInstance();
        DatePickerDialog dpdialog = new DatePickerDialog(TaskDetailsActivity.this,
            //event listener
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int inYear, int inMonth, int inDayOfMonth) {
                    inMonth += 1;
                    year = inYear;
                    month = inMonth;
                    day = inDayOfMonth;
                    EditTimeDialog();
                }
            },
            //default time on dialog open
            year, month-1, day);


        TimePickerDialog timePickerDialog = new TimePickerDialog(TaskDetailsActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int inHourOfDay, int inMinute) {
                hour = inHourOfDay;
                min = inMinute;

                if(type.equals(Types.Todo.name()) || type.equals(Types.Goal.name()))
                    dpdialog.show(); //display date dialog after time dialog
                else EditTimeDialog();
            }
        }, hour, min, false);
        timePickerDialog.show();
    }

    private void EditTimeDialog(){
        final Dialog editTimedialog = new Dialog(TaskDetailsActivity.this);

        editTimedialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        editTimedialog.setCancelable(true);
        editTimedialog.setContentView(R.layout.dialog_edittime);

        //INIT DIALOG COMPONENTS
        TextView tvTimeInput = editTimedialog.findViewById(R.id.tv_edittime_input);

        if(type.equals(Types.Daily.name()))
            tvTimeInput.setText(String.format("Time is %02d:%02d:%02d", hour, min, 0));
        else tvTimeInput.setText(String.format("Time is %02d-%02d-%02d %02d:%02d:%02d", year, month, day, hour, min, 0));

        Button btnEditTimeConfirm = editTimedialog.findViewById(R.id.btn_edittime_confirm);
        btnEditTimeConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String time = String.format("%02d-%02d-%02d %02d:%02d:%02d", year, month, day, hour, min, 0);
                String formatedtime;
                LocalDateTime ldtTime = Task.getNotif(time);
                db.updateTime(id, time, type);

                //change time on views
                if(type.equals(Types.Daily.name()))
                    formatedtime = ldtTime.format(DateTimeFormatter.ofPattern("hh:mm a"));
                else formatedtime = ldtTime.format(DateTimeFormatter.ofPattern("dd MMM, hh:mm a"));
                tvNotif.setText(formatedtime);

                //reschedule alarms
                if(type.equals(Types.Todo.name()) || type.equals(Types.Daily.name())) {
                    ah.cancelAllAlarms(type, id);

                    if(notifOn)
                        ah.setAlarm(type, id);
                }

                editTimedialog.dismiss();
            }
        });

        editTimedialog.show();
    }

    private void initDialogComponents(Dialog d) {
        etEditName = d.findViewById(R.id.et_details_dialog_name);
        etEditDesc = d.findViewById(R.id.et_details_dialog_desc);

        btnDelete = d.findViewById(R.id.btn_details_dialog_delete);
        btnDelete.setOnClickListener(v -> {
            db.deleteOneRow(String.valueOf(id), type);
            ah.cancelAllAlarms(type, id);
            d.dismiss();
            finish();
        });

        btnCancel = d.findViewById(R.id.btn_details_dialog_cancel);
        btnCancel.setOnClickListener(v -> d.dismiss());

        btnSave = d.findViewById(R.id.btn_details_dialog_confirm);
        btnSave.setOnClickListener(v -> {
            UpdateTask();
            d.dismiss();
        });


        etEditName.setHint(this.name);

        if(this.desc != null)
            etEditDesc.setHint(this.desc);
    }

    private void initComponents(){
        frcDetails = findViewById(R.id.frc_details);

        toolbar = getSupportActionBar();
        toolbar.setTitle("Task Details");

        tvName = findViewById(R.id.tv_details_name);
        tvDesc = findViewById(R.id.tv_details_desc);
        tvDone = findViewById(R.id.tv_details_done);
        tvNotif = findViewById(R.id.tv_details_notif);
        tvEndDate = findViewById(R.id.tv_details_label_enddate);
        tvCheckin = findViewById(R.id.tv_details_checkin);

        ivCheck = findViewById(R.id.iv_details_done);
        ivCheck.setOnClickListener(v -> {
            done = !done;
            setDone(done, color);
            db.updateStatus(id, done, type);
        });

        swNotif = findViewById(R.id.sw_details_label_notif);
        swNotif.setOnClickListener(v -> {
            notifOn = !notifOn;
            swNotif.setChecked(notifOn);
            db.updateNotifOn(id, notifOn, type);

            //LocalDateTime alarmTime = Task.getNotif(db.getTime(type, id));

            if(notifOn)
               ah.setAlarm(type, id);
            else ah.cancelAllAlarms(type, id);
        });

        btnEditTime = findViewById(R.id.btn_details_edit_time);
        btnEditTime.setOnClickListener(v -> {
            EditTime();
        });

        tvName.setText(name);
        setDesc(desc);
        tvNotif.setText(notif);
        setDone(done, color);
        swNotif.setChecked(notifOn);

        if(type.equals(Types.Goal.name())) {
            ivCheck.setEnabled(false);
            tvCheckin.setVisibility(View.GONE);
            swNotif.setVisibility(View.INVISIBLE);
            swNotif.setEnabled(false);
            tvEndDate.setVisibility(View.VISIBLE);
        }
    }

    private void setDesc(String desc){
        if(desc == null){
            tvDesc.setVisibility(View.GONE);
        } else {
            tvDesc.setText(desc);
            tvDesc.setVisibility(View.VISIBLE);
        }
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

    private void UpdateTask(){
        String inName = etEditName.getText().toString().trim();
        String inDesc = etEditDesc.getText().toString().trim();

        //if empty use current object values
        if(inName.equals(""))
            inName = name;

        if(inDesc.equals(""))
            inDesc = desc;

        db.updateTask(id, inName, inDesc, type);

        tvName.setText(inName);
        setDesc(inDesc);
    }

    private void displayFragment(String type){
        switch (type){
            case "Daily":
                loadFragment(new DailyDetailsFragment());
                break;
            case "Goal": loadFragment(new GoalDetailsFragment());
                break;
            default: frcDetails.setVisibility(View.GONE);
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        Bundle args = new Bundle();

        //add intent values relevant to fragments
        args.putBooleanArray(DetailFields.DAYS.name(), i.getBooleanArrayExtra(DetailFields.DAYS.name()));
        args.putString(DetailFields.COLOR.name(), color);
        args.putInt(DetailFields.PROGRESS.name(), i.getIntExtra(DetailFields.PROGRESS.name(), 0));
        args.putInt(DetailFields.PROGCOUNT.name(), i.getIntExtra(DetailFields.PROGCOUNT.name(), 0));
        args.putInt(DetailFields.MAXREQ.name(), i.getIntExtra(DetailFields.MAXREQ.name(), 10));

        fragment.setArguments(args);
        transaction.replace(R.id.frc_details, fragment).commit();
    }

    public void retrieveProgress(int currentprog, int progpercent){
        if(progpercent == 100){
            db.updateStatus(id, true, "Goal");
            setDone(true, color);
        } else {
            db.updateStatus(id, false, "Goal");
            setDone(false, color);
        }
        db.updateProgress(id, currentprog, progpercent);
    }
}