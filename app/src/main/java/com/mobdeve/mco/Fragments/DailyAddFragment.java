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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DailyAddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DailyAddFragment extends Fragment implements View.OnClickListener{

    CheckBox cbMon, cbTue, cbWed, cbThu, cbFri, cbSat, cbSun;
    boolean[] days = {true, false, false, false, false, false, false};

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DailyAddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DailyAddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DailyAddFragment newInstance(String param1, String param2) {
        DailyAddFragment fragment = new DailyAddFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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