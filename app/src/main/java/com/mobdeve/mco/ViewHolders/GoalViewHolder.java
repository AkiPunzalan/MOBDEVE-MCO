package com.mobdeve.mco.ViewHolders;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.mco.R;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GoalViewHolder extends RecyclerView.ViewHolder {

    ConstraintLayout clItem;
    ImageView ivColor, ivCheck;
    TextView tvTime, tvName, tvComp;
    ProgressBar progressBar;


    public GoalViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        clItem = itemView.findViewById(R.id.cl_goal_item);

        ivColor = itemView.findViewById(R.id.iv_todo_color);
        ivCheck = itemView.findViewById(R.id.iv_todo_check);

        tvComp = itemView.findViewById(R.id.tv_todo_time);
        tvTime = itemView.findViewById(R.id.tv_goal_time);
        tvName = itemView.findViewById(R.id.tv_todo_name);

        progressBar = itemView.findViewById(R.id.progressBar2);

    }

    public ConstraintLayout getItemLayout() {
        return clItem;
    }

    public void setColor(String color) {
        ImageViewCompat.setImageTintList(ivColor, ColorStateList.valueOf(Color.parseColor(color)));
        progressBar.getProgressDrawable().setColorFilter(Color.parseColor(color));
    }

    public void setName(String name) {
        tvName.setText(name);
    }

    public void setTime(LocalDateTime time){
        String formatedtime;
        LocalDateTime today = LocalDateTime.now();

        formatedtime = "starts at " + time.format(DateTimeFormatter.ofPattern("dd MM"));
        tvTime.setText(formatedtime);
    }

    @SuppressLint("ResourceAsColor")
    public void setCheck(boolean complete, String color) {
        if(complete)
            ImageViewCompat.setImageTintList(ivCheck, ColorStateList.valueOf(Color.parseColor(color)));
    }

    public void setProgressBar(int completion) {
        TextView.setText(Integer.parseInt(String.valueOf(completion)+"%"));

        progressBar.setProgress(Integer.parseInt(String.valueOf(completion)));

    }


}
