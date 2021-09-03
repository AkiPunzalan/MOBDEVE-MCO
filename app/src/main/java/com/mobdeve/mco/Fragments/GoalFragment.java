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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GoalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GoalFragment extends Fragment {

    private DatabaseHelper db;

    private RecyclerView rvGoal;
    private ArrayList<Task.Goal> goals_list = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GoalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GoalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GoalFragment newInstance(String param1, String param2) {
        GoalFragment fragment = new GoalFragment();
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

        View v = inflater.inflate(R.layout.fragment_goal, container, false);

        initRecyclerview(v);

        return v;
    }

    private void initRecyclerview(View v){
        rvGoal = v.findViewById(R.id.rv_goal);
        db = new DatabaseHelper(getActivity());
        initGoals();

        rvGoal.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        this.rvGoal.setAdapter(new GoalAdapter(this.goals_list));
    }

    private void initGoals(){
        Cursor cursor = db.readTable(Types.Goal.name());
        if(cursor.getCount() == 0){
            Log.v("database initialize", "database empty");
        } else {
            while(cursor.moveToNext()){
                goals_list.add(new Task.Goal(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4),
                    cursor.getString(5),
                    cursor.getInt(6))
                );
            }
        }
    }

}