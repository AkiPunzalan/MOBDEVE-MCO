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

import com.mobdeve.mco.Adapters.DailyAdapter;
import com.mobdeve.mco.Adapters.ToDoAdapter;
import com.mobdeve.mco.DatabaseHelper;
import com.mobdeve.mco.Keys.Types;
import com.mobdeve.mco.R;
import com.mobdeve.mco.Task;

import java.util.ArrayList;

public class TodoFragment extends Fragment {

    private DatabaseHelper db;

    private RecyclerView rvTodo;
    private ToDoAdapter adapter;
    private ArrayList<Task> todo_list = new ArrayList<>();

    public TodoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        todo_list.clear();
        todo_list.addAll(initTodo(new ArrayList<Task>()));
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_todo, container, false);

        initRecyclerview(v);

        return v;
    }

    private void initRecyclerview(View v) {
        rvTodo = v.findViewById(R.id.rv_todo);
        db = new DatabaseHelper(getActivity());
        initTodo(todo_list);

        adapter = new ToDoAdapter(this.todo_list);
        rvTodo.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        this.rvTodo.setAdapter(adapter);
    }

    private ArrayList<Task> initTodo(ArrayList<Task> data){
        Cursor cursor = db.readTable(Types.Todo.name());
        if(cursor.getCount() == 0){
            Log.v("database initialize", "database empty");
        } else {
            while(cursor.moveToNext()){
                data.add(new Task(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4),
                    cursor.getInt(5),
                    cursor.getString(6))
                );
            }
        }
        return data;
    }

}