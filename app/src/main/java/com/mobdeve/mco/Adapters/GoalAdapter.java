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
import com.mobdeve.mco.ViewHolders.GoalViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class GoalAdapter extends RecyclerView.Adapter<GoalViewHolder> {

    private ArrayList<Task.Goal> data;

    public GoalAdapter(ArrayList<Task.Goal> data) {
        this.data = data;
    }


    @NonNull
    @NotNull
    @Override
    public GoalViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_goal, parent, false);

        GoalViewHolder vh = new GoalViewHolder(view);

        vh.getItemLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), TaskDetailsActivity.class);

                Task.Goal selectedTask = data.get(vh.getBindingAdapterPosition());

                i.putExtra(DetailFields.ID.name(), selectedTask.getId());
                i.putExtra(DetailFields.NAME.name(), selectedTask.getName());
                i.putExtra(DetailFields.DESC.name(), selectedTask.getDesc());
                i.putExtra(DetailFields.DONE.name(), selectedTask.getStatus());
                i.putExtra(DetailFields.NOTIFON.name(), selectedTask.getNotifOn());
                i.putExtra(DetailFields.NOTIF.name(), selectedTask.getNotifStringWithMonth());
                i.putExtra(DetailFields.COLOR.name(), selectedTask.getColor());
                i.putExtra(DetailFields.TYPE.name(), Types.Goal.name());
                i.putExtra(DetailFields.PROGRESS.name(), selectedTask.getProgress());
                i.putExtra(DetailFields.PROGCOUNT.name(), selectedTask.getCurrentProg());
                i.putExtra(DetailFields.MAXREQ.name(), selectedTask.getMaxreq());

                v.getContext().startActivity(i);
            }
        });

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull GoalViewHolder holder, int position) {
        String item_color = this.data.get(position).getColor();

        holder.setName(this.data.get(position).getName());
        holder.setColor(item_color);
        holder.setCheck(this.data.get(position).getStatus(), item_color);
        holder.setTime(this.data.get(position).getNotif());
        holder.setProgress(this.data.get(position).getProgress(), item_color);

    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }
}
