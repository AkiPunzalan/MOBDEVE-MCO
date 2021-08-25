package com.mobdeve.mco.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.mco.R;
import com.mobdeve.mco.Task;
import com.mobdeve.mco.ViewHolders.DailyViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DailyAdapter extends RecyclerView.Adapter<DailyViewHolder>{

    private ArrayList<Task.Daily> data;

    public DailyAdapter(ArrayList<Task.Daily> data){
        this.data = data;
    }

    @NonNull
    @NotNull
    @Override
    public DailyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_daily, parent, false);

        DailyViewHolder vh = new DailyViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DailyViewHolder holder, int position) {
        String item_color = this.data.get(position).getColor();

        holder.setName(this.data.get(position).getName());
        holder.setColor(item_color);
        holder.setCheck(this.data.get(position).getCompleted(), item_color);
        holder.setTime(this.data.get(position).getNotif());
        holder.setDaysoftheWeek(this.data.get(position).getDays(), item_color);
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }
}
