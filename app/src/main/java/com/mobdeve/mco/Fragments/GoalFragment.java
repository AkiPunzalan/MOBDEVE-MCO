package com.mobdeve.mco.Fragments;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobdeve.mco.Adapters.GoalAdapter;
import com.mobdeve.mco.DatabaseHelper;
import com.mobdeve.mco.Keys.Types;
import com.mobdeve.mco.R;
import com.mobdeve.mco.Task;

import java.util.ArrayList;

public class GoalFragment extends Fragment {

    private DatabaseHelper db;

    private RecyclerView rvGoal;
    private GoalAdapter adapter;
    private ArrayList<Task.Goal> goals_list = new ArrayList<>();

    public GoalFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        goals_list.clear();
        goals_list.addAll(initGoals(new ArrayList<Task.Goal>()));
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_goal, container, false);

        initRecyclerview(v);

        return v;
    }

    private void initRecyclerview(View v){
        rvGoal = v.findViewById(R.id.rv_goal);
        db = new DatabaseHelper(getActivity());
        initGoals(goals_list);

        adapter = new GoalAdapter(this.goals_list);
        rvGoal.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        this.rvGoal.setAdapter(adapter);
    }

    private ArrayList<Task.Goal> initGoals(ArrayList<Task.Goal> data){
        Cursor cursor = db.readTable(Types.Goal.name());
        if(cursor.getCount() == 0){
            Log.v("database initialize", "database empty");
        } else {
            while(cursor.moveToNext()){
                data.add(new Task.Goal(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4),
                    cursor.getInt(5),
                    cursor.getString(6),
                    cursor.getInt(7))
                );
            }
        }
        return data;
    }

}