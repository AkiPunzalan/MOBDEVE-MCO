package com.mobdeve.mco.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.mobdeve.mco.Activities.AddActivity;
import com.mobdeve.mco.Activities.MainActivity;
import com.mobdeve.mco.R;

public class DailyAddFragment extends Fragment implements View.OnClickListener{

    CheckBox cbMon, cbTue, cbWed, cbThu, cbFri, cbSat, cbSun;
    boolean[] days = {true, false, false, false, false, false, false};

    public DailyAddFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_daily_add, container, false);

        initComponents(v);
        return v;
    }

    public void initComponents(View v){
        cbMon = v.findViewById(R.id.cb_mon);
        cbTue = v.findViewById(R.id.cb_tue);
        cbWed = v.findViewById(R.id.cb_wed);
        cbThu = v.findViewById(R.id.cb_thu);
        cbFri = v.findViewById(R.id.cb_fri);
        cbSat = v.findViewById(R.id.cb_sat);
        cbSun = v.findViewById(R.id.cb_sun);

        cbMon.setOnClickListener(this);
        cbTue.setOnClickListener(this);
        cbWed.setOnClickListener(this);
        cbThu.setOnClickListener(this);
        cbFri.setOnClickListener(this);
        cbSat.setOnClickListener(this);
        cbSun.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        CheckBox cbClicked = (CheckBox)v;
        int clicked = v.getId();

        switch (clicked) {
            case R.id.cb_mon:
                days[0] = cbClicked.isChecked();
                break;
            case R.id.cb_tue:
                days[1] = cbClicked.isChecked();
                break;
            case R.id.cb_wed:
                days[2] = cbClicked.isChecked();
                break;
            case R.id.cb_thu:
                days[3] = cbClicked.isChecked();
                break;
            case R.id.cb_fri:
                days[4] = cbClicked.isChecked();
                break;
            case R.id.cb_sat:
                days[5] = cbClicked.isChecked();
                break;
            case R.id.cb_sun:
                days[6] = cbClicked.isChecked();
                break;
            default:
                break;
        }

        AddActivity aa = (AddActivity) getActivity();
        aa.retrieveDays(days);
    }
}