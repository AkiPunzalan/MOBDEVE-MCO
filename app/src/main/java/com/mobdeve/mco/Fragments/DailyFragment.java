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
import android.widget.Button;
import android.widget.Toast;

import com.mobdeve.mco.Adapters.DailyAdapter;
import com.mobdeve.mco.DatabaseHelper;
import com.mobdeve.mco.Keys.Types;
import com.mobdeve.mco.R;
import com.mobdeve.mco.Task;

import java.util.ArrayList;


public class DailyFragment extends Fragment {

    private DatabaseHelper db;

    private RecyclerView rvDaily;
    private DailyAdapter adapter;
    private ArrayList<Task.Daily> daily_list = new ArrayList<Task.Daily>();

    public DailyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        daily_list.clear();
        daily_list.addAll(initDaily(new ArrayList<Task.Daily>()));
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_daily, container, false);

        initRecyclerview(v);

        return v;
    }

    private void initRecyclerview(View v){
        rvDaily = v.findViewById(R.id.rv_daily);
        db = new DatabaseHelper(getActivity());
        initDaily(daily_list);

        adapter = new DailyAdapter(this.daily_list);
        rvDaily.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        this.rvDaily.setAdapter(adapter);
    }

    private ArrayList<Task.Daily> initDaily(ArrayList<Task.Daily> data){
        Cursor cursor = db.readTable(Types.Daily.name());
        if(cursor.getCount() == 0){
            Log.v("database initialize", "database empty");
        } else {
            while(cursor.moveToNext()){
                data.add(new Task.Daily(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4),
                    cursor.getInt(5),
                    cursor.getString(6),
                    cursor.getString(7))
                );
            }
        }
        return data;
    }
}