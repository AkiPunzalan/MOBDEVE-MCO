package com.mobdeve.mco.Adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.mco.Task;
import com.mobdeve.mco.ViewHolders.ToDoViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoViewHolder> {

    private ArrayList<Task> data;

    public ToDoAdapter(ArrayList<Task> data) {
        this.data = data;
    }


    @NonNull
    @NotNull
    @Override
    public ToDoViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ToDoViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
