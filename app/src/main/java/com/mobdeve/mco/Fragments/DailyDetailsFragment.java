package com.mobdeve.mco.Fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.mobdeve.mco.Activities.TaskDetailsActivity;
import com.mobdeve.mco.Keys.DetailFields;
import com.mobdeve.mco.R;

import java.util.Arrays;


public class DailyDetailsFragment extends Fragment implements View.OnClickListener {

    CheckBox cbMon, cbTue, cbWed, cbThu, cbFri, cbSat, cbSun;
    boolean[] days;
    String color;

    public DailyDetailsFragment() {
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
        View v = inflater.inflate(R.layout.fragment_daily_details, container, false);

        cbMon = v.findViewById(R.id.cb_details_days_mon);
        cbTue = v.findViewById(R.id.cb_details_days_tue);
        cbWed = v.findViewById(R.id.cb_details_days_wed);
        cbThu = v.findViewById(R.id.cb_details_days_thu);
        cbFri = v.findViewById(R.id.cb_details_days_fri);
        cbSat = v.findViewById(R.id.cb_details_days_sat);
        cbSun = v.findViewById(R.id.cb_details_days_sun);

        cbMon.setOnClickListener(this);
        cbTue.setOnClickListener(this);
        cbWed.setOnClickListener(this);
        cbThu.setOnClickListener(this);
        cbFri.setOnClickListener(this);
        cbSat.setOnClickListener(this);
        cbSun.setOnClickListener(this);

        Bundle args = getArguments();

        if(args != null){
            days = args.getBooleanArray(DetailFields.DAYS.name());
            color = args.getString(DetailFields.COLOR.name());
            setDaysoftheWeek();
        }

        return v;
    }

    private void setDaysoftheWeek(){
        if(days[0]) cbMon.setTextColor(Color.parseColor(color));
        if(days[1]) cbTue.setTextColor(Color.parseColor(color));
        if(days[2]) cbWed.setTextColor(Color.parseColor(color));
        if(days[3]) cbThu.setTextColor(Color.parseColor(color));
        if(days[4]) cbFri.setTextColor(Color.parseColor(color));
        if(days[5]) cbSat.setTextColor(Color.parseColor(color));
        if(days[6]) cbSun.setTextColor(Color.parseColor(color));

        cbMon.setChecked(days[0]);
        cbTue.setChecked(days[1]);
        cbWed.setChecked(days[2]);
        cbThu.setChecked(days[3]);
        cbFri.setChecked(days[4]);
        cbSat.setChecked(days[5]);
        cbSun.setChecked(days[6]);
    }

    @Override
    public void onClick(View v) {
        CheckBox cb = (CheckBox) v;
        int id = v.getId();

        switch (id){
            case R.id.cb_details_days_mon:
                days[0] = !days[0];
                break;
            case R.id.cb_details_days_tue:
                days[1] = !days[1];
                break;
            case R.id.cb_details_days_wed:
                days[2] = !days[2];
                break;
            case R.id.cb_details_days_thu:
                days[3] = !days[3];
                break;
            case R.id.cb_details_days_fri:
                days[4] = !days[4];
                break;
            case R.id.cb_details_days_sat:
                days[5] = !days[5];
                break;
            case R.id.cb_details_days_sun:
                days[6] = !days[6];
                break;
        }

        if(cb.isChecked())
            cb.setTextColor(Color.parseColor(color));
        else cb.setTextColor(Color.parseColor("#595959"));

        TaskDetailsActivity tda = (TaskDetailsActivity) getActivity();
        tda.retrieveDays(days);
    }
}