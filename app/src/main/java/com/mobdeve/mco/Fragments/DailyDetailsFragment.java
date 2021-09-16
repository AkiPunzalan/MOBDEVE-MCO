package com.mobdeve.mco.Fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobdeve.mco.Keys.DetailFields;
import com.mobdeve.mco.R;


public class DailyDetailsFragment extends Fragment {

    TextView tvMon, tvTue, tvWed, tvThu, tvFri, tvSat, tvSun;

    public DailyDetailsFragment() {
        // Required empty public constructor
    }

    public static DailyDetailsFragment newInstance(boolean[] days, String color) {
        DailyDetailsFragment fragment = new DailyDetailsFragment();
        Bundle args = new Bundle();
        args.putBooleanArray(DetailFields.DAYS.name(), days);
        args.putString(DetailFields.COLOR.name(), color);
        fragment.setArguments(args);
        return fragment;
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

        tvMon = v.findViewById(R.id.tv_details_days_mon);
        tvTue = v.findViewById(R.id.tv_details_days_tue);
        tvWed = v.findViewById(R.id.tv_details_days_wed);
        tvThu = v.findViewById(R.id.tv_details_days_thu);
        tvFri = v.findViewById(R.id.tv_details_days_fri);
        tvSat = v.findViewById(R.id.tv_details_days_sat);
        tvSun = v.findViewById(R.id.tv_details_days_sun);

        Bundle args = getArguments();

        if(args != null)
            setDaysoftheWeek(args.getBooleanArray(DetailFields.DAYS.name()),
                            args.getString(DetailFields.COLOR.name()));

        return v;
    }

    public void setDaysoftheWeek(boolean[] days, String color){
        if(days[0]) tvMon.setTextColor(Color.parseColor(color));
        if(days[1]) tvTue.setTextColor(Color.parseColor(color));
        if(days[2]) tvWed.setTextColor(Color.parseColor(color));
        if(days[3]) tvThu.setTextColor(Color.parseColor(color));
        if(days[4]) tvFri.setTextColor(Color.parseColor(color));
        if(days[5]) tvSat.setTextColor(Color.parseColor(color));
        if(days[6]) tvSun.setTextColor(Color.parseColor(color));
    }
}