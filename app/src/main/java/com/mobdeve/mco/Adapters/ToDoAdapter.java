package com.mobdeve.mco.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.mco.Activities.TaskDetailsActivity;
import com.mobdeve.mco.Keys.DetailFields;
import com.mobdeve.mco.Keys.Types;
import com.mobdeve.mco.R;
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
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_todo, parent, false);

        ToDoViewHolder vh = new ToDoViewHolder(view);

        vh.getItemLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), TaskDetailsActivity.class);

                Task selectedTask = data.get(vh.getBindingAdapterPosition());

                i.putExtra(DetailFields.ID.name(), selectedTask.getId());
                i.putExtra(DetailFields.NAME.name(), selectedTask.getName());
                i.putExtra(DetailFields.DESC.name(), selectedTask.getDesc());
                i.putExtra(DetailFields.DONE.name(), selectedTask.getStatus());
                i.putExtra(DetailFields.COLOR.name(), selectedTask.getColor());
                i.putExtra(DetailFields.NOTIF.name(), selectedTask.getNotifStringWithMonth());
                i.putExtra(DetailFields.TYPE.name(), Types.Todo.name());

                v.getContext().startActivity(i);
            }
        });

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ToDoViewHolder holder, int position) {

        String item_color = this.data.get(position).getColor();

        holder.setName(this.data.get(position).getName());
        holder.setColor(this.data.get(position).getColor());
        holder.setTime(this.data.get(position).getNotif());
        holder.setCheck(this.data.get(position).getStatus(), item_color);
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }
}
