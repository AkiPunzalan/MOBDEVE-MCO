package com.mobdeve.mco.ViewHolders;

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
    ProgressBar pbProgress;


    public GoalViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        clItem = itemView.findViewById(R.id.cl_goal_item);

        ivColor = itemView.findViewById(R.id.iv_goal_color);
        ivCheck = itemView.findViewById(R.id.iv_goal_check);

        tvComp = itemView.findViewById(R.id.tv_goal_progress);
        tvTime = itemView.findViewById(R.id.tv_goal_time);
        tvName = itemView.findViewById(R.id.tv_goal_name);

        pbProgress = itemView.findViewById(R.id.pb_goal_progress);

    }

    public ConstraintLayout getItemLayout() {
        return clItem;
    }

    public void setColor(String color) {
        ImageViewCompat.setImageTintList(ivColor, ColorStateList.valueOf(Color.parseColor(color)));
    }

    public void setName(String name) {
        tvName.setText(name);
    }

    public void setTime(LocalDateTime time){
        String formatedtime;

        formatedtime = "ends on " + time.format(DateTimeFormatter.ofPattern("dd MMM"));
        tvTime.setText(formatedtime);
    }


    public void setCheck(boolean complete, String color) {
        if(complete)
            ImageViewCompat.setImageTintList(ivCheck, ColorStateList.valueOf(Color.parseColor(color)));
    }

    public void setProgress(int completion, String color) {
        String s = completion+"% complete";
        tvComp.setText(s);

        pbProgress.setProgress(completion);
        pbProgress.setProgressTintList(ColorStateList.valueOf(Color.parseColor(color)));
    }


}
