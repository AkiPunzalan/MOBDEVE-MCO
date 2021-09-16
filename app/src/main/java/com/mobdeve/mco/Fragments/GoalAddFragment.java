package com.mobdeve.mco.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mobdeve.mco.Activities.AddActivity;
import com.mobdeve.mco.R;

public class GoalAddFragment extends Fragment {

    EditText etMaxReq;

    public GoalAddFragment() {
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
        View v = inflater.inflate(R.layout.fragment_goal_add, container, false);
        AddActivity aa = (AddActivity) getActivity();

        etMaxReq = v.findViewById(R.id.et_add_goal_maxReq);
        etMaxReq.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int maxreq;
                if(etMaxReq.getText().toString().equals(""))
                    maxreq = 1;
                else maxreq = Integer.parseInt(etMaxReq.getText().toString());
                aa.retrieveMaxreq(maxreq);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        return v;
    }
}