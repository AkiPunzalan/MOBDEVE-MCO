package com.mobdeve.mco.Fragments;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mobdeve.mco.Activities.AddActivity;
import com.mobdeve.mco.Activities.TaskDetailsActivity;
import com.mobdeve.mco.Keys.DetailFields;
import com.mobdeve.mco.R;

public class GoalDetailsFragment extends Fragment {

    ProgressBar pbProgress;
    TextView tvProgressPercent, tvMaxReq, tvCurrentProg;
    ImageView ivPlus, ivMinus;

    String color;
    int maxReq, currentprog, progpercent;

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

        Bundle args = getArguments();

        if(args != null){
            progpercent = args.getInt(DetailFields.PROGRESS.name());
            color = args.getString(DetailFields.COLOR.name());

            maxReq = args.getInt(DetailFields.MAXREQ.name());
            currentprog = args.getInt(DetailFields.PROGCOUNT.name());
        }
        initComponents(v);

        return v;
    }

    private void initComponents(View v){
        tvProgressPercent = v.findViewById(R.id.tv_details_goal_progress);
        tvProgressPercent.setText(progpercent + "%");

        tvMaxReq = v.findViewById(R.id.tv_details_goal_maxreq);
        tvMaxReq.setText("out of " + maxReq);

        tvCurrentProg = v.findViewById(R.id.tv_details_goal_currentprog);
        tvCurrentProg.setText(String.valueOf(currentprog));

        ivPlus = v.findViewById(R.id.iv_details_goal_plus);
        ivMinus = v.findViewById(R.id.iv_details_goal_minus);

        pbProgress = v.findViewById(R.id.pb_details_goal_progress);

        setProgress(progpercent, color);

        ivPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int progcount = Integer.parseInt(tvCurrentProg.getText().toString());
                progcount++;

                updateProgress(progcount);
            }
        });

        ivMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int progcount = Integer.parseInt(tvCurrentProg.getText().toString());
                progcount--;

                updateProgress(progcount);
            }
        });
    }

    private void setProgress(int completion, String color) {
        String s = completion+"% complete";
        tvProgressPercent.setText(s);

        pbProgress.setProgress(completion);
        pbProgress.setProgressTintList(ColorStateList.valueOf(Color.parseColor(color)));
    }

    private void updateProgress(int progcount){
        if(progcount > maxReq)
            progcount = maxReq;
        else if(progcount < 0)
            progcount = 0;

        float x = ( (float) progcount)/maxReq;
        progpercent = (int) (x*100);

        tvCurrentProg.setText(String.valueOf(progcount));
        tvProgressPercent.setText(progpercent+"% complete");
        pbProgress.setProgress(progpercent);

        TaskDetailsActivity tda = (TaskDetailsActivity) getActivity();
        tda.retrieveProgress(progcount, progpercent);
    }
}