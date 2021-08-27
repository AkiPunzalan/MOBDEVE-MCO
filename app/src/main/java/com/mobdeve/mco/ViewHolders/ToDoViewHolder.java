package com.mobdeve.mco.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.mco.R;

import org.jetbrains.annotations.NotNull;

public class ToDoViewHolder extends RecyclerView.ViewHolder {

    ImageView ivCheck, ivColor;
    TextView tvTime, tvName;
    ConstraintLayout clItem;

    public ToDoViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        clItem = itemView.findViewById(R.id.cl_todo_item);

        ivCheck = itemView.findViewById(R.id.iv_todo_check);
        ivColor = itemView.findViewById(R.id.iv_todo_color);

        tvName = itemView.findViewById(R.id.tv_todo_name);
        tvTime = itemView.findViewById(R.id.tv_todo_time);

    }
}
