package com.mobdeve.mco.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobdeve.mco.DatabaseHelper;
import com.mobdeve.mco.Fragments.*;
import com.mobdeve.mco.Keys.DetailFields;
import com.mobdeve.mco.Keys.Types;
import com.mobdeve.mco.R;

public class TaskDetailsActivity extends AppCompatActivity {

    private DatabaseHelper db = new DatabaseHelper(TaskDetailsActivity.this);

    private ImageView ivCheck;
    private TextView tvName, tvDesc, tvDone, tvNotif, tvCheckin;
    private EditText etEditName, etEditDesc;
    private Button btnDelete, btnCancel, btnSave;

    private FragmentContainerView frcDetails;
    private ActionBar toolbar;

    //object values
    int id;
    String type, name, desc, notif, color;
    Boolean done;

    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        getIntentValues();
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
    }

    private void initDialogComponents(Dialog d) {
        etEditName = d.findViewById(R.id.et_details_dialog_name);
        etEditDesc = d.findViewById(R.id.et_details_dialog_desc);

        btnDelete = d.findViewById(R.id.btn_details_dialog_delete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteOneRow(String.valueOf(id), type);
                d.dismiss();
                finish();
            }
        });

        btnCancel = d.findViewById(R.id.btn_details_dialog_cancel);
        btnCancel.setOnClickListener(v -> d.dismiss());

        btnSave = d.findViewById(R.id.btn_details_dialog_confirm);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateTask();
                d.dismiss();
            }
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

        tvCheckin = findViewById(R.id.tv_details_checkin);
        if(type.equals(Types.Goal.name()))
            tvCheckin.setVisibility(View.GONE);

        ivCheck = findViewById(R.id.iv_details_done);
        ivCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                done = !done;
                setDone(done, color);
                db.updateStatus(id, done, type);
            }
        });

        tvName.setText(name);
        setDesc(desc);
        tvNotif.setText(notif);
        setDone(done, color);
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

    private void UpdateTask(){
        String inName = etEditName.getText().toString().trim();
        String inDesc = etEditDesc.getText().toString().trim();

        //if empty use current object values
        if(inName.equals(""))
            inName = name;

        if(inDesc.equals(""))
            inDesc = desc;

        db.updateNameAndDesc(id, inName, inDesc, type);

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

        fragment.setArguments(args);
        transaction.replace(R.id.frc_details, fragment).commit();
    }
}