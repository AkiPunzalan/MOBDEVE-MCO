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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DailyFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class DailyFragment extends Fragment {

    private DatabaseHelper db;

    private RecyclerView rvDaily;
    private ArrayList<Task.Daily> daily_list = new ArrayList<Task.Daily>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DailyFragment newInstance(String param1, String param2) {
        DailyFragment fragment = new DailyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public DailyFragment() {
        // Required empty public constructor
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
        View v = inflater.inflate(R.layout.fragment_daily, container, false);

        initRecyclerview(v);

        return v;
    }

    private void initRecyclerview(View v){
        rvDaily = v.findViewById(R.id.rv_daily);
        db = new DatabaseHelper(getActivity());
        initDaily();

        rvDaily.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        this.rvDaily.setAdapter(new DailyAdapter(this.daily_list));
    }

    private void initDaily(){
        Cursor cursor = db.readTable(Types.Daily.name());
        if(cursor.getCount() == 0){
            Log.v("database initialize", "database empty");
        } else {
            while(cursor.moveToNext()){
                daily_list.add(new Task.Daily(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4),
                    cursor.getString(5),
                    cursor.getString(6))
                );
            }
        }
    }
}