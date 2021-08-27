package com.mobdeve.mco.ViewHolders;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.mco.R;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ToDoViewHolder extends RecyclerView.ViewHolder {

    ImageView ivCheck, ivColor, ivAlarm;
    TextView tvTime, tvName;
    ConstraintLayout clItem;

    public ToDoViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        clItem = itemView.findViewById(R.id.cl_todo_item);

        ivCheck = itemView.findViewById(R.id.iv_todo_check);
        ivColor = itemView.findViewById(R.id.iv_todo_color);
        ivAlarm = itemView.findViewById(R.id.iv_todo_alarm);

        tvName = itemView.findViewById(R.id.tv_todo_name);
        tvTime = itemView.findViewById(R.id.tv_todo_time);
    }

    public ConstraintLayout getItemLayout(){
        return clItem;
    }

    public void setColor(String color){
        ImageViewCompat.setImageTintList(ivColor, ColorStateList.valueOf(Color.parseColor(color)));
    }

    public void setName(String name){
        tvName.setText(name);
    }

    public void setTime(LocalDateTime time){
        String formatedtime;
        LocalDateTime today = LocalDateTime.now();

        if(time == null){
            ivAlarm.setVisibility(View.GONE);
            tvTime.setVisibility(View.GONE);
        }
        else if(!(today.getYear() == time.getYear() && today.getMonth() == time.getMonth() && today.getDayOfMonth() == time.getDayOfMonth())){
            formatedtime = time.format(DateTimeFormatter.ofPattern("dd MMM hh:mm a"));
            tvTime.setText(formatedtime);
        } else {
            formatedtime = time.format(DateTimeFormatter.ofPattern("dd MMM hh:mm a"));
            tvTime.setText(formatedtime);
        }
    }

    @SuppressLint("ResourceAsColor")
    public void setCheck(boolean complete, String color){

        if(complete)
            ImageViewCompat.setImageTintList(ivCheck, ColorStateList.valueOf(Color.parseColor(color)));
    }

}
