package com.mobdeve.mco.Fragments;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mobdeve.mco.Keys.DetailFields;
import com.mobdeve.mco.R;

public class GoalDetailsFragment extends Fragment {

    ProgressBar pbProgress;
    TextView tvProgress;

    public GoalDetailsFragment() {
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
        View v = inflater.inflate(R.layout.fragment_goal_details, container, false);

        tvProgress = v.findViewById(R.id.tv_details_goal_progress);
        pbProgress = v.findViewById(R.id.pb_details_goal_progress);

        Bundle args = getArguments();

        if(args != null){
            setProgress(args.getInt(DetailFields.PROGRESS.name()),
                        args.getString(DetailFields.COLOR.name()));
        }

        return v;
    }

    private void setProgress(int completion, String color) {
        String s = completion+"% complete";
        tvProgress.setText(s);

        pbProgress.setProgress(completion);
        pbProgress.setProgressTintList(ColorStateList.valueOf(Color.parseColor(color)));
    }
}