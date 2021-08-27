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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GoalDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GoalDetailsFragment extends Fragment {

    ProgressBar pbProgress;
    TextView tvProgress;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GoalDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GoalDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GoalDetailsFragment newInstance(String param1, String param2) {
        GoalDetailsFragment fragment = new GoalDetailsFragment();
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